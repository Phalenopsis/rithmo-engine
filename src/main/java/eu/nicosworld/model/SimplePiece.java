package eu.nicosworld.model;

public class SimplePiece extends Piece{
    private final int value;

    public SimplePiece(PieceType type, Player player, int value) {
        super(type, player);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
