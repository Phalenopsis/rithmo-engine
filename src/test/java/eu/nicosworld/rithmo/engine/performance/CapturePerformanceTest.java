package eu.nicosworld.rithmo.engine.performance;

import static org.junit.jupiter.api.Assertions.fail;

import eu.nicosworld.rithmo.engine.capture.CaptureEngine;
import eu.nicosworld.rithmo.engine.capture.capturerule.*;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;
import eu.nicosworld.rithmo.engine.setup.BoardBuilder;
import eu.nicosworld.rithmo.engine.threat.RegularThreatEngine;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Performance regression tests.
 *
 * <p>The goal is not to benchmark absolute performance but to detect accidental algorithmic
 * regressions.
 *
 * <p>The generated board is intentionally denser than a typical game position.
 */
class CapturePerformanceTest {

  private static final int ITERATIONS = 1_000;
  private static final double MAX_AVERAGE_MS = 25.0;

  @Test
  void imprisonment_pre_move_scan_should_remain_fast() {

    CaptureEngine engine = imprisonmentEngine();

    CaptureContext context = new CaptureContext(createState(), null, null);

    warmup(() -> engine.findPreMoveCaptures(context));

    double averageMs = measureAverageMs(() -> engine.findPreMoveCaptures(context));

    System.out.printf("Average imprisonment preMove scan time: %.6f ms%n", averageMs);

    assertPerformance("Imprisonment preMove", averageMs);
  }

  @Test
  void imprisonment_post_move_scan_should_remain_fast() {

    GameState state = createState();

    CaptureEngine engine = imprisonmentEngine();

    PieceAtPosition actor = state.board().getPiecesForPlayer(state.currentPlayer()).getFirst();

    CaptureContext context = new CaptureContext(state, actor, null);

    warmup(() -> engine.findPostMoveCaptures(context));

    double averageMs = measureAverageMs(() -> engine.findPostMoveCaptures(context));

    System.out.printf("Average imprisonment postMove scan time: %.6f ms%n", averageMs);

    assertPerformance("Imprisonment postMove", averageMs);
  }

  @Test
  void imprisonment_end_turn_scan_should_remain_fast() {

    CaptureEngine engine = imprisonmentEngine();

    CaptureContext context = new CaptureContext(createState(), null, null);

    warmup(() -> engine.findEndTurnCaptures(context));

    double averageMs = measureAverageMs(() -> engine.findEndTurnCaptures(context));

    System.out.printf("Average imprisonment endTurn scan time: %.6f ms%n", averageMs);

    assertPerformance("Imprisonment endTurn", averageMs);
  }

  @Test
  void complete_capture_pipeline_should_remain_fast() {

    GameState state = createState();

    RegularMoveGenerator generator = new RegularMoveGenerator();
    FreePathMovementValidator validator = new FreePathMovementValidator();

    RegularThreatEngine threatEngine = new RegularThreatEngine(generator, validator);

    CaptureEngine engine =
        new CaptureEngine(
            List.of(
                new ImprisonmentRule(validator, generator),
                new ProgressionRule(),
                new AmbushRule(),
                new AssaultRule(),
                new EncounterRule(),
                new PowerRule()));

    CaptureContext globalContext = new CaptureContext(state, null, null);

    warmup(() -> runCompleteTurn(engine, threatEngine, state, globalContext));

    double averageMs =
        measureAverageMs(() -> runCompleteTurn(engine, threatEngine, state, globalContext));

    System.out.printf("Average all rules scan time: %.6f ms%n", averageMs);

    assertPerformance("Complete capture pipeline", averageMs);
  }

  private void runCompleteTurn(
      CaptureEngine engine,
      RegularThreatEngine threatEngine,
      GameState state,
      CaptureContext globalContext) {

    engine.findPreMoveCaptures(globalContext);

    for (PieceAtPosition actor : state.board().getPiecesForPlayer(state.currentPlayer())) {

      CaptureContext actorContext =
          new CaptureContext(state, actor, threatEngine.findRegularThreats(state, actor));

      engine.findActiveCaptures(actorContext);
    }

    engine.findEndTurnCaptures(globalContext);
  }

  private static void warmup(Runnable runnable) {
    for (int i = 0; i < 100; i++) {
      runnable.run();
    }
  }

  private static double measureAverageMs(Runnable runnable) {

    long start = System.nanoTime();

    for (int i = 0; i < ITERATIONS; i++) {
      runnable.run();
    }

    long end = System.nanoTime();

    return ((end - start) / 1_000_000.0) / ITERATIONS;
  }

  private static void assertPerformance(String label, double averageMs) {

    if (averageMs > MAX_AVERAGE_MS) {
      fail(
          String.format(
              "%s regression detected: %.3f ms " + "(threshold %.3f ms)",
              label, averageMs, MAX_AVERAGE_MS));
    }
  }

  private CaptureEngine imprisonmentEngine() {

    return new CaptureEngine(
        List.of(new ImprisonmentRule(new FreePathMovementValidator(), new RegularMoveGenerator())));
  }

  private GameState createState() {
    return GameState.initial(createDenseBoard(), Player.BLACK);
  }

  private Board createDenseBoard() {

    BoardBuilder bb = new BoardBuilder();

    for (int y = 0; y < 8; y++) {
      for (int x = 0; x < 16; x++) {

        if ((x + y) % 5 == 0) {
          continue;
        }

        PlayerColor color = ((x + y) % 2 == 0) ? PlayerColor.BLACK : PlayerColor.WHITE;

        PieceType type =
            switch ((x + y) % 3) {
              case 0 -> PieceType.CIRCLE;
              case 1 -> PieceType.SQUARE;
              default -> PieceType.TRIANGLE;
            };

        int value = ((x + 1) * (y + 1)) % 225 + 1;

        bb.piece(type, value, color);
        bb.at(x, y);
      }
    }

    return bb.build();
  }
}
