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
}
