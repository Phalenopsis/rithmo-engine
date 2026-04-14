package eu.nicosworld.capture;

import eu.nicosworld.model.GameState;
import eu.nicosworld.model.PieceAtPosition;
import eu.nicosworld.model.Board;

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