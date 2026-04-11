package eu.nicosworld.movement;

import eu.nicosworld.model.*;
import eu.nicosworld.move.IrregularMoveGenerator;
import eu.nicosworld.move.Move;
import eu.nicosworld.move.MovementEngine;
import eu.nicosworld.move.RegularMoveGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static eu.nicosworld.movement.MoveTestCase.squareAt;
import static eu.nicosworld.movement.MoveTestCase.triangleAt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GlobalMoveTest {
    static Stream<Arguments> globalMovesDSL() {
        return Stream.of(

                // =========================================================
                // TRIANGLE — should include BOTH regular and irregular
                // =========================================================

                // regular
                triangleAt(4,4).canReach(4,6),
                triangleAt(4,4).canReach(6,4),

                // irregular
                triangleAt(4,4).canReach(5,6),
                triangleAt(4,4).canReach(6,5),

                // invalid (should NOT appear)
                triangleAt(4,4).cannotReach(5,5),

                // =========================================================
                // SQUARE — mix regular + irregular
                // =========================================================

                // regular
                squareAt(4,4).canReach(4,7),
                squareAt(4,4).canReach(7,4),

                // irregular
                squareAt(4,4).canReach(5,7),
                squareAt(4,4).canReach(7,5),

                // invalid
                squareAt(4,4).cannotReach(6,6),

                // =========================================================
                // BLOCKING — should affect ONLY regular
                // =========================================================

                // path blocked → regular forbidden
                triangleAt(4,4)
                        .cannotReach(4,6)
                        .becausePathBlockedAt(4,5),

                // but irregular still allowed
                triangleAt(4,4)
                        .canReach(5,6),

                // =========================================================
                // DESTINATION OCCUPIED — blocks BOTH
                // =========================================================

                triangleAt(4,4)
                        .cannotReach(5,6)
                        .becauseOccupied(),

                triangleAt(4,4)
                        .cannotReach(4,6)
                        .becauseOccupied()

        ).map(MoveTestCase::toArguments);
    }

    @ParameterizedTest
    @MethodSource("globalMovesDSL")
    void global_moves(
            PieceType type,
            Position from,
            Position to,
            List<Position> obstacles,
            boolean expected
    ) {

        Board board = new Board(8, 8);
        Player player = new Player(PlayerColor.BLACK);

        Piece piece = new SimplePiece(type, player, 3);
        board.set(from, piece);

        for (Position p : obstacles) {
            board.set(p, new SimplePiece(PieceType.CIRCLE, player, 1));
        }

        GameState state = new GameState(board, player);

        MovementEngine engine = new MovementEngine();
        List<Move> moves = engine.generateMoves(
                state,
                new PieceAtPosition(piece, from)
        );

        boolean contains = moves.stream()
                .anyMatch(m -> m.from().equals(from) && m.to().equals(to));

        assertEquals(expected, contains);
    }

    @Test
    void should_not_generate_duplicate_moves() {

        Board board = new Board(8, 8);
        Player player = new Player(PlayerColor.BLACK);

        Piece triangle = new SimplePiece(PieceType.TRIANGLE, player, 3);
        Position from = new Position(4,4);

        board.set(from, triangle);

        GameState state = new GameState(board, player);
        MovementEngine engine = new MovementEngine();

        List<Move> moves = engine.generateMoves(
                state,
                new PieceAtPosition(triangle, from)
        );

        long distinctCount = moves.stream().distinct().count();

        assertEquals(distinctCount, moves.size());
    }

    @Test
    void global_moves_should_equal_union_of_generators() {

        Board board = new Board(8, 8);
        Player player = new Player(PlayerColor.BLACK);

        Piece piece = new SimplePiece(PieceType.TRIANGLE, player, 3);
        Position from = new Position(4, 4);

        board.set(from, piece);

        GameState state = new GameState(board, player);

        PieceAtPosition pap = new PieceAtPosition(piece, from);

        // =========================================
        // SYSTEM UNDER TEST
        // =========================================
        MovementEngine engine = new MovementEngine();
        List<Move> engineMoves = engine.generateMoves(state, pap);

        // =========================================
        // ORACLE (ground truth reconstruction)
        // =========================================
        List<Move> expected = new ArrayList<>();

        expected.addAll(new RegularMoveGenerator().generate(state, pap));
        expected.addAll(new IrregularMoveGenerator().generate(state, pap));

        // =========================================
        // ASSERTIONS
        // =========================================


        assertEquals(
                Set.copyOf(expected),
                Set.copyOf(engineMoves)
        );

        assertEquals(expected.size(), engineMoves.size(), "wrong number of moves");
        assertTrue(engineMoves.containsAll(expected), "missing expected moves");
        assertTrue(expected.containsAll(engineMoves), "unexpected moves generated");
    }
}
