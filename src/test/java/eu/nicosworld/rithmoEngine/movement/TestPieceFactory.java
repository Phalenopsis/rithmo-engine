package eu.nicosworld.rithmoEngine.movement;

import eu.nicosworld.rithmoEngine.model.Piece;
import eu.nicosworld.rithmoEngine.model.PieceType;
import eu.nicosworld.rithmoEngine.model.Player;
import eu.nicosworld.rithmoEngine.model.SimplePiece;

class TestPieceFactory {

    static Piece create(PieceType type, Player player) {
        return switch (type) {
            case TRIANGLE -> new SimplePiece(type, player, 1);
            case SQUARE -> new SimplePiece(type, player, 1);
            case CIRCLE -> new SimplePiece(type, player, 1);
            default -> throw new IllegalArgumentException();
        };
    }
}
