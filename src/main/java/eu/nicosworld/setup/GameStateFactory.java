package eu.nicosworld.setup;

import eu.nicosworld.model.Board;
import eu.nicosworld.model.GameState;
import eu.nicosworld.model.Player;
import eu.nicosworld.model.PlayerColor;

public class GameStateFactory {

    public static GameState from(Board board, PlayerColor current) {
        Player player = new Player(current);
        return new GameState(board, player);
    }

    public static GameState from(Board board, Player current) {
        return new GameState(board, current);
    }
}
