package eu.nicosworld.rithmo.engine.capture.multirules;

import static eu.nicosworld.rithmo.engine.testutils.CaptureJustifications.*;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureTest;
import eu.nicosworld.rithmo.engine.capture.CaptureEngine;
import eu.nicosworld.rithmo.engine.capture.CaptureTestCase;
import eu.nicosworld.rithmo.engine.capture.capturerule.*;
import eu.nicosworld.rithmo.engine.capture.justification.*;
import eu.nicosworld.rithmo.engine.math.progression.model.ArithmeticEvidence;
import eu.nicosworld.rithmo.engine.math.progression.model.GeometricEvidence;
import eu.nicosworld.rithmo.engine.math.progression.model.HarmonicEvidence;
import eu.nicosworld.rithmo.engine.model.PieceType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MultiRulesTest extends AbstractCaptureTest {

  @BeforeEach
  void setup() {
    this.engine =
        new CaptureEngine(
            List.of(new AmbushRule(), new EncounterRule(), new PowerRule(), new AssaultRule()));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("singleDebugCase")
  void should_validate_combined_capture_rules_oneTestCaseForDebug(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  static Stream<Arguments> singleDebugCase() {
    return Stream.of(
        CaptureTestCase.blackCircleAt(10, 5, 5)
            .againstWhite(PieceType.TRIANGLE, 10, 4, 6)
            .againstWhite(PieceType.CIRCLE, 10, 6, 4)
            .expectEncounter(PieceType.TRIANGLE, 10, encounter(10))
            .expectEncounter(PieceType.CIRCLE, 10, encounter(10))
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

  @Nested
  public class MultiRulesWithProgressionTest {
    @BeforeEach
    void setup() {
      engine =
          new CaptureEngine(
              List.of(
                  new AmbushRule(),
                  new EncounterRule(),
                  new PowerRule(),
                  new AssaultRule(),
                  new ProgressionRule()));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("singleDebugCase")
    void should_validate_combined_capture_rules_oneTestCaseForDebug(CaptureTestCase testCase) {
      launchTestCase(testCase);
    }

    static Stream<Arguments> singleDebugCase() {
      return Stream.of(
          CaptureTestCase.blackPyramidAt(1, 1)
              .withComponent(PieceType.SQUARE, 3)
              .withComponent(PieceType.TRIANGLE, 5)
              .againstWhitePyramid(
                  1,
                  4,
                  new CaptureTestCase.ComponentData(PieceType.CIRCLE, 6),
                  new CaptureTestCase.ComponentData(PieceType.SQUARE, 10))
              .withBlackAlly(PieceType.CIRCLE, 12, 2, 3)
              .expectPartialAssault(PieceType.CIRCLE, 6, assault(2, AssaultOperator.MULTIPLY, 3, 6))
              .expectPartialAssault(
                  PieceType.SQUARE, 10, assault(2, AssaultOperator.MULTIPLY, 5, 10))
              .expectAssault(PieceType.PYRAMID, 16, assault(2, AssaultOperator.MULTIPLY, 8, 16))
              .expectPartialProgression(
                  PieceType.CIRCLE, 6, progression(3, 6, 12, new GeometricEvidence(2)))
              .expectProgression(
                  PieceType.PYRAMID, 16, progression(8, 12, 16, new ArithmeticEvidence(4)))
              .expectPartialProgression(
                  PieceType.CIRCLE, 6, progression(6, 8, 12, new HarmonicEvidence()))
              .expectPartialProgression(
                  PieceType.SQUARE, 10, progression(8, 10, 12, new ArithmeticEvidence(2)))
              .build());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("multiRulesTestData")
    void should_validate_combined_capture_rules(CaptureTestCase testCase) {
      launchTestCase(testCase);
    }

    static Stream<Arguments> multiRulesTestData() {
      return Stream.of(
          CaptureTestCase.blackPyramidAt(1, 1)
              .withComponent(PieceType.SQUARE, 3)
              .withComponent(PieceType.TRIANGLE, 5)
              .againstWhitePyramid(
                  1,
                  4,
                  new CaptureTestCase.ComponentData(PieceType.CIRCLE, 6),
                  new CaptureTestCase.ComponentData(PieceType.SQUARE, 10))
              .withBlackAlly(PieceType.CIRCLE, 12, 2, 3)
              .expectPartialAssault(PieceType.CIRCLE, 6, assault(2, AssaultOperator.MULTIPLY, 3, 6))
              .expectPartialAssault(
                  PieceType.SQUARE, 10, assault(2, AssaultOperator.MULTIPLY, 5, 10))
              .expectAssault(PieceType.PYRAMID, 16, assault(2, AssaultOperator.MULTIPLY, 8, 16))
              .expectPartialProgression(
                  PieceType.CIRCLE, 6, progression(3, 6, 12, new GeometricEvidence(2)))
              .expectProgression(
                  PieceType.PYRAMID, 16, progression(8, 12, 16, new ArithmeticEvidence(4)))
              .expectPartialProgression(
                  PieceType.CIRCLE, 6, progression(6, 8, 12, new HarmonicEvidence()))
              .expectPartialProgression(
                  PieceType.SQUARE, 10, progression(8, 10, 12, new ArithmeticEvidence(2)))
              .build());
    }
  }
}
