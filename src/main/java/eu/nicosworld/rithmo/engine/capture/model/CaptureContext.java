package eu.nicosworld.rithmo.engine.capture.model;

import eu.nicosworld.rithmo.engine.model.Board;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;

/**
 * Snapshot of the game state focused on a specific piece to evaluate its capture opportunities at
 * its current location.
 */
public record CaptureContext(GameState state, PieceAtPosition actor) {
  public Board board() {
    return state.board();
  }
}
