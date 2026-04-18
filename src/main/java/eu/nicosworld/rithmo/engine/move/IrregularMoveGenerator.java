package eu.nicosworld.rithmo.engine.move;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.Position;

import java.util.ArrayList;
import java.util.List;

/**
 /**
 * Implementation of {@link AbstractMoveGenerator} that defines
 * irregular movement patterns for pieces.
 *
 * <p>This generator encodes fixed movement patterns (deltas)
 * for each piece type and produces all valid moves based on:
 * <ul>
 *     <li>board boundaries</li>
 *     <li>occupation of target squares</li>
 *     <li>predefined movement offsets (Delta patterns)</li>
 * </ul>
 *
 * <p>Unlike regular movement rules, this implementation does not rely
 * on dynamic computation or symmetry rules, but instead uses
 * explicit predefined movement vectors.</p>
 *
 * <p>Each move produced is marked as {@link MoveNature#IRREGULAR},
 * indicating it comes from non-standard movement logic.</p>
 *
 * <p>Circle pieces currently have no irregular movement in this variant.</p>
 */
public class IrregularMoveGenerator extends AbstractMoveGenerator {

    @Override
    protected List<Move> generateCircleMoves(GameState state, Position from) {
        return List.of();
    }

    @Override
    protected List<Move> generateTriangleMoves(GameState state, Position from) {
        return generateFromDeltas(state, from, triangleDeltas());
    }

    @Override
    protected List<Move> generateSquareMoves(GameState state, Position from) {
        return generateFromDeltas(state, from, squareDeltas());
    }

    // =========================
    // CORE
    // =========================

    /**
     * Generates moves by applying a list of relative offsets (deltas)
     * to a starting position.</br>
     * Only return destinations inside the board
     *
     * @param state current game state
     * @param from starting position
     * @param deltas movement offsets to apply
     * @return list of valid moves for the given pattern
     */
    private List<Move> generateFromDeltas(
            GameState state,
            Position from,
            List<Delta> deltas
    ) {

        List<Move> moves = new ArrayList<>();

        for (Delta d : deltas) {

            Position to = new Position(
                    from.getX() + d.dx(),
                    from.getY() + d.dy()
            );

            if (isInsideBoard(state, to)) {
                moves.add(new Move(from, to, MoveNature.IRREGULAR));
            }
        }

        return moves;
    }

    // =========================
    // PATTERNS
    // =========================

    /**
     * Movement pattern for triangle pieces.
     * Each delta represents a legal irregular jump.
     */
    private List<Delta> triangleDeltas() {
        return List.of(
                new Delta(1, 2),
                new Delta(2, 1),
                new Delta(2, -1),
                new Delta(1, -2),
                new Delta(-1, -2),
                new Delta(-2, -1),
                new Delta(-2, 1),
                new Delta(-1, 2)
        );
    }

    /**
     * Movement pattern for square pieces.
     * Each delta represents a longer irregular jump than triangle.
     */
    private List<Delta> squareDeltas() {
        return List.of(
                new Delta(1, 3),
                new Delta(3, 1),
                new Delta(3, -1),
                new Delta(1, -3),
                new Delta(-1, -3),
                new Delta(-3, -1),
                new Delta(-3, 1),
                new Delta(-1, 3)
        );
    }
}