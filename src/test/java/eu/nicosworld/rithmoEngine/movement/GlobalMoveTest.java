package eu.nicosworld.rithmoEngine.movement;

import eu.nicosworld.rithmoEngine.model.*;
import eu.nicosworld.rithmoEngine.move.IrregularMoveGenerator;
import eu.nicosworld.rithmoEngine.move.Move;
import eu.nicosworld.rithmoEngine.move.MovementEngine;
import eu.nicosworld.rithmoEngine.move.RegularMoveGenerator;
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

public class GlobalMoveTest extends MovementTestBase {
    static Stream<Arguments> globalMovesDSL() {
        return Stream.of(

                // =========================================================
                // TRIANGLE — should include BOTH regular and irregular
                // =========================================================

                // regular
                MoveTestCase.triangleAt(4,4).canReach(4,6),
                MoveTestCase.triangleAt(4,4).canReach(6,4),

                // irregular
                MoveTestCase.triangleAt(4,4).canReach(5,6),
                MoveTestCase.triangleAt(4,4).canReach(6,5),

                // invalid (should NOT appear)
                MoveTestCase.triangleAt(4,4).cannotReach(5,5),

                // =========================================================
                // SQUARE — mix regular + irregular
                // =========================================================

                // regular
                MoveTestCase.squareAt(4,4).canReach(4,7),
                MoveTestCase.squareAt(4,4).canReach(7,4),

                // irregular
                MoveTestCase.squareAt(4,4).canReach(5,7),
                MoveTestCase.squareAt(4,4).canReach(7,5),

                // invalid
                MoveTestCase.squareAt(4,4).cannotReach(6,6),

                // =========================================================
                // BLOCKING — should affect ONLY regular
                // =========================================================

                // path blocked → regular forbidden
                MoveTestCase.triangleAt(4,4)
                        .cannotReach(4,6)
                        .becausePathBlockedAt(4,5),

                // but irregular still allowed
                MoveTestCase.triangleAt(4,4)
                        .canReach(5,6),

                // =========================================================
                // DESTINATION OCCUPIED — blocks BOTH
                // =========================================================

                MoveTestCase.triangleAt(4,4)
                        .cannotReach(5,6)
                        .becauseOccupied(),

                MoveTestCase.triangleAt(4,4)
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
        board = board.addPiece(from, piece);

        for (Position p : obstacles) {
            board = board.addPiece(p, new SimplePiece(PieceType.CIRCLE, player, 1));
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

        board = board.addPiece(from, triangle);

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

        board = board.addPiece(from, piece);

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
