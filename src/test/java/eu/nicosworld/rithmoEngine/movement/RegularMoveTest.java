package eu.nicosworld.rithmoEngine.movement;

import eu.nicosworld.rithmoEngine.model.PieceType;
import eu.nicosworld.rithmoEngine.model.Position;
import eu.nicosworld.rithmoEngine.move.MoveNature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class RegularMoveTest extends MovementTestBase {
    static Stream<Arguments> regularMovesDSL() {
        return Stream.of(

                // =========================================================
                // CIRCLE — valid diagonal moves
                // =========================================================
                MoveTestCase.circleAt(4, 4).canReach(3, 3),
                MoveTestCase.circleAt(4, 4).canReach(5, 5),
                MoveTestCase.circleAt(4, 4).canReach(3, 5),
                MoveTestCase.circleAt(4, 4).canReach(5, 3),

                // CIRCLE — invalid directions
                MoveTestCase.circleAt(4, 4).cannotReach(4, 3),
                MoveTestCase.circleAt(4, 4).cannotReach(4, 5),

                // CIRCLE — destination occupied (from dedicated test)
                MoveTestCase.circleAt(4, 4)
                        .cannotReach(5, 5)
                        .becauseOccupied(),

                // =========================================================
                // TRIANGLE — valid orthogonal moves (distance = 2)
                // =========================================================
                MoveTestCase.triangleAt(4, 4).canReach(4, 2),
                MoveTestCase.triangleAt(4, 4).canReach(4, 6),
                MoveTestCase.triangleAt(4, 4).canReach(2, 4),
                MoveTestCase.triangleAt(4, 4).canReach(6, 4),

                // TRIANGLE — invalid moves
                MoveTestCase.triangleAt(4, 4).cannotReach(4, 3),
                MoveTestCase.triangleAt(4, 4).cannotReach(4, 1),

                // TRIANGLE — path blocked (from dedicated test)
                MoveTestCase.triangleAt(4, 4)
                        .cannotReach(4, 6)
                        .becausePathBlockedAt(4, 5),

                // =========================================================
                // SQUARE — valid orthogonal moves (distance = 3)
                // =========================================================
                MoveTestCase.squareAt(4, 4).canReach(4, 1),
                MoveTestCase.squareAt(4, 4).canReach(4, 7),
                MoveTestCase.squareAt(4, 4).canReach(1, 4),
                MoveTestCase.squareAt(4, 4).canReach(7, 4),

                // SQUARE — invalid moves
                MoveTestCase.squareAt(4, 4).cannotReach(4, 3),
                MoveTestCase.squareAt(4, 4).cannotReach(4, 2),

                // SQUARE — path blocked (from dedicated test)
                MoveTestCase.squareAt(4, 4)
                        .cannotReach(4, 7)
                        .becausePathBlockedAt(4, 5)

        ).map(MoveTestCase::toArguments);
    }

    @ParameterizedTest
    @MethodSource("regularMovesDSL")
    void irregular_moves(PieceType type,
                         Position from,
                         Position to,
                         List<Position> obstacles,
                         boolean expected) {
        assertMoveResult(type,
                from,
                to,
                obstacles,
                expected,
                MoveNature.REGULAR);
    }

}


