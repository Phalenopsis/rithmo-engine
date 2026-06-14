package eu.nicosworld.rithmo.engine.exception;

import eu.nicosworld.rithmo.engine.model.PieceAtPosition;

public class NotEmptyPositionException extends RuntimeException {
  public NotEmptyPositionException(PieceAtPosition pieceAtPosition) {
    super(
        "There is already %s at position %s."
            .formatted(pieceAtPosition.piece(), pieceAtPosition.position()));
  }
}
