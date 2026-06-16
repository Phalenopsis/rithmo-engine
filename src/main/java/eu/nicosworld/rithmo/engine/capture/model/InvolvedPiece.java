package eu.nicosworld.rithmo.engine.capture.model;

import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.model.Pyramid;

/**
 * Represents a piece involved in a game action.
 *
 * <p>The {@code parentPiece} is the piece present on the board, while {@code specificComponent}
 * identifies the exact piece concerned by the action. For non-pyramid pieces, both references are
 * identical. For pyramid components, {@code parentPiece} refers to the pyramid and {@code
 * specificComponent} to the component inside it.
 *
 * @param parentPiece the piece present on the board
 * @param position the piece position on the board
 * @param specificComponent the exact piece involved in the action
 */
public record InvolvedPiece(
    Piece parentPiece, // The piece on the board (Pyramid or SimplePiece)
    Position position, // Its position
    Piece specificComponent // The specific component (e.g. Triangle 36)
    ) {

  /**
   * Creates an InvolvedPiece representing an entire piece (Circle, Square, Triangle, or a whole
   * Pyramid). In this case, the parent piece and the specific component are the same.
   */
  public static InvolvedPiece whole(Piece piece, Position position) {
    return new InvolvedPiece(piece, position, piece);
  }

  /**
   * Creates an InvolvedPiece representing an entire piece (Circle, Square, Triangle, or a whole
   * Pyramid). In this case, the parent piece and the specific component are the same.
   */
  public static InvolvedPiece whole(PieceAtPosition pieceAtPosition) {
    return new InvolvedPiece(
        pieceAtPosition.piece(), pieceAtPosition.position(), pieceAtPosition.piece());
  }

  /** Creates an InvolvedPiece representing a specific component inside a pyramid. */
  public static InvolvedPiece component(Pyramid pyramid, Piece component, Position position) {
    return new InvolvedPiece(pyramid, position, component);
  }

  /** Utility method to check whether the involvement concerns the entire piece. */
  public boolean isWhole() {
    return parentPiece.equals(specificComponent);
  }
}
