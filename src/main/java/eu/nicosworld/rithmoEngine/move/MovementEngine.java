package eu.nicosworld.rithmoEngine.move;

import eu.nicosworld.rithmoEngine.model.GameState;
import eu.nicosworld.rithmoEngine.model.PieceAtPosition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * High-level movement engine responsible for aggregating all movement rules
 * and applying environment constraints (blocking pieces, occupied destinations).
 *
 * <p>The engine follows a multi-stage pipeline:
 * <ol>
 * <li>Generate theoretical moves from {@link RegularMoveGenerator} and {@link IrregularMoveGenerator}</li>
 * <li>Filter regular moves using {@link FreePathMovementValidator} to ensure "sliding" paths are clear</li>
 * <li>Filter all moves to ensure the final destination tile is empty</li>
 * </ol>
 * </p>
 */
public class MovementEngine {

    private final RegularMoveGenerator regular;
    private final IrregularMoveGenerator irregular;
    private final FreePathMovementValidator freePathMovementValidator;

    public MovementEngine() {
        regular = new RegularMoveGenerator();
        irregular = new IrregularMoveGenerator();
        freePathMovementValidator = new FreePathMovementValidator();
    }

    /**
     * Generates all legal moves for a given piece.
     * * <p>A move is considered legal if it matches the piece's pattern,
     * its path is not obstructed (for regular moves), and its destination is empty.</p>
     *
     * @param state current game state
     * @param pap piece with its position
     * @return a filtered list of unique legal moves
     */
    public List<Move> generateMoves(GameState state, PieceAtPosition pap) {

        Set<Move> moves = new HashSet<>();

        moves.addAll(getFreePathRegularMoves(state, regular.generate(state, pap)));
        moves.addAll(irregular.generate(state, pap));

        return moves.stream().filter(m -> isFreeDestination(state, m)).toList();

    }

    /**
     * Filters a list of moves to keep only those where the path between
     * 'from' and 'to' contains no other pieces.
     */
    private List<Move> getFreePathRegularMoves(GameState state, List<Move> moves) {
        return moves.stream().filter(m -> isFreePathMove(state, m)).toList();
    }

    private boolean isFreePathMove(GameState state, Move move) {
        return !freePathMovementValidator.isBlocked(state, move.from(), move.to());
    }

    /**
     * Validates that the destination square of a move is currently unoccupied.
     */
    private boolean isFreeDestination(GameState state, Move move) {
        return state.board().isEmpty(move.to());
    }
}