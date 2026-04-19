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
 * <p>Pipeline:
 * <ol>
 *     <li>Generate raw moves from rule generators</li>
 *     <li>Filter regular moves (path + destination)</li>
 *     <li>Filter irregular moves (destination only)</li>
 * </ol>
 * </p>
 */
public class MovementEngine {

    private final RegularMoveGenerator regular;
    private final IrregularMoveGenerator irregular;
    private final FreePathMovementValidator freePathMovementValidator;

    public MovementEngine() {
        this.regular = new RegularMoveGenerator();
        this.irregular = new IrregularMoveGenerator();
        this.freePathMovementValidator = new FreePathMovementValidator();
    }

    // =========================================================
    // PUBLIC API
    // =========================================================

    /**
     * Generates all legal moves for a given piece.
     */
    public List<Move> generateMoves(GameState state, PieceAtPosition pap) {

        Set<Move> result = new HashSet<>();

        result.addAll(getFreePathRegularMoves(state, pap));
        result.addAll(getIrregularMoves(state, pap));

        return result.stream()
                .filter(m -> isFreeDestination(state, m))
                .toList();
    }

    /**
     * Generates ONLY regular moves usable after a pre-capture action.
     * (i.e. movement restricted to "clean sliding rules")
     */
    public List<Move> generateFreePathRegularMoves(GameState state, PieceAtPosition pap) {
        return getFreePathRegularMoves(state, pap);
    }

    // =========================================================
    // REGULAR MOVES
    // =========================================================

    private List<Move> getFreePathRegularMoves(GameState state, PieceAtPosition pap) {

        List<Move> raw = regular.generate(state, pap);

        return raw.stream()
                .filter(m -> isFreePathMove(state, m))
                .filter(m -> isFreeDestination(state, m))
                .toList();
    }

    private boolean isFreePathMove(GameState state, Move move) {
        return !freePathMovementValidator.isBlocked(state, move.from(), move.to());
    }

    // =========================================================
    // IRREGULAR MOVES
    // =========================================================

    private List<Move> getIrregularMoves(GameState state, PieceAtPosition pap) {
        return irregular.generate(state, pap);
    }

    // =========================================================
    // CONSTRAINTS
    // =========================================================

    private boolean isFreeDestination(GameState state, Move move) {
        return state.board().isEmpty(move.to());
    }

    // =========================================================
    // PLAYER LEVEL
    // =========================================================

    /**
     * Generates all legal moves available for a given player.
     */
    public List<Move> getAllMoves(GameState state, Player player) {

        List<Move> moves = new ArrayList<>();

        Board board = state.board();

        for (PieceAtPosition pap : board.getPiecesForPlayer(player)) {
            moves.addAll(generateMoves(state, pap));
        }

        return moves;
    }
}