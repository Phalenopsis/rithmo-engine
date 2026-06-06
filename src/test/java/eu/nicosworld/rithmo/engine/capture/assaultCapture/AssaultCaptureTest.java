package eu.nicosworld.rithmo.engine.capture.assaultCapture;

import static eu.nicosworld.rithmo.engine.testutils.CaptureJustifications.assault;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureTest;
import eu.nicosworld.rithmo.engine.capture.CaptureEngine;
import eu.nicosworld.rithmo.engine.capture.CaptureTestCase;
import eu.nicosworld.rithmo.engine.capture.capturerule.AssaultRule;
import eu.nicosworld.rithmo.engine.capture.justification.AssaultOperator;
import eu.nicosworld.rithmo.engine.model.PieceType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AssaultCaptureTest extends AbstractCaptureTest {

  @BeforeEach
  void setup() {
    // We inject only the AssaultRule to isolate the behavior
    this.engine = new CaptureEngine(List.of(new AssaultRule(regularGenerator, pathValidator)));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("assaultTestData")
  void should_validate_assault_logic(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("singleDebugCase")
  void should_validate_assault_logic_OneTestCaseForDebug(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  static Stream<Arguments> singleDebugCase() {
    return Stream.of(
        // A pyramid attacks using its internal component (5)
        // 5 * 3 empty spaces = 15
        CaptureTestCase.blackPyramidAt(1, 1)
            .withComponent(PieceType.CIRCLE, 5)
            .againstWhite(PieceType.TRIANGLE, 15, 5, 1)
            .expectAssault(PieceType.TRIANGLE, 15, assault(3, AssaultOperator.MULTIPLY, 5, 15))
            .build());
  }

  static Stream<Arguments> assaultTestData() {
    return Stream.of(
        // =============================
        // SIMPLE ASSAULT (Multiplication)
        // =============================

        // Fail: 6 * 1 empty space = 6 (Target is 12)
        CaptureTestCase.blackTriangleAt(6, 1, 1)
            .againstWhite(PieceType.TRIANGLE, 12, 3, 1)
            .expectNoCapture()
            .build(),

        // Success: 6 * 2 empty spaces = 12
        CaptureTestCase.blackTriangleAt(6, 1, 1)
            .againstWhite(PieceType.TRIANGLE, 12, 4, 1)
            .expectAssault(PieceType.TRIANGLE, 12, assault(2, AssaultOperator.MULTIPLY, 6, 12))
            .build(),

        // =============================
        // SIMPLE ASSAULT (Division)
        // =============================

        // Success: 36 / 3 empty spaces = 12
        CaptureTestCase.blackCircleAt(36, 1, 1)
            .againstWhite(PieceType.TRIANGLE, 12, 5, 1)
            .expectAssault(PieceType.TRIANGLE, 12, assault(3, AssaultOperator.DIVIDE, 36, 12))
            .build(),

        // Fail: 36 / 2 empty spaces = 18 (Target is 12)
        CaptureTestCase.blackCircleAt(36, 1, 1)
            .againstWhite(PieceType.TRIANGLE, 12, 4, 1)
            .expectNoCapture()
            .build(),

        // =============================
        // OBSTACLES (Path is blocked)
        // =============================
        CaptureTestCase.blackTriangleAt(6, 1, 1)
            .againstWhite(PieceType.TRIANGLE, 12, 4, 1)
            .withObstacleAt(2, 1)
            .expectNoCapture()
            .build(),

        // =============================
        // DIAGONALS
        // =============================
        // Distance 2 in diagonal: (1,1) -> (4,4) has 2 empty squares (2,2 and 3,3)
        // 5 * 2 = 10
        CaptureTestCase.blackCircleAt(5, 1, 1)
            .againstWhite(PieceType.SQUARE, 10, 4, 4)
            .expectAssault(PieceType.SQUARE, 10, assault(2, AssaultOperator.MULTIPLY, 5, 10))
            .build(),

        // =============================
        // PYRAMIDS (Attacker)
        // =============================
        // A pyramid attacks using its internal component (5)
        // 5 * 3 empty spaces = 15
        CaptureTestCase.blackPyramidAt(1, 1)
            .withComponent(PieceType.CIRCLE, 5)
            .againstWhite(PieceType.TRIANGLE, 15, 5, 1)
            .expectAssault(PieceType.TRIANGLE, 15, assault(3, AssaultOperator.MULTIPLY, 5, 15))
            .build(),

        // =============================
        // PYRAMIDS (Target)
        // =============================
        // A pyramid target is hit on its total value (16)
        // 8 * 2 empty spaces = 16
        CaptureTestCase.blackCircleAt(8, 1, 1)
            .againstWhitePyramid(
                4,
                1,
                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 10),
                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 6))
            .expectAssault(PieceType.PYRAMID, 16, assault(2, AssaultOperator.MULTIPLY, 8, 16))
            .build(),

        // Partial Capture: hitting a specific component of a pyramid via assault
        // 5 * 4 empty spaces = 20
        CaptureTestCase.blackCircleAt(5, 1, 1)
            .againstWhitePyramid(
                6,
                1,
                new CaptureTestCase.ComponentData(PieceType.SQUARE, 20),
                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 10))
            .expectPartialAssault(PieceType.SQUARE, 20, assault(4, AssaultOperator.MULTIPLY, 5, 20))
            .build());
  }
}
