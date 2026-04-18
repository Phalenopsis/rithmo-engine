package eu.nicosworld.rithmoEngine.movement;

import eu.nicosworld.rithmoEngine.model.*;
import eu.nicosworld.rithmoEngine.move.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GlobalMoveTest extends MovementTestBase {

    // =========================================================
    // DSL
    // =========================================================
    static Stream<SimpleMoveTestCase> globalMovesDSL() {
        return Stream.of(

                // TRIANGLE
                SimpleMoveTestCase.triangleAt(4,4).canReach(4,6),
                SimpleMoveTestCase.triangleAt(4,4).canReach(6,4),

                SimpleMoveTestCase.triangleAt(4,4).canReach(5,6),
                SimpleMoveTestCase.triangleAt(4,4).canReach(6,5),

                SimpleMoveTestCase.triangleAt(4,4).cannotReach(5,5),

                // SQUARE
                SimpleMoveTestCase.squareAt(4,4).canReach(4,7),
                SimpleMoveTestCase.squareAt(4,4).canReach(7,4),

                SimpleMoveTestCase.squareAt(4,4).canReach(5,7),
                SimpleMoveTestCase.squareAt(4,4).canReach(7,5),

                SimpleMoveTestCase.squareAt(4,4).cannotReach(6,6),

                // BLOCKING (regular only blocked)
                SimpleMoveTestCase.triangleAt(4,4)
                        .cannotReach(4,6)
                        .becausePathBlockedAt(4,5),

                SimpleMoveTestCase.triangleAt(4,4)
                        .canReach(5,6),

                // OCCUPIED (blocks everything)
                SimpleMoveTestCase.triangleAt(4,4)
                        .cannotReach(5,6)
                        .becauseOccupied(),

                SimpleMoveTestCase.triangleAt(4,4)
                        .cannotReach(4,6)
                        .becauseOccupied()
        );
    }

    // =========================================================
    // TEST ENGINE OUTPUT
    // =========================================================
    @ParameterizedTest
    @MethodSource("globalMovesDSL")
    void global_moves(SimpleMoveTestCase tc) {

        Player player = new Player(PlayerColor.BLACK);

        Piece piece = TestPieceFactory.create(tc.type(), player);

        Board board = new Board(8, 8);
        board = board.addPiece(tc.from(), piece);

        for (Position p : tc.obstacles()) {
            board = board.addPiece(p, new SimplePiece(PieceType.CIRCLE, player, 1));
        }

        GameState state = new GameState(board, player);

        List<Move> moves = engine.generateMoves(
                state,
                new PieceAtPosition(piece, tc.from())
        );

        boolean contains = moves.stream()
                .anyMatch(m -> m.from().equals(tc.from()) && m.to().equals(tc.to()));

        assertEquals(tc.expected(), contains);
    }

    // =========================================================
    // NO DUPLICATES
    // =========================================================
    @Test
    void should_not_generate_duplicate_moves() {

        Player player = new Player(PlayerColor.BLACK);
        Piece triangle = new SimplePiece(PieceType.TRIANGLE, player, 3);

        Board board = new Board(8, 8);
        Position from = new Position(4,4);

        board = board.addPiece(from, triangle);

        GameState state = new GameState(board, player);

        List<Move> moves = engine.generateMoves(
                state,
                new PieceAtPosition(triangle, from)
        );

        assertEquals(moves.size(), moves.stream().distinct().count());
    }

    // =========================================================
    // ORACLE TEST (engine correctness)
    // =========================================================
    @Test
    void global_moves_should_equal_union_of_generators() {

        Player player = new Player(PlayerColor.BLACK);

        Piece piece = new SimplePiece(PieceType.TRIANGLE, player, 3);
        Position from = new Position(4,4);

        Board board = new Board(8, 8);
        board = board.addPiece(from, piece);

        GameState state = new GameState(board, player);

        PieceAtPosition pap = new PieceAtPosition(piece, from);

        List<Move> engineMoves = engine.generateMoves(state, pap);

        List<Move> expected = new java.util.ArrayList<>();
        expected.addAll(new RegularMoveGenerator().generate(state, pap));
        expected.addAll(new IrregularMoveGenerator().generate(state, pap));

        assertEquals(Set.copyOf(expected), Set.copyOf(engineMoves));

        assertEquals(expected.size(), engineMoves.size());
        assertTrue(engineMoves.containsAll(expected));
        assertTrue(expected.containsAll(engineMoves));
    }
}