package eu.nicosworld.rithmo.engine.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.move.Move;
import eu.nicosworld.rithmo.engine.move.MoveNature;
import eu.nicosworld.rithmo.engine.move.MovementEngine;
import eu.nicosworld.rithmo.engine.setup.BoardBuilder;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base class for movement rule tests.
 *
 * <p>Provides helpers to build a board, execute move generation, and assert expected move
 * availability.
 */
abstract class MovementTestBase {

  protected BoardBuilder builder;
  protected MovementEngine engine;

  @BeforeEach
  void setup() {
    builder = new BoardBuilder(8, 8);
    engine = new MovementEngine();
  }

  /**
   * Asserts that a move is either valid or invalid for a given setup.
   *
   * @param piece the piece being tested
   * @param from starting position
   * @param to target position
   * @param obstacles list of blocking positions
   * @param expected whether the move should be present
   * @param nature expected move type
   */
  void assertMoveResult(
      Piece piece,
      Position from,
      Position to,
      List<Position> obstacles,
      boolean expected,
      MoveNature nature) {

    Player player = piece.getPlayer();

    // Place tested piece
    builder.withPiece(piece).at(from.getX(), from.getY());

    // Place obstacles (same player context for consistency)
    for (Position p : obstacles) {
      builder.withPiece(new SimplePiece(PieceType.CIRCLE, player, 1)).at(p.getX(), p.getY());
    }

    Board board = builder.build();

    GameState state = GameState.initial(board, player);

    List<Move> moves = engine.generateMoves(state, new PieceAtPosition(piece, from));

    boolean contains =
        moves.stream()
            .anyMatch(m -> m.from().equals(from) && m.to().equals(to) && m.nature() == nature);

    assertEquals(expected, contains);
  }
}
