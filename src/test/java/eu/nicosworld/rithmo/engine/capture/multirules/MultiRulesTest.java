package eu.nicosworld.rithmo.engine.capture.multirules;

import static eu.nicosworld.rithmo.engine.testutils.CaptureJustifications.*;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureTest;
import eu.nicosworld.rithmo.engine.capture.CaptureEngine;
import eu.nicosworld.rithmo.engine.capture.CaptureTestCase;
import eu.nicosworld.rithmo.engine.capture.capturerule.AmbushRule;
import eu.nicosworld.rithmo.engine.capture.capturerule.AssaultRule;
import eu.nicosworld.rithmo.engine.capture.capturerule.EncounterRule;
import eu.nicosworld.rithmo.engine.capture.capturerule.PowerRule;
import eu.nicosworld.rithmo.engine.capture.justification.AmbushOperator;
import eu.nicosworld.rithmo.engine.capture.justification.AssaultOperator;
import eu.nicosworld.rithmo.engine.capture.justification.PowerRelation;
import eu.nicosworld.rithmo.engine.model.PieceType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MultiRulesTest extends AbstractCaptureTest {

  @BeforeEach
  void setup() {
    this.engine =
        new CaptureEngine(
            List.of(
                new AmbushRule(regularGenerator, pathValidator),
                new EncounterRule(regularGenerator, pathValidator),
                new PowerRule(regularGenerator, pathValidator),
                new AssaultRule(regularGenerator, pathValidator)));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("singleDebugCase")
  void should_validate_combined_capture_rules_oneTestCaseForDebug(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  static Stream<Arguments> singleDebugCase() {
    return Stream.of(
        CaptureTestCase.blackTriangleAt(8, 0, 0)
            .againstWhite(PieceType.CIRCLE, 8, 0, 2)
            .withBlackAlly(PieceType.CIRCLE, 1, 1, 3)
            .expectAmbush(PieceType.CIRCLE, 8, ambush(8, AmbushOperator.MULTIPLY, 1, 8))
            .expectEncounter(PieceType.CIRCLE, 8, encounter(8))
            .expectAssault(PieceType.CIRCLE, 8, assault(1, AssaultOperator.MULTIPLY, 8, 8))
            .build());
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("multiRulesTestData")
  void should_validate_combined_capture_rules(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  static Stream<Arguments> multiRulesTestData() {
    return Stream.of(
        CaptureTestCase.blackCircleAt(10, 5, 5)
            .againstWhite(PieceType.TRIANGLE, 10, 4, 6)
            .againstWhite(PieceType.CIRCLE, 10, 6, 4)
            .expectEncounter(PieceType.TRIANGLE, 10, encounter(10))
            .expectEncounter(PieceType.CIRCLE, 10, encounter(10))
            .build(),
        CaptureTestCase.blackCircleAt(8, 3, 3)
            .againstWhitePyramid(
                4,
                4,
                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 8),
                new CaptureTestCase.ComponentData(PieceType.TRIANGLE, 64))
            .withBlackAlly(PieceType.CIRCLE, 8, 5, 5)
            .expectPartialEncounter(PieceType.CIRCLE, 8, encounter(8))
            .expectPartialPower(PieceType.TRIANGLE, 64, power(8, PowerRelation.POWER, 2, 64))
            .expectPartialAmbush(PieceType.TRIANGLE, 64, ambush(8, AmbushOperator.MULTIPLY, 8, 64))
            .build(),
        CaptureTestCase.blackSquareAt(8, 4, 1)
            .againstWhitePyramid(
                4,
                4,
                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 8),
                new CaptureTestCase.ComponentData(PieceType.TRIANGLE, 64))
            .withBlackAlly(PieceType.TRIANGLE, 8, 6, 4)
            .expectPartialEncounter(PieceType.CIRCLE, 8, encounter(8))
            .expectPartialPower(PieceType.TRIANGLE, 64, power(8, PowerRelation.POWER, 2, 64))
            .expectPartialAmbush(PieceType.TRIANGLE, 64, ambush(8, AmbushOperator.MULTIPLY, 8, 64))
            .build(),

        // same but with path blocked
        CaptureTestCase.blackSquareAt(8, 4, 1)
            .againstWhitePyramid(
                4,
                4,
                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 8),
                new CaptureTestCase.ComponentData(PieceType.TRIANGLE, 64))
            .withBlackAlly(PieceType.TRIANGLE, 8, 6, 4)
            .withObstacleAt(4, 3)
            .expectNoCapture()
            .build(),
        CaptureTestCase.blackCircleAt(10, 5, 5)
            .againstWhite(PieceType.TRIANGLE, 10, 4, 6)
            .againstWhite(PieceType.CIRCLE, 10, 6, 4)
            .expectEncounter(PieceType.TRIANGLE, 10, encounter(10))
            .expectEncounter(PieceType.CIRCLE, 10, encounter(10))
            .build(),
        CaptureTestCase.blackTriangleAt(8, 0, 0)
            .againstWhite(PieceType.CIRCLE, 8, 0, 2)
            .withBlackAlly(PieceType.CIRCLE, 1, 1, 3)
            .expectAmbush(PieceType.CIRCLE, 8, ambush(8, AmbushOperator.MULTIPLY, 1, 8))
            .expectEncounter(PieceType.CIRCLE, 8, encounter(8))
            .expectAssault(PieceType.CIRCLE, 8, assault(1, AssaultOperator.MULTIPLY, 8, 8))
            .build());
  }
}
