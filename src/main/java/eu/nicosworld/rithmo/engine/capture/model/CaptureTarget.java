package eu.nicosworld.rithmo.engine.capture.model;

import eu.nicosworld.rithmo.engine.model.Piece;

/**
 * Represents a potential capture target in any capture rule.
 *
 * <p>A CaptureTarget is uniquely identified by the underlying Piece (UUID-based identity).</p>
 * <p>It can represent either:
 * <ul>
 *     <li>a whole piece capture</li>
 *     <li>a partial capture (component of a pyramid)</li>
 * </ul>
 */
public record CaptureTarget(
        Piece piece,
        int value,
        boolean isWholePiece
) {
}