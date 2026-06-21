package eu.nicosworld.rithmo.engine.testutils;

import eu.nicosworld.rithmo.engine.model.*;
import java.util.function.Predicate;

public class PieceAtPositionHelper {
  public static PieceAtPosition create(
      PieceType type, Player player, int value, String positionRepresentation) {
    Position position = StringRepresentationHelper.parsePosition(positionRepresentation);
    SimplePiece piece = new SimplePiece(type, player, value);
    return new PieceAtPosition(piece, position);
  }

  public static Predicate<PieceAtPosition> samePieceAtPosition(PieceAtPosition expected) {
    return actual ->
        actual.position().equals(expected.position())
            && samePiece(actual.piece()).test(expected.piece());
  }

  public static Predicate<Piece> samePiece(Piece expected) {
    return actual ->
        actual.getPlayer().equals(expected.getPlayer())
            && actual.getValue() == expected.getValue()
            && actual.getType().equals(expected.getType());
  }
}
