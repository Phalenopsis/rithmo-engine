package eu.nicosworld.movement;

import eu.nicosworld.model.*;
import eu.nicosworld.move.MoveNature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static eu.nicosworld.movement.MoveTestCase.triangleAt;
import static eu.nicosworld.movement.MoveTestCase.squareAt;

class IrregularMoveTest extends MovementTestBase {
    static Stream<Arguments> irregularMovesDSL() {
        return Stream.of(

                // =========================================================
                // TRIANGLE — valid irregular moves
                // =========================================================
                triangleAt(4,4).canReach(5,6),
                triangleAt(4,4).canReach(6,5),
                triangleAt(4,4).canReach(6,3),
                triangleAt(4,4).canReach(5,2),
                triangleAt(4,4).canReach(3,2),
                triangleAt(4,4).canReach(2,3),
                triangleAt(4,4).canReach(2,5),
                triangleAt(4,4).canReach(3,6),

                // TRIANGLE — blocked destination
                triangleAt(4,4).cannotReach(5,6).becauseOccupied(),
                triangleAt(4,4).cannotReach(6,5).becauseOccupied(),

                // =========================================================
                // SQUARE — valid irregular moves
                // =========================================================
                squareAt(4,4).canReach(5,7),
                squareAt(4,4).canReach(7,5),
                squareAt(4,4).canReach(7,3),
                squareAt(4,4).canReach(5,1),
                squareAt(4,4).canReach(3,1),
                squareAt(4,4).canReach(1,3),
                squareAt(4,4).canReach(1,5),
                squareAt(4,4).canReach(3,7),

                // SQUARE — blocked destination
                squareAt(4,4).cannotReach(5,7).becauseOccupied(),
                squareAt(4,4).cannotReach(7,5).becauseOccupied(),

                // =========================================================
                // OUT OF BOARD — TRIANGLE
                // =========================================================
                triangleAt(4,4).cannotReach(6,7),
                triangleAt(0,0).cannotReach(-1,2),
                triangleAt(0,0).cannotReach(1,-2),

                // =========================================================
                // OUT OF BOARD — SQUARE
                // =========================================================
                squareAt(6,6).cannotReach(9,7),
                squareAt(6,6).cannotReach(7,9),
                squareAt(1,1).cannotReach(-2,1),
                squareAt(1,1).cannotReach(1,-2)

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