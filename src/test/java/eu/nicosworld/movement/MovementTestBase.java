package eu.nicosworld.movement;

import eu.nicosworld.model.*;
import eu.nicosworld.move.Move;
import eu.nicosworld.move.MoveNature;
import eu.nicosworld.move.MovementEngine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class MovementTestBase {

    protected MovementEngine engine = new MovementEngine();

    void assertMoveResult(PieceType type,
                          Position from,
                          Position to,
                          List<Position> obstacles,
                          boolean expected,
                          MoveNature nature) {
        Board board = new Board(8, 8);
        Player player = new Player(PlayerColor.BLACK);

        Piece pieceToMove = new SimplePiece(type, player, 3);

        board.set(from, pieceToMove);

        for (Position p : obstacles) {
            board.set(p, new SimplePiece(PieceType.CIRCLE, player, 1));
        }

        GameState state = new GameState(board, player);

        List<Move> moves = engine.generateMoves(state,
                new PieceAtPosition(pieceToMove, from)
        );

        boolean contains = moves.contains(
                new Move(from, to, nature)
        );

        assertEquals(expected, contains);
    }

    void assertMoveResult(Pyramid pyramid,
                          Position from,
                          Position to,
                          List<Position> obstacles,
                          boolean expected,
                          MoveNature nature) {
        Board board = new Board(8, 8);
        Player player = new Player(PlayerColor.BLACK);

        board.set(from, pyramid);

        for (Position p : obstacles) {
            board.set(p, new SimplePiece(PieceType.CIRCLE, player, 1));
        }

        GameState state = new GameState(board, player);

        List<Move> moves = engine.generateMoves(state,
                new PieceAtPosition(pyramid, from)
        );

        boolean contains = moves.contains(
                new Move(from, to, nature)
        );

        assertEquals(expected, contains);
    }
}
