package eu.nicosworld.move;

import eu.nicosworld.model.GameState;
import eu.nicosworld.model.PieceAtPosition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovementEngine {

    private RegularMoveGenerator regular;
    private IrregularMoveGenerator irregular;

    public MovementEngine() {
        regular = new RegularMoveGenerator();
        irregular = new IrregularMoveGenerator();
    }

    public List<Move> generateMoves(GameState state, PieceAtPosition pap) {

        Set<Move> moves = new HashSet<>();

        moves.addAll(regular.generate(state, pap));
        moves.addAll(irregular.generate(state, pap));

        return new ArrayList<>(moves);
    }
}
