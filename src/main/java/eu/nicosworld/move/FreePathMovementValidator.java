package eu.nicosworld.move;

import eu.nicosworld.model.GameState;
import eu.nicosworld.model.Position;

/**
 * Validator responsible for checking if a movement path is clear of obstacles.
 * * <p>This class is used to validate "sliding" (regular) moves as well as
 * distance-based capture rules like the Assault. It supports orthogonal
 * (horizontal/vertical) and 45-degree diagonal trajectories.</p>
 */
public class FreePathMovementValidator {

    /**
     * Determines whether the path between two positions is blocked by another piece.
     * * <p>The calculation identifies the unit direction vector and iterates through
     * every intermediate square. The starting (from) and ending (to) positions
     * are not checked for occupancy.</p>
     *
     * @param state the current game state containing the board
     * @param from  the starting position of the moving or attacking piece
     * @param to    the target position (destination or piece to be captured)
     * @return {@code true} if at least one square between 'from' and 'to' is occupied,
     * {@code false} if the path is clear or if positions are adjacent.
     */
    public boolean isBlocked(GameState state, Position from, Position to) {
        int diffX = to.getX() - from.getX();
        int diffY = to.getY() - from.getY();

        // Ensure the path is either a straight line or a perfect 45-degree diagonal
        if (!isPathLinear(diffX, diffY)) return false;

        int dist = getDistance(diffX, diffY);

        // Safety check to prevent division by zero if from == to
        if (dist == 0) return false;

        int dx = diffX / dist;
        int dy = diffY / dist;

        for (int step = 1; step < dist; step++) {
            Position p = add(from, dx * step, dy * step);
            if (!state.getBoard().isEmpty(p)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the movement vector corresponds to a valid straight path.
     * @param dx horizontal difference
     * @param dy vertical difference
     * @return {@code true} if the path is orthogonal or 45-degree diagonal
     */
    private boolean isPathLinear(int dx, int dy) {
        return dx == 0 || dy == 0 || Math.abs(dx) == Math.abs(dy);
    }

    /**
     * Utility method to translate a position by a specific delta.
     * @param p  the base position
     * @param dx horizontal offset
     * @param dy vertical offset
     * @return a new {@link Position} instance
     */
    private Position add(Position p, int dx, int dy) {
        return new Position(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Calculates the movement distance in number of tiles (Chebyshev distance).
     * <p>For regular moves or assaults, this represents the total number of steps
     * required to reach the target.</p>
     * @param dx horizontal difference
     * @param dy vertical difference
     * @return the integer distance between the two points
     */
    private int getDistance(int dx, int dy) {
        return Math.max(Math.abs(dx), Math.abs(dy));
    }
}