package eu.nicosworld.rithmo.engine.capture;

import static org.junit.jupiter.api.Assertions.fail;

import eu.nicosworld.rithmo.engine.capture.capturerule.ImprisonmentRule;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;
import eu.nicosworld.rithmo.engine.setup.BoardBuilder;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Performance regression test for imprisonment detection.
 *
 * <p>This test intentionally builds an artificial high-density board that is significantly more
 * crowded than a typical Rithmomachia position.
 *
 * <p>The purpose of this test is not to benchmark absolute performance but to detect major
 * algorithmic regressions (for example introducing an accidental O(n²) or O(n³) traversal).
 *
 * <p>The benchmark executes the imprisonment scan repeatedly after a warmup phase and verifies that
 * the average execution time remains below a generous threshold.
 *
 * <p>The threshold is intentionally high to avoid false positives caused by different hardware, JVM
 * implementations or CI environments.
 */
class ImprisonmentPerformanceTest {

  private static final int ITERATIONS = 1_000;
  private static final double MAX_AVERAGE_MS = 25.0;

  @Test
  void imprisonment_scan_should_remain_reasonably_fast() {

    Board board = createDenseBoard();
    GameState state = GameState.initial(board, Player.BLACK);

    CaptureEngine engine =
        new CaptureEngine(
            List.of(
                new ImprisonmentRule(new FreePathMovementValidator(), new RegularMoveGenerator())));

    CaptureContext context = new CaptureContext(state, null, null);

    // Warmup phase to allow JVM optimizations.
    for (int i = 0; i < 100; i++) {
      engine.findGlobalCaptures(context);
    }

    long start = System.nanoTime();

    for (int i = 0; i < ITERATIONS; i++) {
      engine.findGlobalCaptures(context);
    }

    long end = System.nanoTime();

    double averageMs = ((end - start) / 1_000_000.0) / ITERATIONS;

    System.out.printf("Average imprisonment scan time: %.6f ms%n", averageMs);

    if (averageMs > MAX_AVERAGE_MS) {
      fail(
          String.format(
              "Performance regression detected. "
                  + "Average imprisonment scan took %.3f ms "
                  + "(threshold: %.3f ms).",
              averageMs, MAX_AVERAGE_MS));
    }
  }

  /**
   * Builds an artificial dense board used to stress imprisonment detection.
   *
   * <p>Approximately 80% of the board is populated with pieces of alternating colors, types and
   * values.
   */
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
