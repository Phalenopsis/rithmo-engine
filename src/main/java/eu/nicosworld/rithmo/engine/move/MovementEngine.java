package eu.nicosworld.rithmo.engine.move;

import eu.nicosworld.rithmo.engine.model.Board;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Player;

import java.util.ArrayList;
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

    /**
     * Generates all legal moves available for a given player in the current game state.
     *
     * <p>This method aggregates moves for every piece owned by the specified player by delegating
     * to {@link #generateMoves(GameState, PieceAtPosition)} for each piece on the board.</p>
     *
     * <p>It does not mutate the game state and only evaluates the current board configuration.</p>
     *
     * @param state  the current immutable game state
     * @param player the player for whom available moves are computed
     * @return a list of all legal moves available to the player in the given state
     */
    public List<Move> getAllMoves(GameState state, Player player) {
        List<Move> moves = new ArrayList<>();

        Board board = state.board();
        List<PieceAtPosition> piecesAtPosition = board.getPiecesForPlayer(player);

        for(PieceAtPosition pieceAtPosition : piecesAtPosition) {
            moves.addAll(generateMoves(state, pieceAtPosition));
        }

        return moves;
    }
}