package eu.nicosworld.move;

import eu.nicosworld.model.GameState;
import eu.nicosworld.model.PieceAtPosition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * High-level movement engine responsible for aggregating all movement rules
 * and producing the complete set of legal moves for a given piece.
 *
 * <p>This engine delegates move generation to multiple specialized generators:
 * <ul>
 *     <li>{@link RegularMoveGenerator} for standard movement rules</li>
 *     <li>{@link IrregularMoveGenerator} for alternative movement patterns</li>
 * </ul>
 *
 * <p>The final result merges all generated moves and removes duplicates.</p>
 *
 * <p>This class acts as a facade over lower-level movement rule implementations,
 * providing a unified entry point for the game logic.</p>
 */
public class MovementEngine {

    private final RegularMoveGenerator regular;
    private final IrregularMoveGenerator irregular;

    public MovementEngine() {
        regular = new RegularMoveGenerator();
        irregular = new IrregularMoveGenerator();
    }

    /**
     * Generates all possible moves for a given piece by combining
     * multiple movement rule sets.
     *
     * <p>Duplicate moves are removed using a set-based aggregation.</p>
     *
     * @param state current game state
     * @param pap piece with its position
     * @return list of all unique legal moves for the piece
     */
    public List<Move> generateMoves(GameState state, PieceAtPosition pap) {

        Set<Move> moves = new HashSet<>();

        moves.addAll(regular.generate(state, pap));
        moves.addAll(irregular.generate(state, pap));

        return new ArrayList<>(moves);
    }
}