package eu.nicosworld.rithmo.engine.movement;

import eu.nicosworld.rithmo.engine.model.PieceType;
import eu.nicosworld.rithmo.engine.model.Position;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;

/**
 * Lightweight DSL for movement test definitions.
 * Used to declare expected move behaviours in a readable way.
 */
public class SimpleMoveTestCase {

    private final PieceType type;
    private final Position from;

    private Position to;
    private final List<Position> obstacles = new ArrayList<>();
    private boolean expected;

    private SimpleMoveTestCase(PieceType type, int x, int y) {
        this.type = type;
        this.from = new Position(x, y);
    }

    // =========================================================
    // ENTRY POINTS
    // =========================================================

    public static SimpleMoveTestCase triangleAt(int x, int y) {
        return new SimpleMoveTestCase(PieceType.TRIANGLE, x, y);
    }

    public static SimpleMoveTestCase squareAt(int x, int y) {
        return new SimpleMoveTestCase(PieceType.SQUARE, x, y);
    }

    public static SimpleMoveTestCase circleAt(int x, int y) {
        return new SimpleMoveTestCase(PieceType.CIRCLE, x, y);
    }

    // ⚠️ IMPORTANT : volontairement supprimé
    // public static SimpleMoveTestCase pyramidAt(...) ❌

    // =========================================================
    // DSL
    // =========================================================

    public SimpleMoveTestCase to(int x, int y) {
        this.to = new Position(x, y);
        return this;
    }

    public SimpleMoveTestCase expect(boolean value) {
        this.expected = value;
        return this;
    }

    public SimpleMoveTestCase canReach(int x, int y) {
        return to(x, y).expect(true);
    }

    public SimpleMoveTestCase cannotReach(int x, int y) {
        return to(x, y).expect(false);
    }

    public SimpleMoveTestCase becauseOccupied() {
        this.obstacles.add(this.to);
        return this;
    }

    public SimpleMoveTestCase becausePathBlockedAt(int x, int y) {
        this.obstacles.add(new Position(x, y));
        return this;
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public PieceType type() {
        return type;
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }

    public List<Position> obstacles() {
        return obstacles;
    }

    public boolean expected() {
        return expected;
    }

    // =========================================================
    // CONVERSION
    // =========================================================

    public Arguments toArguments() {
        return Arguments.of(type, from, to, obstacles, expected);
    }
}