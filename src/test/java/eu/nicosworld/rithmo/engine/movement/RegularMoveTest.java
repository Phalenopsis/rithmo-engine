package eu.nicosworld.rithmo.engine.movement;

import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.PlayerColor;
import eu.nicosworld.rithmo.engine.move.MoveNature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class RegularMoveTest extends MovementTestBase {
    static Stream<SimpleMoveTestCase> regularMovesDSL() {
        return Stream.of(

                // =========================================================
                // CIRCLE — valid diagonal moves
                // =========================================================
                SimpleMoveTestCase.circleAt(4, 4).canReach(3, 3),
                SimpleMoveTestCase.circleAt(4, 4).canReach(5, 5),
                SimpleMoveTestCase.circleAt(4, 4).canReach(3, 5),
                SimpleMoveTestCase.circleAt(4, 4).canReach(5, 3),

                // CIRCLE — invalid directions
                SimpleMoveTestCase.circleAt(4, 4).cannotReach(4, 3),
                SimpleMoveTestCase.circleAt(4, 4).cannotReach(4, 5),

                SimpleMoveTestCase.circleAt(4, 4)
                        .cannotReach(5, 5)
                        .becauseOccupied(),

                // =========================================================
                // TRIANGLE
                // =========================================================
                SimpleMoveTestCase.triangleAt(4, 4).canReach(4, 2),
                SimpleMoveTestCase.triangleAt(4, 4).canReach(4, 6),
                SimpleMoveTestCase.triangleAt(4, 4).canReach(2, 4),
                SimpleMoveTestCase.triangleAt(4, 4).canReach(6, 4),

                SimpleMoveTestCase.triangleAt(4, 4).cannotReach(4, 3),
                SimpleMoveTestCase.triangleAt(4, 4).cannotReach(4, 1),

                SimpleMoveTestCase.triangleAt(4, 4)
                        .cannotReach(4, 6)
                        .becausePathBlockedAt(4, 5),

                // =========================================================
                // SQUARE
                // =========================================================
                SimpleMoveTestCase.squareAt(4, 4).canReach(4, 1),
                SimpleMoveTestCase.squareAt(4, 4).canReach(4, 7),
                SimpleMoveTestCase.squareAt(4, 4).canReach(1, 4),
                SimpleMoveTestCase.squareAt(4, 4).canReach(7, 4),

                SimpleMoveTestCase.squareAt(4, 4).cannotReach(4, 3),
                SimpleMoveTestCase.squareAt(4, 4).cannotReach(4, 2),

                SimpleMoveTestCase.squareAt(4, 4)
                        .cannotReach(4, 7)
                        .becausePathBlockedAt(4, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("regularMovesDSL")
    void irregular_moves(SimpleMoveTestCase testCase) {

        Player player = new Player(PlayerColor.BLACK);

        Piece piece = TestPieceFactory.create(testCase.type(), player);

        assertMoveResult(
                piece,
                testCase.from(),
                testCase.to(),
                testCase.obstacles(),
                testCase.expected(),
                MoveNature.REGULAR
        );
    }

}


