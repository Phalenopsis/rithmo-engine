package eu.nicosworld.rithmo.engine.movement;

import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.move.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PyramidMoveTest extends MovementTestBase {

    private Player player() {
        return new Player(PlayerColor.BLACK);
    }

    // =========================================================
    // FULL PYRAMID - REGULAR MOVES
    // =========================================================
    static Stream<Arguments> fullPyramidMoves() {
        return Stream.of(

                // Circle (diagonals 1)
                Arguments.of(3,3, true),
                Arguments.of(5,5, true),
                Arguments.of(3,5, true),
                Arguments.of(5,3, true),

                // Triangle (orthogonal 2)
                Arguments.of(4,6, true),
                Arguments.of(6,4, true),
                Arguments.of(4,2, true),
                Arguments.of(2,4, true),

                // Square (orthogonal 3)
                Arguments.of(4,7, true),
                Arguments.of(7,4, true),
                Arguments.of(4,1, true),
                Arguments.of(1,4, true)

        );
    }

    @ParameterizedTest
    @MethodSource("fullPyramidMoves")
    void full_pyramid_moves(int toX, int toY, boolean expected) {

        Player player = player();

        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(player)
                .full()
                .at(4, 4)
                .build();

        assertMoveResult(
                pyramid,
                new Position(4, 4),
                new Position(toX, toY),
                List.of(),
                expected,
                MoveNature.REGULAR
        );
    }

    // =========================================================
    // PYRAMID WITHOUT TRIANGLE
    // =========================================================
    static Stream<Arguments> pyramidWithoutTriangleMoves() {
        return Stream.of(

                Arguments.of(3,3, true),
                Arguments.of(5,5, true),

                Arguments.of(4,6, false),
                Arguments.of(6,4, false),

                Arguments.of(4,7, true),
                Arguments.of(7,4, true)

        );
    }

    @ParameterizedTest
    @MethodSource("pyramidWithoutTriangleMoves")
    void pyramid_without_triangle(int toX, int toY, boolean expected) {

        Player player = player();

        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(player)
                .full()
                .withoutTriangle()
                .at(4, 4)
                .build();

        assertMoveResult(
                pyramid,
                new Position(4, 4),
                new Position(toX, toY),
                List.of(),
                expected,
                MoveNature.REGULAR
        );
    }

    // =========================================================
    // MINIMAL PYRAMID
    // =========================================================
    static Stream<Arguments> pyramidMinimalMoves() {
        return Stream.of(

                Arguments.of(3,3, true),
                Arguments.of(5,5, true),

                Arguments.of(4,6, false),
                Arguments.of(4,7, false)

        );
    }

    @ParameterizedTest
    @MethodSource("pyramidMinimalMoves")
    void pyramid_minimal(int toX, int toY, boolean expected) {

        Player player = player();

        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(player)
                .minimalCirclesOnly()
                .at(4, 4)
                .build();

        assertMoveResult(
                pyramid,
                new Position(4, 4),
                new Position(toX, toY),
                List.of(),
                expected,
                MoveNature.REGULAR
        );
    }

    // =========================================================
    // FULL PYRAMID - IRREGULAR MOVES
    // =========================================================
    static Stream<Arguments> fullPyramidIrregularMoves() {
        return Stream.of(

                Arguments.of(5,6, true),
                Arguments.of(6,5, true),
                Arguments.of(6,3, true),
                Arguments.of(5,2, true),
                Arguments.of(3,2, true),
                Arguments.of(2,3, true),
                Arguments.of(2,5, true),
                Arguments.of(3,6, true),

                Arguments.of(5,7, true),
                Arguments.of(7,5, true),
                Arguments.of(7,3, true),
                Arguments.of(5,1, true),
                Arguments.of(3,1, true),
                Arguments.of(1,3, true),
                Arguments.of(1,5, true),
                Arguments.of(3,7, true)

        );
    }

    @ParameterizedTest
    @MethodSource("fullPyramidIrregularMoves")
    void full_pyramid_irregular(int toX, int toY, boolean expected) {

        Player player = player();

        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(player)
                .full()
                .at(4, 4)
                .build();

        assertMoveResult(
                pyramid,
                new Position(4, 4),
                new Position(toX, toY),
                List.of(),
                expected,
                MoveNature.IRREGULAR
        );
    }

    // =========================================================
    // PYRAMID WITHOUT TRIANGLE - IRREGULAR
    // =========================================================
    static Stream<Arguments> pyramidWithoutTriangleIrregularMoves() {
        return Stream.of(

                Arguments.of(5,7, true),
                Arguments.of(7,5, true),

                Arguments.of(5,6, false),
                Arguments.of(6,5, false)

        );
    }

    @ParameterizedTest
    @MethodSource("pyramidWithoutTriangleIrregularMoves")
    void pyramid_without_triangle_irregular(int toX, int toY, boolean expected) {

        Player player = player();

        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(player)
                .full()
                .withoutTriangle()
                .at(4, 4)
                .build();

        assertMoveResult(
                pyramid,
                new Position(4, 4),
                new Position(toX, toY),
                List.of(),
                expected,
                MoveNature.IRREGULAR
        );
    }

    // =========================================================
    // MINIMAL IRREGULAR
    // =========================================================
    static Stream<Arguments> pyramidMinimalIrregularMoves() {
        return Stream.of(

                Arguments.of(5,6, false),
                Arguments.of(7,5, false),
                Arguments.of(3,3, false)

        );
    }

    @ParameterizedTest
    @MethodSource("pyramidMinimalIrregularMoves")
    void pyramid_minimal_irregular(int toX, int toY, boolean expected) {

        Player player = player();

        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(player)
                .minimalCirclesOnly()
                .at(4, 4)
                .build();

        assertMoveResult(
                pyramid,
                new Position(4, 4),
                new Position(toX, toY),
                List.of(),
                expected,
                MoveNature.IRREGULAR
        );
    }

    // =========================================================
    // UNIQUENESS TEST
    // =========================================================
    @Test
    void pyramid_moves_should_be_unique_and_match_union_of_generators() {

        Player player = player();

        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(player)
                .full()
                .at(4, 4)
                .build();

        Board board = new Board(8, 8);
        Position from = new Position(4, 4);

        board = board.addPiece(from, pyramid);

        GameState state = new GameState(board, player);
        PieceAtPosition pap = new PieceAtPosition(pyramid, from);

        MovementEngine engine = new MovementEngine();

        List<Move> engineMoves = engine.generateMoves(state, pap);

        List<Move> expected = new ArrayList<>();
        expected.addAll(new RegularMoveGenerator().generate(state, pap));
        expected.addAll(new IrregularMoveGenerator().generate(state, pap));

        assertEquals(engineMoves.size(), Set.copyOf(engineMoves).size());
        assertEquals(Set.copyOf(expected), Set.copyOf(engineMoves));
        assertEquals(expected.size(), engineMoves.size());
        assertTrue(engineMoves.containsAll(expected));
        assertTrue(expected.containsAll(engineMoves));
    }
}