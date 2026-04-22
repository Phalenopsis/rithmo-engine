package eu.nicosworld.rithmo.engine.model;

import java.util.Objects;

/**
 * Represents a participant in the Rithmomachie game.
 * <p>A player is defined by their assigned color, which determines
 * the pieces they control and their starting position on the board.</p>
 */
public class Player {
    public static final Player BLACK = new Player(PlayerColor.BLACK);
    public static final Player WHITE = new Player(PlayerColor.WHITE);

    private final PlayerColor color;

    /**
     * Constructs a player with the specified color.
     *
     * @param color the color identifying the player (e.g., BLACK or WHITE)
     */
    private Player(PlayerColor color) {
        this.color = color;
    }

    /**
     * @return the color of this player
     */
    public PlayerColor getColor() {
        return color;
    }

    public Player opponent() {
        return this == BLACK ? WHITE : BLACK;
    }

    public static Player of(PlayerColor color) {
        return color == PlayerColor.BLACK ? BLACK : WHITE;
    }


    @Override
    public String toString() {
        return "Player " + color;
    }
}