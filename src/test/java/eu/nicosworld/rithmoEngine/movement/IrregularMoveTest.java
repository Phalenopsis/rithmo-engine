package eu.nicosworld.rithmoEngine.movement;

import eu.nicosworld.rithmoEngine.model.PieceType;
import eu.nicosworld.rithmoEngine.model.Position;
import eu.nicosworld.rithmoEngine.move.MoveNature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class IrregularMoveTest extends MovementTestBase {
    static Stream<Arguments> irregularMovesDSL() {
        return Stream.of(

                // =========================================================
                // TRIANGLE — valid irregular moves
                // =========================================================
                MoveTestCase.triangleAt(4,4).canReach(5,6),
                MoveTestCase.triangleAt(4,4).canReach(6,5),
                MoveTestCase.triangleAt(4,4).canReach(6,3),
                MoveTestCase.triangleAt(4,4).canReach(5,2),
                MoveTestCase.triangleAt(4,4).canReach(3,2),
                MoveTestCase.triangleAt(4,4).canReach(2,3),
                MoveTestCase.triangleAt(4,4).canReach(2,5),
                MoveTestCase.triangleAt(4,4).canReach(3,6),

                // TRIANGLE — blocked destination
                MoveTestCase.triangleAt(4,4).cannotReach(5,6).becauseOccupied(),
                MoveTestCase.triangleAt(4,4).cannotReach(6,5).becauseOccupied(),

                // =========================================================
                // SQUARE — valid irregular moves
                // =========================================================
                MoveTestCase.squareAt(4,4).canReach(5,7),
                MoveTestCase.squareAt(4,4).canReach(7,5),
                MoveTestCase.squareAt(4,4).canReach(7,3),
                MoveTestCase.squareAt(4,4).canReach(5,1),
                MoveTestCase.squareAt(4,4).canReach(3,1),
                MoveTestCase.squareAt(4,4).canReach(1,3),
                MoveTestCase.squareAt(4,4).canReach(1,5),
                MoveTestCase.squareAt(4,4).canReach(3,7),

                // SQUARE — blocked destination
                MoveTestCase.squareAt(4,4).cannotReach(5,7).becauseOccupied(),
                MoveTestCase.squareAt(4,4).cannotReach(7,5).becauseOccupied(),

                // =========================================================
                // OUT OF BOARD — TRIANGLE
                // =========================================================
                MoveTestCase.triangleAt(4,4).cannotReach(6,7),
                MoveTestCase.triangleAt(0,0).cannotReach(-1,2),
                MoveTestCase.triangleAt(0,0).cannotReach(1,-2),

                // =========================================================
                // OUT OF BOARD — SQUARE
                // =========================================================
                MoveTestCase.squareAt(6,6).cannotReach(9,7),
                MoveTestCase.squareAt(6,6).cannotReach(7,9),
                MoveTestCase.squareAt(1,1).cannotReach(-2,1),
                MoveTestCase.squareAt(1,1).cannotReach(1,-2)

        ).map(MoveTestCase::toArguments);
    }

    @ParameterizedTest
    @MethodSource("irregularMovesDSL")
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
                MoveNature.IRREGULAR);
    }
}