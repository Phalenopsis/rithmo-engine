package eu.nicosworld.rithmo.engine.move;

import eu.nicosworld.rithmo.engine.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

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

    /**
     * Retrieves all pieces located at positions reachable by a regular move
     * from a given origin position, applying an additional filtering condition.
     *
     * <p>This method computes all reachable positions using the movement rules
     * of the three basic piece types (square, circle, triangle), then collects
     * the pieces found on those positions.</p>
     *
     * <p>A piece is included in the result if:</p>
     * <ul>
     *   <li>it satisfies the provided {@code filter}</li>
     *   <li>and it matches the movement type used to reach it</li>
     *   <li>or it is a {@link Pyramid} containing at least one component
     *       compatible with that movement type</li>
     * </ul>
     *
     * <p>If multiple movement types can reach the same position, the piece is stored
     * only once in the resulting map.</p>
     *
     * <p>This method is typically used as a geometric scanner to find nearby pieces
     * matching specific criteria (e.g. allies, enemies, or value-based filters).</p>
     *
     * @param state the current game state containing the board and pieces
     * @param from the origin position from which regular moves are evaluated
     * @param filter a predicate applied to each piece (e.g. filter by color, type, or value)
     * @return a map associating each reachable position with the corresponding piece;
     *         only positions containing a compatible and filtered piece are included
     */
    public Map<Position, Piece> getAllPieceAround(GameState state, Position from, Predicate<Piece> filter) {
        Board board = state.board();

        List<Move> squareMoves = generateSquareMoves(state, from);
        List<Move> circleMoves = generateCircleMoves(state, from);
        List<Move> triangleMoves = generateTriangleMoves(state, from);

        HashMap<Position, Piece> caseAround = new HashMap<>();
        caseAround.putAll(getPieceAround(board, PieceType.SQUARE, squareMoves, filter));
        caseAround.putAll(getPieceAround(board, PieceType.CIRCLE, circleMoves, filter));
        caseAround.putAll(getPieceAround(board, PieceType.TRIANGLE, triangleMoves, filter));

        return caseAround;
    }

    /**
     * Retrieves all pieces located at positions reachable by a regular move
     * from a given origin position, without applying any additional filtering.
     *
     * <p>This is a convenience method equivalent to calling
     * {@link #getAllPieceAround(GameState, Position, Predicate)}
     * with a predicate that accepts all pieces.</p>
     *
     * @param state the current game state containing the board and pieces
     * @param from the origin position from which regular moves are evaluated
     * @return a map associating each reachable position with the corresponding piece;
     *         only positions containing a compatible piece are included
     */
    public Map<Position, Piece> getAllPieceAround(GameState state, Position from) {
        return getAllPieceAround(state, from, p -> true);
    }

    /**
     * Filters and returns pieces located on a list of candidate positions,
     * restricted to a specific movement type and an additional custom filter.
     *
     * <p>A piece is considered valid if:</p>
     * <ul>
     *   <li>it satisfies the provided {@code filter}</li>
     *   <li>and it matches the given {@link PieceType}</li>
     *   <li>or it is a {@link Pyramid} containing at least one component
     *       of the given type</li>
     * </ul>
     *
     * <p>This method is typically used to retrieve nearby pieces that are reachable
     * via a specific movement pattern (circle, triangle, square), while allowing
     * additional filtering logic such as selecting only allies or enemies.</p>
     *
     * @param board the game board used to retrieve pieces
     * @param type the movement type used to filter compatible pieces
     * @param moves the list of candidate moves representing reachable positions
     * @param filter a predicate applied to each piece (e.g. filter by color, value, or type)
     * @return a map associating each valid position with the corresponding piece
     */
    public Map<Position, Piece> getPieceAround(Board board, PieceType type, List<Move> moves, Predicate<Piece> filter) {
        HashMap<Position, Piece> caseAround = new HashMap<>();
        for (Move move : moves) {
            Piece piece = board.getPieceAt(move.to());
            if (piece != null && filter.test(piece)) {
                if (piece.getType().equals(type)
                        || (piece.getType().equals(PieceType.PYRAMID)
                        && ((Pyramid) piece).hasComponent(type))
                ) {
                    caseAround.put(move.to(), piece);
                }
            }
        }
        return caseAround;
    }
}