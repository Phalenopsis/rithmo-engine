package eu.nicosworld.rithmo.engine.capture.model;

import eu.nicosworld.rithmo.engine.capture.CaptureType;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PlayerColor;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.model.Pyramid;

/**
 * Represents a legal capture opportunity identified by the capture engine.
 *
 * <p>This record describes both the geometric and arithmetic conditions
 * under which a capture is possible, independently of when it is executed
 * (before or after movement).</p>
 *
 * <p>A capture can target either:
 * <ul>
 *   <li>an entire piece (standard case),</li>
 *   <li>or a specific component of a pyramid.</li>
 * </ul>
 *
 * <p>In all cases:
 * <ul>
 *   <li>{@code target} represents the piece currently on the board (e.g. a pyramid),</li>
 *   <li>{@code capturedPiece} represents what is actually removed (either the whole piece or one of its components).</li>
 * </ul>
 *
 * <p>Example:
 * <ul>
 *   <li>A circle 45 capturing a pyramid containing a triangle 45 will produce:</li>
 *   <ul>
 *     <li>{@code target = pyramid}</li>
 *     <li>{@code capturedPiece = triangle(45)}</li>
 *     <li>{@code isWholeCapture = false}</li>
 *   </ul>
 * </ul>
 *
 * @param attacker          the piece performing the capture
 * @param attackerPosition  the position of the attacker at the moment the capture is evaluated
 * @param target            the piece located on the board that is being targeted (may be a pyramid)
 * @param targetPosition    the position of the target piece
 * @param capturedPiece     the actual piece removed as a result of the capture
 * @param isWholeCapture    true if the entire {@code target} is captured, false if only a component is taken
 * @param type              the capture rule (e.g. ENCOUNTER, ASSAULT, etc.) that enables this capture
 */
public record CaptureAction(
        Piece attacker,
        Position attackerPosition,
        Piece target,
        Position targetPosition,
        Piece capturedPiece,
        boolean isWholeCapture,
        CaptureType type
) {
    /**
     * @return the color of the player performing the capture
     */
    public PlayerColor getActorColor() {
        return attacker.getPlayer().getColor();
    }

    /**
     * @return the color of the player whose piece is being target
     */
    public PlayerColor getCapturedColor() {
        return target.getPlayer().getColor();
    }

    public boolean isPartialCapture() {
        return !isWholeCapture;
    }

    @Override
    public String toString() {
        return "Capture[" +
                type +
                " | " +
                formatPiece(attacker) + "@" + attackerPosition +
                " -> " +
                formatCaptured() + "@" + targetPosition +
                "]";
    }

    private String formatCaptured() {
        if (isWholeCapture) {
            return formatPiece(target);
        }
        return formatPiece(capturedPiece) + " (partial)";
    }

    private String formatPiece(Piece piece) {
        String base = piece.getType().name();
        int value = piece.getValue();

        if (piece instanceof Pyramid pyramid) {
            String components = pyramid.getComponents().stream()
                    .map(p -> p.getType().name() + "(" + p.getValue() + ")")
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            return base + "(" + value + ")[" + components + "]";
        }

        return base + "(" + value + ")";
    }
}