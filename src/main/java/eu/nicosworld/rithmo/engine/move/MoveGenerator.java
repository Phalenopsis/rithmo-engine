package eu.nicosworld.rithmo.engine.move;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import java.util.List;

/**
 * Contract responsible for generating all legal moves for a given piece in a given game state.
 *
 * <p>A MoveGenerator encapsulates the movement rules of the game, independently of capture rules or
 * higher-level game logic.
 *
 * <p>Each implementation may define different movement strategies depending on piece type, board
 * rules, or game variants.
 *
 * <p>The generator is expected to:
 *
 * <ul>
 *   <li>Compute all possible moves for the given piece
 *   <li>Respect board boundaries and game constraints
 *   <li>Ignore captures logic if handled at a higher layer
 * </ul>
 *
 * <p>This interface is designed to support extensibility: different rule sets can be implemented
 * without modifying the engine.
 */
public interface MoveGenerator {

  /**
   * Generates all possible moves for a given piece in a specific game state.
   *
   * @param state current game state (board, pieces, rules context)
   * @param pap piece with its current position
   * @return list of all legal moves for this piece (may be empty but never null)
   */
  List<Move> generate(GameState state, PieceAtPosition pap);
}
