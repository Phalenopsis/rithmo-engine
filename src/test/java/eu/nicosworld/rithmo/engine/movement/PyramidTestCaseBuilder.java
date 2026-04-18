package eu.nicosworld.rithmo.engine.movement;

import eu.nicosworld.rithmo.engine.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder dedicated to constructing Pyramid test fixtures.
 * <p>
 * Avoids duplication of Pyramid composition in test classes.
 * </p>
 */
class PyramidTestCaseBuilder {

    private final Player player;
    private final List<SimplePiece> components = new ArrayList<>();

    private Position position = new Position(4, 4);

    private PyramidTestCaseBuilder(Player player) {
        this.player = player;
    }

    // =========================================================
    // ENTRY
    // =========================================================

    static PyramidTestCaseBuilder forPlayer(Player player) {
        return new PyramidTestCaseBuilder(player);
    }

    // =========================================================
    // POSITION
    // =========================================================

    PyramidTestCaseBuilder at(int x, int y) {
        this.position = new Position(x, y);
        return this;
    }

    // =========================================================
    // COMPONENTS
    // =========================================================

    PyramidTestCaseBuilder full() {
        components.clear();

        components.add(new SimplePiece(PieceType.SQUARE, player, 36));
        components.add(new SimplePiece(PieceType.SQUARE, player, 25));

        components.add(new SimplePiece(PieceType.TRIANGLE, player, 16));
        components.add(new SimplePiece(PieceType.TRIANGLE, player, 9));

        components.add(new SimplePiece(PieceType.CIRCLE, player, 4));
        components.add(new SimplePiece(PieceType.CIRCLE, player, 1));

        return this;
    }

    PyramidTestCaseBuilder withoutTriangle() {
        components.removeIf(p -> p.getType() == PieceType.TRIANGLE);
        return this;
    }

    PyramidTestCaseBuilder minimalCirclesOnly() {
        components.clear();

        components.add(new SimplePiece(PieceType.CIRCLE, player, 4));
        components.add(new SimplePiece(PieceType.CIRCLE, player, 1));

        return this;
    }

    // =========================================================
    // BUILD
    // =========================================================

    Pyramid build() {
        return new Pyramid(player, List.copyOf(components));
    }

    PieceAtPosition buildAt() {
        return new PieceAtPosition(build(), position);
    }

    Position position() {
        return position;
    }
}