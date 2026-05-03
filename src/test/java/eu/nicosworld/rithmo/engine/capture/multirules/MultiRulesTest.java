package eu.nicosworld.rithmo.engine.capture.multirules;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureTest;
import eu.nicosworld.rithmo.engine.capture.CaptureEngine;
import eu.nicosworld.rithmo.engine.capture.CaptureTestCase;
import eu.nicosworld.rithmo.engine.capture.capturerule.AmbushRule;
import eu.nicosworld.rithmo.engine.capture.capturerule.AssaultRule;
import eu.nicosworld.rithmo.engine.capture.capturerule.EncounterRule;
import eu.nicosworld.rithmo.engine.capture.capturerule.PowerRule;
import eu.nicosworld.rithmo.engine.model.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class MultiRulesTest extends AbstractCaptureTest {

    @BeforeEach
    void setup() {
        this.engine = new CaptureEngine(List.of(
                new AmbushRule(regularGenerator, pathValidator),
                new EncounterRule(regularGenerator, pathValidator),
                new PowerRule(regularGenerator, pathValidator),
                new AssaultRule(regularGenerator, pathValidator)
                ));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("multiRulesTestData")
    void should_validate_ambush_logic(CaptureTestCase testCase) {
        launchTestCase(testCase);
    }

    static Stream<Arguments> multiRulesTestData() {
        return Stream.of(
                CaptureTestCase.blackCircleAt(10, 5, 5) // Un cercle de 10 en (5,5)
                        // Cible 1 : Un triangle de 10 en (5,6) -> Rencontre (Encounter)
                        .againstWhite(PieceType.TRIANGLE, 10, 4, 6)
                        // Cible 2 : Un cercle de 10 en (6,5) -> Rencontre (Encounter)
                        .againstWhite(PieceType.CIRCLE, 10, 6, 4)
                        .expectEncounter(PieceType.TRIANGLE, 10)
                        .expectEncounter(PieceType.CIRCLE, 10)
                        .build(),
                CaptureTestCase.blackCircleAt(8, 3, 3)
                        .againstWhitePyramid(4, 4,
                                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 8),
                                new CaptureTestCase.ComponentData(PieceType.TRIANGLE, 64))
                        .withBlackAlly(PieceType.CIRCLE, 8, 5, 5)
                        .expectPartialEncounter(PieceType.CIRCLE, 8)
                        .expectPartialPower(PieceType.TRIANGLE, 64)
                        .expectPartialAmbush(PieceType.TRIANGLE, 64)
                        .build(),
                CaptureTestCase.blackSquareAt(8, 4, 1)
                        .againstWhitePyramid(4, 4,
                                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 8),
                                new CaptureTestCase.ComponentData(PieceType.TRIANGLE, 64))
                        .withBlackAlly(PieceType.TRIANGLE, 8, 6, 4)
                        .expectPartialEncounter(PieceType.CIRCLE, 8)
                        .expectPartialPower(PieceType.TRIANGLE, 64)
                        .expectPartialAmbush(PieceType.TRIANGLE, 64)
                        .build(),
                // same but with path blocked
                CaptureTestCase.blackSquareAt(8, 4, 1)
                        .againstWhitePyramid(4, 4,
                                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 8),
                                new CaptureTestCase.ComponentData(PieceType.TRIANGLE, 64))
                        .withBlackAlly(PieceType.TRIANGLE, 8, 6, 4)
                        .withObstacleAt(4,3)
                        .expectNoCapture()
                        .build()

        );
    }
}