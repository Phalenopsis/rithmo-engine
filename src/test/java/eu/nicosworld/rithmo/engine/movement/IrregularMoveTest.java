package eu.nicosworld.rithmo.engine.movement;

import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.move.MoveNature;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class IrregularMoveTest extends MovementTestBase {
  static Stream<SimpleMoveTestCase> irregularMovesDSL() {
    return Stream.of(

        // =========================================================
        // TRIANGLE — valid irregular moves
        // =========================================================
        SimpleMoveTestCase.triangleAt(4, 4).canReach(5, 6),
        SimpleMoveTestCase.triangleAt(4, 4).canReach(6, 5),
        SimpleMoveTestCase.triangleAt(4, 4).canReach(6, 3),
        SimpleMoveTestCase.triangleAt(4, 4).canReach(5, 2),
        SimpleMoveTestCase.triangleAt(4, 4).canReach(3, 2),
        SimpleMoveTestCase.triangleAt(4, 4).canReach(2, 3),
        SimpleMoveTestCase.triangleAt(4, 4).canReach(2, 5),
        SimpleMoveTestCase.triangleAt(4, 4).canReach(3, 6),

        // TRIANGLE — blocked destination
        SimpleMoveTestCase.triangleAt(4, 4).cannotReach(5, 6).becauseOccupied(),
        SimpleMoveTestCase.triangleAt(4, 4).cannotReach(6, 5).becauseOccupied(),

        // =========================================================
        // SQUARE — valid irregular moves
        // =========================================================
        SimpleMoveTestCase.squareAt(4, 4).canReach(5, 7),
        SimpleMoveTestCase.squareAt(4, 4).canReach(7, 5),
        SimpleMoveTestCase.squareAt(4, 4).canReach(7, 3),
        SimpleMoveTestCase.squareAt(4, 4).canReach(5, 1),
        SimpleMoveTestCase.squareAt(4, 4).canReach(3, 1),
        SimpleMoveTestCase.squareAt(4, 4).canReach(1, 3),
        SimpleMoveTestCase.squareAt(4, 4).canReach(1, 5),
        SimpleMoveTestCase.squareAt(4, 4).canReach(3, 7),

        // SQUARE — blocked destination
        SimpleMoveTestCase.squareAt(4, 4).cannotReach(5, 7).becauseOccupied(),
        SimpleMoveTestCase.squareAt(4, 4).cannotReach(7, 5).becauseOccupied(),

        // =========================================================
        // OUT OF BOARD — TRIANGLE
        // =========================================================
        SimpleMoveTestCase.triangleAt(4, 4).cannotReach(6, 7),
        SimpleMoveTestCase.triangleAt(0, 0).cannotReach(-1, 2),
        SimpleMoveTestCase.triangleAt(0, 0).cannotReach(1, -2),

        // =========================================================
        // OUT OF BOARD — SQUARE
        // =========================================================
        SimpleMoveTestCase.squareAt(6, 6).cannotReach(9, 7),
        SimpleMoveTestCase.squareAt(6, 6).cannotReach(7, 9),
        SimpleMoveTestCase.squareAt(1, 1).cannotReach(-2, 1),
        SimpleMoveTestCase.squareAt(1, 1).cannotReach(1, -2));
  }

  @ParameterizedTest
  @MethodSource("irregularMovesDSL")
  void irregular_moves(SimpleMoveTestCase testCase) {

    Player player = Player.BLACK;

    Piece piece = TestPieceFactory.create(testCase.type(), player);

    assertMoveResult(
        piece,
        testCase.from(),
        testCase.to(),
        testCase.obstacles(),
        testCase.expected(),
        MoveNature.IRREGULAR);
  }
}
