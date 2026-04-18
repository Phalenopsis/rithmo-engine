package eu.nicosworld.rithmoEngine.setup;

import eu.nicosworld.rithmoEngine.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Fluent builder for creating board configurations, now supporting composite pieces like Pyramids.
 */
public class BoardBuilder {

    private Board board = new Board();
    private final Player white = new Player(PlayerColor.WHITE);
    private final Player black = new Player(PlayerColor.BLACK);

    private Piece currentPiece;
    private List<SimplePiece> currentComponents = new ArrayList<>();

    public BoardBuilder piece(PieceType type, int value, PlayerColor color) {
        Player owner = color.equals(PlayerColor.BLACK) ? black : white;
        if (type == PieceType.PYRAMID) {
            currentPiece = new Pyramid(owner, new ArrayList<>());
            currentComponents = new ArrayList<>();
        } else {
            currentPiece = new SimplePiece(type, owner, value);
        }
        return this;
    }

    public BoardBuilder withComponent(PieceType type, int value) {
        if (!(currentPiece instanceof Pyramid)) {
            throw new IllegalStateException("Cannot add components to a non-pyramid piece");
        }
        currentComponents.add(new SimplePiece(type, currentPiece.getPlayer(), value));
        return this;
    }

    // Standard piece helpers
    public BoardBuilder blackCircle(int value) { return piece(PieceType.CIRCLE, value, PlayerColor.BLACK); }
    public BoardBuilder whiteCircle(int value) { return piece(PieceType.CIRCLE, value, PlayerColor.WHITE); }
    public BoardBuilder blackTriangle(int value) { return piece(PieceType.TRIANGLE, value, PlayerColor.BLACK); }
    public BoardBuilder whiteTriangle(int value) { return piece(PieceType.TRIANGLE, value, PlayerColor.WHITE); }
    public BoardBuilder blackSquare(int value) { return piece(PieceType.SQUARE, value, PlayerColor.BLACK); }
    public BoardBuilder whiteSquare(int value) { return piece(PieceType.SQUARE, value, PlayerColor.WHITE); }

    public BoardBuilder at(int x, int y) {
        if (currentPiece instanceof Pyramid) {
            // Re-instantiate to ensure components are immutable/final if needed
            currentPiece = new Pyramid(currentPiece.getPlayer(), new ArrayList<>(currentComponents));
        }
        board = board.addPiece(currentPiece, new Position(x, y));
        return this;
    }

    public Board build() {
        return board.copy();
    }
}