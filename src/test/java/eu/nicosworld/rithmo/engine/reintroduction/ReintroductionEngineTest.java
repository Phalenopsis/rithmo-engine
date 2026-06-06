package eu.nicosworld.rithmo.engine.reintroduction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.setup.PreDefinedState;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReintroductionEngineTest {
  ReintroductionEngine engine;

  @BeforeEach
  void setup() {
    engine = new ReintroductionEngine();
  }

  @Test
  void getFreeSpacesForColumn_Empty4X8Board_ShouldBe8Position() {
    Board board = new Board(4, 8);
    GameState state = GameState.initial(board, Player.BLACK);

    List<Position> positions = engine.getFreeSpaceForColumn(state, 0);
    assertEquals(8, positions.size());

    List<Position> expected =
        List.of(
            new Position(0, 0),
            new Position(0, 1),
            new Position(0, 2),
            new Position(0, 3),
            new Position(0, 4),
            new Position(0, 5),
            new Position(0, 6),
            new Position(0, 7));
    assertEquals(expected, positions);
  }

  @Test
  void getFreeSpacesFor_predefinedVerySimpleGame_ShouldBe3PositionsForBlackAnd2ForWhite() {
    GameState state = PreDefinedState.predefinedVerySimpleGame(Player.BLACK);
    //        0     1     2     3
    //        0  BT4   .     .     .
    //        1  .     BC4   .     WC4
    //        2  .     .     .     .
    //        3  .     .     .     WC4

    List<Position> positionsForBlack = engine.getFreeSpacesFor(state, Player.BLACK);
    List<Position> positionsForWhite = engine.getFreeSpacesFor(state, Player.WHITE);
    assertEquals(3, positionsForBlack.size());
    assertEquals(2, positionsForWhite.size());

    List<Position> expectedForBlack =
        List.of(new Position(0, 1), new Position(0, 2), new Position(0, 3));
    assertEquals(expectedForBlack, positionsForBlack);

    List<Position> expectedForWhite = List.of(new Position(3, 0), new Position(3, 2));
    assertEquals(expectedForWhite, positionsForWhite);
  }

  @Test
  void generateReintroduction_predefinedVerySimpleGame_ShouldBe0Reintroductions() {
    GameState state = PreDefinedState.predefinedVerySimpleGame(Player.BLACK);
    //        0     1     2     3
    //        0  BT4   .     .     .
    //        1  .     BC4   .     WC4
    //        2  .     .     .     .
    //        3  .     .     .     WC4

    List<Reintroduction> reintroductions = engine.generateReintroduction(state);

    assertEquals(0, reintroductions.size());
  }

  @Test
  void generateReintroduction_predefinedVerySimpleGame_ShouldBe3Reintroductions() {
    GameState state = PreDefinedState.predefinedVerySimpleGame(Player.BLACK);
    //        0     1     2     3
    //        0  BT4   .     .     .
    //        1  .     BC4   .     WC4
    //        2  .     .     .     .
    //        3  .     .     .     WC4
    GameState newState =
        state.withAssets(
            state.currentPlayer().getColor(),
            state
                .assetsOfCurrentPlayer()
                .captureAndStore(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 16)));

    List<Reintroduction> reintroductions = engine.generateReintroduction(newState);

    assertEquals(3, reintroductions.size());
  }

  @Test
  void generateReintroduction_predefinedVerySimpleGame_ShouldBe2Reintroductions() {
    GameState state = PreDefinedState.predefinedVerySimpleGame(Player.WHITE);
    //        0     1     2     3
    //        0  BT4   .     .     .
    //        1  .     BC4   .     WC4
    //        2  .     .     .     .
    //        3  .     .     .     WC4
    GameState newState =
        state.withAssets(
            state.currentPlayer().getColor(),
            state
                .assetsOfCurrentPlayer()
                .captureAndStore(new SimplePiece(PieceType.CIRCLE, Player.BLACK, 16)));
    Piece piece = newState.assetsOfCurrentPlayer().reserve().getFirst();

    List<Reintroduction> reintroductions = engine.generateReintroduction(newState);

    assertEquals(2, reintroductions.size());
    assertThat(reintroductions)
        .contains(new Reintroduction(piece, new Position(3, 0)))
        .contains(new Reintroduction(piece, new Position(3, 2)));
  }

  @Test
  void generateReintroduction_predefinedVerySimpleGame_ShouldBe6ReintroductionsAllForBlackPlayer() {
    GameState state = PreDefinedState.predefinedVerySimpleGame(Player.BLACK);
    //        0     1     2     3
    //        0  BT4   .     .     .
    //        1  .     BC4   .     WC4
    //        2  .     .     .     .
    //        3  .     .     .     WC4
    GameState newState =
        state.withAssets(
            state.currentPlayer().getColor(),
            state
                .assetsOfCurrentPlayer()
                .captureAndStore(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 16))
                .captureAndStore(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 64)));

    List<Reintroduction> reintroductions = engine.generateReintroduction(newState);

    assertEquals(6, reintroductions.size());
    assertThat(reintroductions.stream().allMatch(r -> r.piece().getPlayer().equals(Player.BLACK)));
  }
}
