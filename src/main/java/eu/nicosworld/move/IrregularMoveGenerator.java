package eu.nicosworld.move;

import eu.nicosworld.model.*;

import java.util.ArrayList;
import java.util.List;

public class IrregularMoveGenerator extends AbstractMoveGenerator {

    protected List<Move> generateCircleMoves(GameState state,
                                             Position from) {
        return List.of();
    }

    protected List<Move> generateTriangleMoves(GameState state,
                                             Position from) {
        return generateFromDeltas(state, from, triangleDeltas());
    }

    protected List<Move> generateSquareMoves(GameState state,
                                           Position from) {
        return generateFromDeltas(state, from, squareDeltas());
    }

    // =========================
    // CORE
    // =========================

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

            if (!isInsideBoard(state, to)) continue;

            if (!state.getBoard().isEmpty(to)) continue;

            moves.add(new Move(from, to, MoveNature.IRREGULAR));
        }

        return moves;
    }

    // =========================
    // PATTERNS
    // =========================

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