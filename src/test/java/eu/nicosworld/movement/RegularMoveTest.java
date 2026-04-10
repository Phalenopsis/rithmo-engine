package eu.nicosworld.movement;

import eu.nicosworld.model.*;
import eu.nicosworld.move.MoveNature;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static eu.nicosworld.movement.MoveTestCase.*;

class RegularMoveTest extends MovementTestBase {
    static Stream<Arguments> regularMovesDSL() {
        return Stream.of(

                // =========================================================
                // CIRCLE — valid diagonal moves
                // =========================================================
                circleAt(4, 4).canReach(3, 3),
                circleAt(4, 4).canReach(5, 5),
                circleAt(4, 4).canReach(3, 5),
                circleAt(4, 4).canReach(5, 3),

                // CIRCLE — invalid directions
                circleAt(4, 4).cannotReach(4, 3),
                circleAt(4, 4).cannotReach(4, 5),

                // CIRCLE — destination occupied (from dedicated test)
                circleAt(4, 4)
                        .cannotReach(5, 5)
                        .becauseOccupied(),

                // =========================================================
                // TRIANGLE — valid orthogonal moves (distance = 2)
                // =========================================================
                triangleAt(4, 4).canReach(4, 2),
                triangleAt(4, 4).canReach(4, 6),
                triangleAt(4, 4).canReach(2, 4),
                triangleAt(4, 4).canReach(6, 4),

                // TRIANGLE — invalid moves
                triangleAt(4, 4).cannotReach(4, 3),
                triangleAt(4, 4).cannotReach(4, 1),

                // TRIANGLE — path blocked (from dedicated test)
                triangleAt(4, 4)
                        .cannotReach(4, 6)
                        .becausePathBlockedAt(4, 5),

                // =========================================================
                // SQUARE — valid orthogonal moves (distance = 3)
                // =========================================================
                squareAt(4, 4).canReach(4, 1),
                squareAt(4, 4).canReach(4, 7),
                squareAt(4, 4).canReach(1, 4),
                squareAt(4, 4).canReach(7, 4),

                // SQUARE — invalid moves
                squareAt(4, 4).cannotReach(4, 3),
                squareAt(4, 4).cannotReach(4, 2),

                // SQUARE — path blocked (from dedicated test)
                squareAt(4, 4)
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


