package eu.nicosworld.rithmo.engine.model;

import java.util.Objects;

/**
 * Represents a participant in the Rithmomachie game.
 * <p>A player is defined by their assigned color, which determines
 * the pieces they control and their starting position on the board.</p>
 */
public class Player {
    private final PlayerColor color;

    /**
     * Constructs a player with the specified color.
     *
     * @param color the color identifying the player (e.g., BLACK or WHITE)
     */
    public Player(PlayerColor color) {
        this.color = color;
    }

    /**
     * @return the color of this player
     */
    public PlayerColor getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return color == player.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    @Override
    public String toString() {
        return "Player " + color;
    }
}