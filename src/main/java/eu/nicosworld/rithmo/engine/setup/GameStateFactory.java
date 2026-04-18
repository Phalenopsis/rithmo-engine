package eu.nicosworld.rithmo.engine.setup;

import eu.nicosworld.rithmo.engine.model.Board;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.PlayerColor;

public class GameStateFactory {

    public static GameState from(Board board, PlayerColor current) {
        Player player = new Player(current);
        return new GameState(board, player);
    }

    public static GameState from(Board board, Player current) {
        return new GameState(board, current);
    }
}
