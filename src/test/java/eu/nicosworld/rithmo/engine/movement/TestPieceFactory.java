package eu.nicosworld.rithmo.engine.movement;

import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PieceType;
import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.SimplePiece;

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
