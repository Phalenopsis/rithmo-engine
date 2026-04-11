package eu.nicosworld.move;

import eu.nicosworld.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all move generators in the Rithmomachie engine.
 *
 * <p>This class implements the common dispatch logic for generating moves
 * depending on the type of piece, while delegating the actual movement rules
 * to concrete subclasses.</p>
 *
 * <h2>Design intent</h2>
 * <p>
 * The move generation system is structured using a <b>template method pattern</b>:
 * </p>
 *
 * <ul>
 *     <li>{@link #generate(GameState, PieceAtPosition)} handles dispatch logic</li>
 *     <li>Concrete subclasses define movement rules for each piece type</li>
 * </ul>
 *
 * <p>This ensures:</p>
 * <ul>
 *     <li>no duplication of dispatch logic</li>
 *     <li>consistent handling of all piece types</li>
 *     <li>easy extension for alternative rule sets (variants of the game)</li>
 * </ul>
 *
 * <h2>Piece dispatch</h2>
 * <p>Movement generation is performed based on the runtime type of the piece:</p>
 * <ul>
 *     <li>{@code CIRCLE} → {@link #generateCircleMoves(GameState, Position)}</li>
 *     <li>{@code TRIANGLE} → {@link #generateTriangleMoves(GameState, Position)}</li>
 *     <li>{@code SQUARE} → {@link #generateSquareMoves(GameState, Position)}</li>
 *     <li>{@code PYRAMID} → decomposed into its internal components</li>
 * </ul>
 *
 * <h2>Pyramid behavior</h2>
 * <p>
 * A pyramid is a composite piece. It can move using any of its internal components
 * (circle, triangle, square). Each enabled component generates its own set of moves,
 * which are merged into a single result.
 * </p>
 *
 * <p>Example: a pyramid containing circle + triangle will generate both circle moves
 * and triangle moves from the same position.</p>
 *
 * <h2>Board safety</h2>
 * <p>
 * Utility method {@link #isOutsideBoard(GameState, Position)} allows subclasses
 * to validate whether a move remains within board boundaries.
 * </p>
 *
 * <h2>Extension model</h2>
 * <p>
 * Subclasses must implement movement rules for each basic piece type.
 * This allows different rule sets or game variants without modifying this class.
 * </p>
 */
public abstract class AbstractMoveGenerator implements MoveGenerator {

    /**
     * Generates all legal moves for a given piece in a given game state.
     *
     * <p>This method acts as a dispatcher based on piece type and delegates
     * the actual movement logic to subclass implementations.</p>
     *
     * @param state current game state (board, players, rules)
     * @param pap piece with its current position
     * @return list of all legal moves for the given piece
     */
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

    /**
     * Checks whether a position is outside the board boundaries.
     *
     * <p>This is a helper method for subclasses when validating potential moves.</p>
     *
     * @param state current game state
     * @param p position to check
     * @return true if the position is outside the board, false otherwise
     */
    protected boolean isOutsideBoard(GameState state, Position p) {
        return !state.getBoard().isInside(p);
    }

    /**
     * Generates moves for a pyramid piece.
     *
     * <p>A pyramid is treated as a composite piece. Each internal component
     * (circle, triangle, square) contributes its own move set.</p>
     *
     * @param state current game state
     * @param pap piece with position
     * @return list of all moves derived from active pyramid components
     */
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