package eu.nicosworld.rithmo.engine.testutils;

import eu.nicosworld.rithmo.engine.model.Board;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.Pyramid;
import eu.nicosworld.rithmo.engine.setup.BoardBuilder;
import eu.nicosworld.rithmo.engine.setup.PyramidBuilder;

public class PreDefinedState {
  public static GameState predefinedVerySimpleGame(Player player) {
    // spotless:off
    Board board =
        new BoardBuilder(4, 4)
            .blackTriangle(4).at(0, 0)
            .blackCircle(4).at(1, 1)
            .whiteCircle(4).at(3, 1)
            .whiteCircle(4).at(3, 3)
            .build();
    // spotless:on

    return GameState.initial(board, player);
  }

  public static GameState progressionVictoryState_mediumBoard_arithmetic(Player player) {
    // spotless:off
    Board board =
        new BoardBuilder(8, 4)
            .blackTriangle(56).at(7, 0)
            .blackCircle(16).at(7, 1)
            .blackCircle(36).at(7, 3)
            .whiteTriangle(56).at(0, 0)
            .whiteCircle(16).at(0, 1)
            .whiteCircle(36).at(0, 3)
            .build();
    // spotless:on

    return GameState.initial(board, player);
  }

  public static GameState progressionVictoryState_mediumBoard_arithmetic_withPyramid(
      Player player) {
    // spotless:off
    Pyramid blackPyramid = PyramidBuilder.forPlayer(Player.BLACK)
        .withSquare(34)
        .withTriangle(3)
        .withCircle(19)
        .build();

    Pyramid whitePyramid = PyramidBuilder.forPlayer(Player.WHITE)
        .withSquare(53)
        .withCircle(3)
        .build();

    Board board =
        new BoardBuilder(8, 4)
            .withPiece(blackPyramid).at(7,0)
            .blackCircle(16).at(7, 1)
            .blackCircle(36).at(7, 3)
            .whiteTriangle(56).at(0, 0)
            .whiteCircle(16).at(0, 1)
            .withPiece(whitePyramid).at(0, 3)
            .build();
    // spotless:on

    return GameState.initial(board, player);
  }

  public static GameState progressionVictoryState_negative_mediumBoard_arithmetic_withPyramid(
      Player player) {
    // spotless:off
    Pyramid blackPyramid = PyramidBuilder.forPlayer(Player.BLACK)
        .withSquare(2)
        .withTriangle(4)
        .withCircle(6)
        .withSquare(56)
        .build();

    Pyramid whitePyramid = PyramidBuilder.forPlayer(Player.WHITE)
        .withSquare(1)
        .withCircle(4)
        .withTriangle(7)
        .withSquare(36)
        .build();

    Board board =
        new BoardBuilder(8, 4)
            .withPiece(blackPyramid).at(7,0)
            .blackCircle(16).at(7, 1)
            .blackCircle(36).at(7, 3)
            .whiteTriangle(56).at(0, 0)
            .whiteCircle(16).at(0, 1)
            .withPiece(whitePyramid).at(0, 3)
            .build();
    // spotless:on

    return GameState.initial(board, player);
  }

  public static GameState progressionVictoryState_classicBoard_excellent(Player player) {
    // spotless:off
    Board board =
        new BoardBuilder()
            .blackTriangle(12).at(15, 0)
            .blackCircle(4).at(12, 0)
            .blackCircle(9).at(12, 3)
            .blackSquare(6).at(15, 3)
            .whiteTriangle(12).at(0, 0)
            .whiteCircle(4).at(3, 0)
            .whiteCircle(9).at(3, 3)
            .whiteSquare(6).at(0, 3)
            .build();
    // spotless:on

    return GameState.initial(board, player);
  }

  public static GameState progressionVictoryState_classicBoard_geometric(Player player) {
    // spotless:off
    Board board =
        new BoardBuilder()
            .blackCircle(4).at(12, 0)
            .blackTriangle(12).at(12, 1)
            .blackCircle(36).at(12, 2)
            .whiteCircle(4).at(3, 0)
            .whiteTriangle(12).at(3, 1)
            .whiteCircle(36).at(3, 2)
            .build();
    // spotless:on

    return GameState.initial(board, player);
  }

  public static GameState progressionVictoryState_classicBoard_harmonic(Player player) {
    // spotless:off
    Board board =
        new BoardBuilder()
            .blackSquare(6).at(14, 0)
            .blackCircle(8).at(14, 1)
            .blackTriangle(12).at(14, 2)
            .whiteSquare(6).at(1, 0)
            .whiteCircle(8).at(1, 1)
            .whiteTriangle(12).at(1, 2)
            .build();
    // spotless:on

    return GameState.initial(board, player);
  }

  public static GameState progressionVictoryState_classicBoard_doubleCombination(Player player) {
    // spotless:off
    Board board =
        new BoardBuilder()
            .blackSquare(6).at(15, 0)
            .blackCircle(8).at(15, 1)
            .blackCircle(9).at(15, 2)
            .blackTriangle(12).at(15, 3)
            .whiteSquare(6).at(0, 0)
            .whiteCircle(8).at(0, 1)
            .whiteCircle(9).at(0, 2)
            .whiteTriangle(12).at(0, 3)
            .build();
    // spotless:on

    return GameState.initial(board, player);
  }
}
