package eu.nicosworld.move;

import eu.nicosworld.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link AbstractMoveGenerator} that defines
 * the geometric reach of standard (regular) movement rules.
 *
 * <p>This generator computes "potential" moves based on fixed geometric patterns.
 * <b>Note:</b> This class only validates board boundaries. It does NOT check
 * if the path is blocked or if the destination is occupied; these concerns
 * are delegated to the {@link MovementEngine}.</p>
 *
 * <p>Move distances (Theoretical Reach):</p>
 * <ul>
 * <li>Circle → 1 step (Diagonal)</li>
 * <li>Triangle → 2 steps (Orthogonal)</li>
 * <li>Square → 3 steps (Orthogonal)</li>
 * </ul>
 */
public class RegularMoveGenerator extends AbstractMoveGenerator {

    private final int CIRCLE_MOVE_DISTANCE = 1;
    private final int TRIANGLE_MOVE_DISTANCE = 2;
    private final int SQUARE_MOVE_DISTANCE = 3;

    @Override
    public List<Move> generateCircleMoves(GameState state, Position from) {
        return generateDiagonal(state, from, CIRCLE_MOVE_DISTANCE);
    }

    @Override
    public List<Move> generateTriangleMoves(GameState state, Position from) {
        return generateOrthogonal(state, from, TRIANGLE_MOVE_DISTANCE);
    }

    @Override
    public List<Move> generateSquareMoves(GameState state, Position from) {
        return generateOrthogonal(state, from, SQUARE_MOVE_DISTANCE);
    }

    // =========================
    // CORE MOVEMENT LOGIC
    // =========================

    /**
     * Generates orthogonal moves (horizontal and vertical lines).
     *
     * @param state current game state
     * @param from starting position
     * @param dist movement distance in tiles
     * @return list of valid moves
     */
    private List<Move> generateOrthogonal(GameState state, Position from, int dist) {

        int[][] dirs = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        return generateLineMoves(state, from, dirs, dist);
    }

    /**
     * Generates diagonal moves (4 diagonal directions).
     *
     * @param state current game state
     * @param from starting position
     * @param dist movement distance in tiles
     * @return list of valid moves
     */
    private List<Move> generateDiagonal(GameState state, Position from, int dist) {

        int[][] dirs = {
                {1, 1},
                {1, -1},
                {-1, 1},
                {-1, -1}
        };

        return generateLineMoves(state, from, dirs, dist);
    }

    /**
     * Applies a set of directional vectors to generate line-based moves.</br>
     * Only return destinations inside the board.
     *
     * @param state current game state
     * @param from starting position
     * @param directions array of direction vectors
     * @param dist movement distance
     * @return list of moves
     */
    private List<Move> generateLineMoves(
            GameState state,
            Position from,
            int[][] directions,
            int dist
    ) {

        List<Move> moves = new ArrayList<>();

        for (int[] d : directions) {

            Position to = add(from, d[0] * dist, d[1] * dist);

            if (isInsideBoard(state, to)) {
                moves.add(new Move(from, to, MoveNature.REGULAR));
            }
        }

        return moves;
    }


    /**
     * Utility method to translate a position by a delta.
     */
    private Position add(Position p, int dx, int dy) {
        return new Position(p.getX() + dx, p.getY() + dy);
    }
}