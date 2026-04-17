package eu.nicosworld.rithmoEngine.capture;

import eu.nicosworld.rithmoEngine.model.GameState;
import eu.nicosworld.rithmoEngine.model.PieceAtPosition;
import eu.nicosworld.rithmoEngine.model.Board;

/**
 * Snapshot of the game state focused on a specific piece
 * to evaluate its capture opportunities at its current location.
 */
public record CaptureContext(
        GameState state,
        PieceAtPosition actor
) {
    public Board board() {
        return state.getBoard();
    }
}