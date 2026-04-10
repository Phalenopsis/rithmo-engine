package eu.nicosworld.move;

import eu.nicosworld.model.GameState;
import eu.nicosworld.model.PieceAtPosition;

import java.util.ArrayList;
import java.util.List;

public class MovementEngine {

    private RegularMoveGenerator regular;
    private IrregularMoveGenerator irregular;

    public MovementEngine() {
        regular = new RegularMoveGenerator();
        irregular = new IrregularMoveGenerator();
    }

    public List<Move> generateMoves(GameState state, PieceAtPosition pap) {

        List<Move> moves = new ArrayList<>();

        moves.addAll(regular.generate(state, pap));
        moves.addAll(irregular.generate(state, pap));

        return deduplicate(moves);
    }

    private List<Move> deduplicate(List<Move> moves) {
        return moves;
    }
}
