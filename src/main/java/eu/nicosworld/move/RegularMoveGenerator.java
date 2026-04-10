package eu.nicosworld.move;

import eu.nicosworld.model.*;

import java.util.ArrayList;
import java.util.List;

public class RegularMoveGenerator extends AbstractMoveGenerator {
    private final int CIRCLE_MOVE_DISTANCE = 1;
    private final int TRIANGLE_MOVE_DISTANCE = 2;
    private final int SQUARE_MOVE_DISTANCE = 3;

    public List<Move> generateCircleMoves(GameState state, Position from) {
        return generateDiagonal(state, from, CIRCLE_MOVE_DISTANCE);
    }

    public List<Move> generateTriangleMoves(GameState state, Position from) {
        return generateOrthogonal(state, from, TRIANGLE_MOVE_DISTANCE);
    }

    public List<Move> generateSquareMoves(GameState state, Position from) {
        return generateOrthogonal(state, from, SQUARE_MOVE_DISTANCE);
    }

    private List<Move> generateOrthogonal(GameState state, Position from, int dist) {

        int[][] dirs = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        return generateLineMoves(state, from, dirs, dist);
    }

    private List<Move> generateDiagonal(GameState state, Position from, int dist) {

        int[][] dirs = {
                {1, 1},
                {1, -1},
                {-1, 1},
                {-1, -1}
        };

        return generateLineMoves(state, from, dirs, dist);
    }

    private Position add(Position p, int dx, int dy) {
        return new Position(p.getX() + dx, p.getY() + dy);
    }

    private List<Move> generateLineMoves(
            GameState state,
            Position from,
            int[][] directions,
            int dist
    ) {

        List<Move> moves = new ArrayList<>();

        for (int[] d : directions) {

            Position to = add(from, d[0] * dist, d[1] * dist);

            if (!isInsideBoard(state, to)) continue;

            if (isBlocked(state, from, d, dist)) continue;

            if (state.getBoard().isEmpty(to)) {
                moves.add(new Move(from, to, MoveNature.REGULAR));
            }
        }

        return moves;
    }

    private boolean isBlocked(GameState state, Position from, int[] d, int dist) {

        for (int step = 1; step < dist; step++) {

            Position p = add(from, d[0] * step, d[1] * step);

            if (!state.getBoard().isEmpty(p)) {
                return true;
            }
        }

        return false;
    }
}