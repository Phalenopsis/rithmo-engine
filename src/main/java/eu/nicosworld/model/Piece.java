package eu.nicosworld.model;

import java.util.Objects;

public abstract class Piece {
    private PieceType type;
    private Player owner;
    private PieceState state;
    private String id;

    protected Piece(PieceType type, Player player) {
        this(type, player, PieceState.IN_GAME);
    }

    protected Piece(PieceType type, Player player, PieceState state) {
        this.type = type;
        this.owner = player;
        this.state = state;
    }

    abstract int getValue();

    public Player getPlayer() {
        return owner;
    }

    public PieceType getType() {
        return type;
    }

    public PieceState getState() {
        return state;
    }
}
