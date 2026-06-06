package eu.nicosworld.rithmo.engine.capture.model;

import eu.nicosworld.rithmo.engine.model.Piece;

/**
 * Represents a potential capture target in any capture rule.
 *
 * <p>A CaptureTarget is uniquely identified by the underlying Piece (UUID-based identity).
 *
 * <p>It can represent either:
 *
 * <ul>
 *   <li>a whole piece capture
 *   <li>a partial capture (component of a pyramid)
 * </ul>
 */
public record CaptureTarget(Piece piece, int value, boolean isWholePiece) {}
