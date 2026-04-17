package eu.nicosworld.capture.encounterCapture;

import eu.nicosworld.capture.*;
import eu.nicosworld.capture.capturerule.EncounterRule;
import eu.nicosworld.model.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static eu.nicosworld.capture.CaptureTestCase.*;

public class EncounterCaptureTest extends AbstractCaptureTest {

    @BeforeEach
    void setup() {
        // Isolation of the Encounter rule
        this.engine = new CaptureEngine(List.of(new EncounterRule(regularGenerator, pathValidator)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("encounterTestData")
    void should_validate_encounter_logic(CaptureTestCase testCase) {
        launchTestCase(testCase);
    }

    static Stream<Arguments> encounterTestData() {
        return Stream.of(
                // =============================
                // BASIC ENCOUNTERS
                // =============================
                blackCircleAt(4, 1, 1)
                        .againstWhite(PieceType.CIRCLE, 4, 2, 2)
                        .expectEncounter(PieceType.CIRCLE, 4)
                        .build(),

                blackTriangleAt(9, 2, 2)
                        .againstWhite(PieceType.SQUARE, 9, 2, 4)
                        .expectEncounter(PieceType.SQUARE, 9)
                        .build(),

                blackSquareAt(16, 2, 2)
                        .againstWhite(PieceType.TRIANGLE, 16, 5, 2)
                        .expectEncounter(PieceType.TRIANGLE, 16)
                        .build(),

                // =============================
                // MULTIPLE CAPTURES
                // =============================
                blackCircleAt(25, 4, 4)
                        .againstWhite(PieceType.TRIANGLE, 25, 5, 5)
                        .againstWhite(PieceType.SQUARE, 25, 3, 3)
                        .expectEncounter(PieceType.TRIANGLE, 25)
                        .expectEncounter(PieceType.SQUARE, 25)
                        .build(),

                // =============================
                // NO FRIENDLY FIRE / SAME COLOR
                // =============================
                blackCircleAt(4, 1, 1)
                        .withBlackAlly(PieceType.CIRCLE, 4, 2, 2)
                        .expectNoCapture()
                        .build(),

                // =============================
                // WRONG DISTANCE / MOVEMENT
                // =============================
                blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 4, 4)
                        .expectNoCapture()
                        .build(),

                blackCircleAt(5, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 5, 2, 3) // Orthogonal (Circle is diagonal)
                        .expectNoCapture()
                        .build(),

                // =============================
                // OBSTACLES (Path not clear)
                // =============================
                blackSquareAt(8, 1, 1)
                        .againstWhite(PieceType.CIRCLE, 8, 4, 1)
                        .withObstacleAt(2, 1)
                        .expectNoCapture()
                        .build(),

                // =============================
                // WRONG VALUES
                // =============================
                blackCircleAt(5, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 6, 3, 3)
                        .expectNoCapture()
                        .build(),

                // =============================
                // PYRAMID (Attacker)
                // =============================
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 4)
                        .withComponent(PieceType.SQUARE, 9)
                        .againstWhite(PieceType.TRIANGLE, 4, 3, 3) // Circle move
                        .expectEncounter(PieceType.TRIANGLE, 4)
                        .build(),

                // Pyramid total value
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 4)
                        .withComponent(PieceType.SQUARE, 6)
                        .againstWhite(PieceType.CIRCLE, 10, 3, 3)
                        .expectEncounter(PieceType.CIRCLE, 10)
                        .build(),

                // =============================
                // PYRAMID (Target)
                // =============================
                // Capture Whole Pyramid (Total Value Match)
                blackCircleAt(6, 2, 2)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.CIRCLE, 2),
                                new ComponentData(PieceType.CIRCLE, 4))
                        .expectEncounter(PieceType.PYRAMID, 6)
                        .build(),

                // Capture Partial (Component Match)
                blackCircleAt(4, 2, 2)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.CIRCLE, 4),
                                new ComponentData(PieceType.SQUARE, 5))
                        .expectPartialEncounter(PieceType.CIRCLE, 4)
                        .build(),

                // =============================
                // PYRAMIDE vs PYRAMIDE
                // =============================
                // Component vs Component
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 5)
                        .withComponent(PieceType.SQUARE, 9)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.TRIANGLE, 5),
                                new ComponentData(PieceType.CIRCLE, 12))
                        .expectPartialEncounter(PieceType.TRIANGLE, 5)
                        .build(),

                // Component vs Total Value
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 10)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.CIRCLE, 4),
                                new ComponentData(PieceType.CIRCLE, 6))
                        .expectEncounter(PieceType.PYRAMID, 10)
                        .build(),

                // Multi-match within a Pyramid
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 5)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.CIRCLE, 5),
                                new ComponentData(PieceType.TRIANGLE, 5))
                        .expectPartialEncounter(PieceType.CIRCLE, 5)
                        .expectPartialEncounter(PieceType.TRIANGLE, 5)
                        .build()
        );
    }
}