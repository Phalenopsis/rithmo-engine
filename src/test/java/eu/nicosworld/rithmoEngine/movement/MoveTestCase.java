package eu.nicosworld.rithmoEngine.movement;

import eu.nicosworld.rithmoEngine.model.PieceType;
import eu.nicosworld.rithmoEngine.model.Position;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;

class MoveTestCase {

    private final PieceType type;
    private final Position from;
    private Position to;
    private final List<Position> obstacles = new ArrayList<>();
    private boolean expected;

    private MoveTestCase(PieceType type, int x, int y) {
        this.type = type;
        this.from = new Position(x, y);
    }

    // =========================
    // ENTRY POINTS
    // =========================
    static MoveTestCase triangleAt(int x, int y) {
        return new MoveTestCase(PieceType.TRIANGLE, x, y);
    }

    static MoveTestCase squareAt(int x, int y) {
        return new MoveTestCase(PieceType.SQUARE, x, y);
    }

    static MoveTestCase circleAt(int x, int y) {
        return new MoveTestCase(PieceType.CIRCLE, x, y);
    }

    static MoveTestCase pyramidAt(int x, int y) {
        return new MoveTestCase(PieceType.PYRAMID, x, y);
    }

    // =========================
    // DSL METHODS
    // =========================

    MoveTestCase to(int x, int y) {
        this.to = new Position(x, y);
        return this;
    }

    MoveTestCase blocked() {
        this.obstacles.add(this.to);
        return this;
    }

    MoveTestCase isValid() {
        this.expected = true;
        return this;
    }

    MoveTestCase isInvalid() {
        this.expected = false;
        return this;
    }

    MoveTestCase canReach(int x, int y) {
        return this.to(x, y).isValid();
    }

    MoveTestCase cannotReach(int x, int y) {
        return this.to(x, y).isInvalid();
    }

    MoveTestCase becauseOccupied() {
        this.obstacles.add(this.to);
        return this;
    }

    MoveTestCase becausePathBlockedAt(int x, int y) {
        this.obstacles.add(new Position(x, y));
        return this;
    }

    // =========================
    // CONVERSION
    // =========================

    Arguments toArguments() {
        return Arguments.of(type, from, to, obstacles, expected);
    }
}
