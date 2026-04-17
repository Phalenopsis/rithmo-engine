package eu.nicosworld.rithmoEngine.setup;

import eu.nicosworld.rithmoEngine.model.Board;
import eu.nicosworld.rithmoEngine.model.GameState;
import eu.nicosworld.rithmoEngine.model.Player;
import eu.nicosworld.rithmoEngine.model.PlayerColor;

public class GameStateFactory {

    public static GameState from(Board board, PlayerColor current) {
        Player player = new Player(current);
        return new GameState(board, player);
    }

    public static GameState from(Board board, Player current) {
        return new GameState(board, current);
    }
}
