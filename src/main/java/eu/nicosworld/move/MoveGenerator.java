package eu.nicosworld.move;

import eu.nicosworld.model.GameState;
import eu.nicosworld.model.PieceAtPosition;

import java.util.List;

public interface MoveGenerator {
    List<Move> generate(GameState state, PieceAtPosition pap);
}
