package eu.nicosworld.setup;

import eu.nicosworld.model.*;

public class BoardBuilder {

    private final Board board = new Board();
    private final Player white = new Player(PlayerColor.WHITE);
    private final Player black = new Player(PlayerColor.BLACK);

    private Piece currentPiece;

    // =========================
    // PIECE CREATION
    // =========================

    public BoardBuilder whiteCircle(int value) {
        currentPiece = new SimplePiece(PieceType.CIRCLE, white, value);
        return this;
    }

    public BoardBuilder blackCircle(int value) {
        currentPiece = new SimplePiece(PieceType.CIRCLE, black, value);
        return this;
    }

    public BoardBuilder whiteTriangle(int value) {
        currentPiece = new SimplePiece(PieceType.TRIANGLE, white, value);
        return this;
    }

    public BoardBuilder blackTriangle(int value) {
        currentPiece = new SimplePiece(PieceType.TRIANGLE, black, value);
        return this;
    }

    public BoardBuilder whiteSquare(int value) {
        currentPiece = new SimplePiece(PieceType.SQUARE, white, value);
        return this;
    }

    public BoardBuilder blackSquare(int value) {
        currentPiece = new SimplePiece(PieceType.SQUARE, black, value);
        return this;
    }

    // =========================
    // POSITION
    // =========================

    public BoardBuilder at(int x, int y) {
        board.set(new Position(x, y), currentPiece);
        return this;
    }

    // =========================
    // BUILD
    // =========================
    public Board build() {
        return board;
    }
}
