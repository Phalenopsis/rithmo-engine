package eu.nicosworld.rithmoEngine.movement;

import eu.nicosworld.rithmoEngine.model.*;
import eu.nicosworld.rithmoEngine.move.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PyramidMoveTest extends MovementTestBase {

    // =========================================================
    // FULL PYRAMID (circle + triangle + square abilities)
    // =========================================================
    static Stream<Arguments> fullPyramidMoves() {
        return Stream.of(

                // -------------------------
                // CIRCLE moves (diagonal 1)
                // -------------------------
                MoveTestCase.pyramidAt(4,4).canReach(3,3),
                MoveTestCase.pyramidAt(4,4).canReach(5,5),
                MoveTestCase.pyramidAt(4,4).canReach(3,5),
                MoveTestCase.pyramidAt(4,4).canReach(5,3),

                // -------------------------
                // TRIANGLE moves (orthogonal 2)
                // -------------------------
                MoveTestCase.pyramidAt(4,4).canReach(4,6),
                MoveTestCase.pyramidAt(4,4).canReach(6,4),
                MoveTestCase.pyramidAt(4,4).canReach(4,2),
                MoveTestCase.pyramidAt(4,4).canReach(2,4),

                // -------------------------
                // SQUARE moves (orthogonal 3)
                // -------------------------
                MoveTestCase.pyramidAt(4,4).canReach(4,7),
                MoveTestCase.pyramidAt(4,4).canReach(7,4),
                MoveTestCase.pyramidAt(4,4).canReach(4,1),
                MoveTestCase.pyramidAt(4,4).canReach(1,4)
        ).map(MoveTestCase::toArguments);
    }

    @ParameterizedTest
    @MethodSource("fullPyramidMoves")
    void full_pyramid_moves(
            PieceType type,
            Position from,
            Position to,
            List<Position> obstacles,
            boolean expected
    ) {
        Player player = new Player(PlayerColor.BLACK);
        SimplePiece square36 = new SimplePiece(PieceType.SQUARE, player, 36);
        SimplePiece square25 = new SimplePiece(PieceType.SQUARE, player, 25);
        SimplePiece triangle16 = new SimplePiece(PieceType.TRIANGLE, player, 16);
        SimplePiece triangle9 = new SimplePiece(PieceType.TRIANGLE, player, 9);
        SimplePiece circle4 = new SimplePiece(PieceType.CIRCLE, player, 4);
        SimplePiece circle1 = new SimplePiece(PieceType.CIRCLE, player, 1);

        Pyramid pyramid = new Pyramid(type, player, List.of(square36, square25, triangle16, triangle9, circle4, circle1));

        assertMoveResult(pyramid, from, to, obstacles, expected, MoveNature.REGULAR);
    }

    // =========================================================
    // PYRAMID WITHOUT TRIANGLE
    // =========================================================
    static Stream<Arguments> pyramidWithoutTriangleMoves() {
        return Stream.of(

                // circle still works
                MoveTestCase.pyramidAt(4,4).canReach(3,3),
                MoveTestCase.pyramidAt(4,4).canReach(5,5),

                // triangle moves disabled
                MoveTestCase.pyramidAt(4,4).cannotReach(4,6),
                MoveTestCase.pyramidAt(4,4).cannotReach(6,4),

                // square still works
                MoveTestCase.pyramidAt(4,4).canReach(4,7),
                MoveTestCase.pyramidAt(4,4).canReach(7,4)
        ).map(MoveTestCase::toArguments);
    }

    @ParameterizedTest
    @MethodSource("pyramidWithoutTriangleMoves")
    void pyramid_without_triangle(
            PieceType type,
            Position from,
            Position to,
            List<Position> obstacles,
            boolean expected
    ) {
        Player player = new Player(PlayerColor.BLACK);
        SimplePiece square36 = new SimplePiece(PieceType.SQUARE, player, 36);
        SimplePiece square25 = new SimplePiece(PieceType.SQUARE, player, 25);
        SimplePiece circle4 = new SimplePiece(PieceType.CIRCLE, player, 4);
        SimplePiece circle1 = new SimplePiece(PieceType.CIRCLE, player, 1);

        Pyramid pyramid = new Pyramid(type, player, List.of(square36, square25, circle4, circle1));

        assertMoveResult(pyramid, from, to, obstacles, expected, MoveNature.REGULAR);
    }

    // =========================================================
    // PYRAMID WITH ONLY 2 CIRCLES (VERY LIMITED)
    // =========================================================
    static Stream<Arguments> pyramidMinimalMoves() {
        return Stream.of(

                // only circle-like moves remain
                MoveTestCase.pyramidAt(4,4).canReach(3,3),
                MoveTestCase.pyramidAt(4,4).canReach(5,5),

                // everything else forbidden
                MoveTestCase.pyramidAt(4,4).cannotReach(4,6),
                MoveTestCase.pyramidAt(4,4).cannotReach(4,7)
        ).map(MoveTestCase::toArguments);
    }

    @ParameterizedTest
    @MethodSource("pyramidMinimalMoves")
    void pyramid_minimal(
            PieceType type,
            Position from,
            Position to,
            List<Position> obstacles,
            boolean expected
    ) {
        Player player = new Player(PlayerColor.BLACK);
        SimplePiece circle4 = new SimplePiece(PieceType.CIRCLE, player, 4);
        SimplePiece circle1 = new SimplePiece(PieceType.CIRCLE, player, 1);

        Pyramid pyramid = new Pyramid(type, player, List.of(circle4, circle1));

        assertMoveResult(pyramid, from, to, obstacles, expected, MoveNature.REGULAR);
    }

    static Stream<Arguments> fullPyramidIrregularMoves() {
        return Stream.of(

                // =========================================================
                // TRIANGLE IRREGULAR MOVES
                // =========================================================
                MoveTestCase.pyramidAt(4,4).canReach(5,6),
                MoveTestCase.pyramidAt(4,4).canReach(6,5),
                MoveTestCase.pyramidAt(4,4).canReach(6,3),
                MoveTestCase.pyramidAt(4,4).canReach(5,2),
                MoveTestCase.pyramidAt(4,4).canReach(3,2),
                MoveTestCase.pyramidAt(4,4).canReach(2,3),
                MoveTestCase.pyramidAt(4,4).canReach(2,5),
                MoveTestCase.pyramidAt(4,4).canReach(3,6),

                // =========================================================
                // SQUARE IRREGULAR MOVES
                // =========================================================
                MoveTestCase.pyramidAt(4,4).canReach(5,7),
                MoveTestCase.pyramidAt(4,4).canReach(7,5),
                MoveTestCase.pyramidAt(4,4).canReach(7,3),
                MoveTestCase.pyramidAt(4,4).canReach(5,1),
                MoveTestCase.pyramidAt(4,4).canReach(3,1),
                MoveTestCase.pyramidAt(4,4).canReach(1,3),
                MoveTestCase.pyramidAt(4,4).canReach(1,5),
                MoveTestCase.pyramidAt(4,4).canReach(3,7)
        ).map(MoveTestCase::toArguments);
    }

    @ParameterizedTest
    @MethodSource("fullPyramidIrregularMoves")
    void full_pyramid_irregular_moves(
            PieceType type,
            Position from,
            Position to,
            List<Position> obstacles,
            boolean expected
    ) {
        Player player = new Player(PlayerColor.BLACK);

        Pyramid pyramid = new Pyramid(
                type,
                player,
                List.of(
                        new SimplePiece(PieceType.SQUARE, player, 36),
                        new SimplePiece(PieceType.SQUARE, player, 25),
                        new SimplePiece(PieceType.TRIANGLE, player, 16),
                        new SimplePiece(PieceType.TRIANGLE, player, 9),
                        new SimplePiece(PieceType.CIRCLE, player, 4),
                        new SimplePiece(PieceType.CIRCLE, player, 1)
                )
        );

        assertMoveResult(
                pyramid,
                from,
                to,
                obstacles,
                expected,
                MoveNature.IRREGULAR
        );
    }

    static Stream<Arguments> pyramidWithoutTriangleIrregularMoves() {
        return Stream.of(

                // square still active
                MoveTestCase.pyramidAt(4,4).canReach(5,7),
                MoveTestCase.pyramidAt(4,4).canReach(7,5),

                // triangle disabled
                MoveTestCase.pyramidAt(4,4).cannotReach(5,6),
                MoveTestCase.pyramidAt(4,4).cannotReach(6,5)

        ).map(MoveTestCase::toArguments);
    }

    @ParameterizedTest
    @MethodSource("pyramidWithoutTriangleIrregularMoves")
    void pyramid_without_triangle_irregular(
            PieceType type,
            Position from,
            Position to,
            List<Position> obstacles,
            boolean expected
    ) {
        Player player = new Player(PlayerColor.BLACK);

        Pyramid pyramid = new Pyramid(
                type,
                player,
                List.of(
                        new SimplePiece(PieceType.SQUARE, player, 36),
                        new SimplePiece(PieceType.SQUARE, player, 25),
                        new SimplePiece(PieceType.CIRCLE, player, 4),
                        new SimplePiece(PieceType.CIRCLE, player, 1)
                )
        );

        assertMoveResult(
                pyramid,
                from,
                to,
                obstacles,
                expected,
                MoveNature.IRREGULAR
        );
    }

    static Stream<Arguments> pyramidMinimalIrregularMoves() {
        return Stream.of(

                // no triangle, no square irregular
                MoveTestCase.pyramidAt(4,4).cannotReach(5,6),
                MoveTestCase.pyramidAt(4,4).cannotReach(7,5),

                // circle has no irregular moves
                MoveTestCase.pyramidAt(4,4).cannotReach(3,3)

        ).map(MoveTestCase::toArguments);
    }

    @ParameterizedTest
    @MethodSource("pyramidMinimalIrregularMoves")
    void pyramid_minimal_irregular(
            PieceType type,
            Position from,
            Position to,
            List<Position> obstacles,
            boolean expected
    ) {
        Player player = new Player(PlayerColor.BLACK);

        Pyramid pyramid = new Pyramid(
                type,
                player,
                List.of(
                        new SimplePiece(PieceType.CIRCLE, player, 4),
                        new SimplePiece(PieceType.CIRCLE, player, 1)
                )
        );

        assertMoveResult(
                pyramid,
                from,
                to,
                obstacles,
                expected,
                MoveNature.IRREGULAR
        );
    }

    @Test
    void pyramid_moves_should_be_unique_and_match_union_of_generators() {

        Board board = new Board(8, 8);
        Player player = new Player(PlayerColor.BLACK);

        Position from = new Position(4, 4);

        Pyramid pyramid = new Pyramid(
                PieceType.PYRAMID,
                player,
                List.of(
                        new SimplePiece(PieceType.SQUARE, player, 36),
                        new SimplePiece(PieceType.SQUARE, player, 25),
                        new SimplePiece(PieceType.TRIANGLE, player, 16),
                        new SimplePiece(PieceType.TRIANGLE, player, 9),
                        new SimplePiece(PieceType.CIRCLE, player, 4),
                        new SimplePiece(PieceType.CIRCLE, player, 1)
                )
        );

        board.set(from, pyramid);

        GameState state = new GameState(board, player);
        PieceAtPosition pap = new PieceAtPosition(pyramid, from);

        MovementEngine engine = new MovementEngine();

        List<Move> engineMoves = engine.generateMoves(state, pap);

        // =========================================
        // ORACLE
        // =========================================
        List<Move> expected = new ArrayList<>();

        expected.addAll(new RegularMoveGenerator().generate(state, pap));
        expected.addAll(new IrregularMoveGenerator().generate(state, pap));

        // =========================================
        // ASSERTIONS
        // =========================================

        // no duplicates
        assertEquals(
                engineMoves.size(),
                Set.copyOf(engineMoves).size(),
                "Duplicate moves detected"
        );

        // equality ignoring order
        assertEquals(Set.copyOf(expected), Set.copyOf(engineMoves));

        // strict checks
        assertEquals(expected.size(), engineMoves.size(), "wrong number of moves");
        assertTrue(engineMoves.containsAll(expected), "missing moves");
        assertTrue(expected.containsAll(engineMoves), "unexpected moves");
    }
}