package eu.nicosworld.move;

import eu.nicosworld.model.*;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractMoveGenerator implements MoveGenerator {

    @Override
    public List<Move> generate(GameState state, PieceAtPosition pap) {

        Piece piece = pap.piece();
        Position from = pap.position();

        return switch (piece.getType()) {

            case CIRCLE -> generateCircleMoves(state, from);

            case TRIANGLE -> generateTriangleMoves(state, from);

            case SQUARE -> generateSquareMoves(state, from);

            case PYRAMID -> generatePyramidMoves(state, pap);
        };
    }

    protected boolean isInsideBoard(GameState state, Position p) {
        return state.getBoard().isInside(p);
    }

    protected List<Move> generatePyramidMoves(GameState state, PieceAtPosition pap) {

        List<Move> moves = new ArrayList<>();

        Pyramid pyramid = (Pyramid) pap.piece();
        Position from = pap.position();

        if (pyramid.hasCircle()) {
            moves.addAll(generateCircleMoves(state, from));
        }
        if (pyramid.hasTriangle()) {
            moves.addAll(generateTriangleMoves(state, from));
        }
        if (pyramid.hasSquare()) {
            moves.addAll(generateSquareMoves(state, from));
        }

        return moves;
    }

    // 🔥 hooks à implémenter
    protected abstract List<Move> generateCircleMoves(GameState state, Position from);

    protected abstract List<Move> generateTriangleMoves(GameState state, Position from);

    protected abstract List<Move> generateSquareMoves(GameState state, Position from);
}
