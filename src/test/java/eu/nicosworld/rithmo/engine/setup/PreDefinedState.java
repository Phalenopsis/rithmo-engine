package eu.nicosworld.rithmo.engine.setup;

import eu.nicosworld.rithmo.engine.model.Board;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.Player;

public class PreDefinedState {
  public static GameState predefinedVerySimpleGame(Player player) {
    Board board =
        new BoardBuilder(4, 4)
            .blackTriangle(4)
            .at(0, 0)
            .blackCircle(4)
            .at(1, 1)
            .whiteCircle(4)
            .at(3, 1)
            .whiteCircle(4)
            .at(3, 3)
            .build();

    return GameState.initial(board, player);
  }

  public static GameState progressionVictoryState_mediumBoard_arithmetic(Player player) {
    Board board =
        new BoardBuilder(8, 4)
            .blackTriangle(56)
            .at(7, 0)
            .blackCircle(16)
            .at(7, 1)
            .blackCircle(36)
            .at(7, 3)
            .whiteTriangle(56)
            .at(0, 0)
            .whiteCircle(16)
            .at(0, 1)
            .whiteCircle(36)
            .at(0, 3)
            .build();
    return GameState.initial(board, player);
  }

  public static GameState progressionVictoryState_classicBoard_excellent(Player player) {
    Board board =
        new BoardBuilder()
            .blackTriangle(12)
            .at(15, 0)
            .blackCircle(4)
            .at(12, 0)
            .blackCircle(9)
            .at(12, 3)
            .blackSquare(6)
            .at(15, 3)
            .whiteTriangle(12)
            .at(0, 0)
            .whiteCircle(4)
            .at(3, 0)
            .whiteCircle(9)
            .at(3, 3)
            .whiteSquare(6)
            .at(0, 3)
            .build();
    return GameState.initial(board, player);
  }
}
