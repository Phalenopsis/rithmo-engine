package eu.nicosworld.move;

import eu.nicosworld.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link AbstractMoveGenerator} that defines
 * the standard (regular) movement rules of the game.
 *
 * <p>This generator computes moves based on fixed geometric patterns:
 * orthogonal and diagonal directions combined with a fixed movement distance
 * depending on the piece type.</p>
 *
 * <p>Unlike irregular movement, regular movement enforces:
 * <ul>
 *     <li>linear movement along a direction (no jumps over arbitrary patterns)</li>
 *     <li>path blocking rules (intermediate squares must be empty)</li>
 *     <li>fixed distance per piece type</li>
 * </ul>
 *
 * <p>Move distances are defined as:</p>
 * <ul>
 *     <li>Circle → 1 step</li>
 *     <li>Triangle → 2 steps</li>
 *     <li>Square → 3 steps</li>
 * </ul>
 *
 * <p>Each generated move is marked as {@link MoveNature#REGULAR}.</p>
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
     * Applies a set of directional vectors to generate line-based moves.
     *
     * <p>A move is valid if:
     * <ul>
     *     <li>the destination is inside the board</li>
     *     <li>the path between origin and destination is not blocked</li>
     *     <li>the destination square is empty</li>
     * </ul>
     *
     * @param state current game state
     * @param from starting position
     * @param directions array of direction vectors
     * @param dist movement distance
     * @return list of valid moves
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

            if (isOutsideBoard(state, to)) continue;

            if (isBlocked(state, from, d, dist)) continue;

            if (state.getBoard().isEmpty(to)) {
                moves.add(new Move(from, to, MoveNature.REGULAR));
            }
        }

        return moves;
    }

    /**
     * Checks whether a movement path is blocked by another piece.
     *
     * <p>The path is checked step-by-step between origin and destination
     * (excluding both endpoints).</p>
     *
     * @param state current game state
     * @param from starting position
     * @param d direction vector
     * @param dist movement distance
     * @return true if at least one intermediate square is occupied
     */
    private boolean isBlocked(GameState state, Position from, int[] d, int dist) {

        for (int step = 1; step < dist; step++) {

            Position p = add(from, d[0] * step, d[1] * step);

            if (!state.getBoard().isEmpty(p)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Utility method to translate a position by a delta.
     */
    private Position add(Position p, int dx, int dy) {
        return new Position(p.getX() + dx, p.getY() + dy);
    }
}