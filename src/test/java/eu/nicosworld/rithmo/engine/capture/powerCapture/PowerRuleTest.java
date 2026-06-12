package eu.nicosworld.rithmo.engine.capture.powerCapture;

import static eu.nicosworld.rithmo.engine.capture.CaptureTestCase.*;
import static eu.nicosworld.rithmo.engine.testutils.CaptureJustifications.power;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureTest;
import eu.nicosworld.rithmo.engine.capture.CaptureEngine;
import eu.nicosworld.rithmo.engine.capture.CaptureTestCase;
import eu.nicosworld.rithmo.engine.capture.capturerule.PowerRule;
import eu.nicosworld.rithmo.engine.capture.justification.PowerRelation;
import eu.nicosworld.rithmo.engine.model.PieceType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class PowerRuleTest extends AbstractCaptureTest {

  @BeforeEach
  void setup() {
    // We isolate the PowerRule for these tests
    this.engine = new CaptureEngine(List.of(new PowerRule()));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("powerTestData")
  void should_validate_power_logic(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("singleDebugCase")
  void should_validate_power_logic_oneTestCaseForDebug(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  static Stream<Arguments> singleDebugCase() {
    return Stream.of(
        blackSquareAt(100, 1, 1)
            .againstWhite(PieceType.CIRCLE, 10, 4, 1)
            .expectPower(PieceType.CIRCLE, 10, power(100, PowerRelation.ROOT, 2, 10))
            .build());
  }

  static Stream<Arguments> powerTestData() {
    return Stream.of(
        // =============================
        // 1. SQUARES (Carrés)
        // =============================

        // Success: 4^2 = 16 (Within Circle range: diagonal 1)
        blackCircleAt(4, 2, 2)
            .againstWhite(PieceType.TRIANGLE, 16, 3, 3)
            .expectPower(PieceType.TRIANGLE, 16, power(4, PowerRelation.POWER, 2, 16))
            .build(),

        // Success: sqrt(100) = 10 (Within Square range: orthogonal up to 3)
        blackSquareAt(100, 1, 1)
            .againstWhite(PieceType.CIRCLE, 10, 4, 1)
            .expectPower(PieceType.CIRCLE, 10, power(100, PowerRelation.ROOT, 2, 10))
            .build(),

        // Fail: 5^2 = 25 (But target is 24)
        blackCircleAt(5, 2, 2).againstWhite(PieceType.CIRCLE, 24, 3, 3).expectNoCapture().build(),

        // =============================
        // 2. CUBES
        // =============================

        // Success: 3^3 = 27 (Within Triangle range: orthogonal 2 )
        blackTriangleAt(3, 1, 1)
            .againstWhite(PieceType.SQUARE, 27, 3, 1)
            .expectPower(PieceType.SQUARE, 27, power(3, PowerRelation.POWER, 3, 27))
            .build(),

        // Success: cubert(64) = 4
        blackCircleAt(64, 2, 2)
            .againstWhite(PieceType.CIRCLE, 4, 3, 3)
            .expectPower(PieceType.CIRCLE, 4, power(64, PowerRelation.ROOT, 3, 4))
            .build(),

        // =============================
        // 3. RANGE & OBSTACLES
        // =============================

        // Fail: 4^2 = 16 but out of range for a Circle (dist 2)
        blackCircleAt(4, 1, 1).againstWhite(PieceType.TRIANGLE, 16, 3, 3).expectNoCapture().build(),

        // Fail: Path is blocked
        blackSquareAt(9, 1, 1)
            .againstWhite(PieceType.TRIANGLE, 81, 5, 1)
            .withObstacleAt(3, 1)
            .expectNoCapture()
            .build(),

        // =============================
        // 4. PYRAMIDS
        // =============================

        // Attacker Pyramid uses component: 6^2 = 36
        blackPyramidAt(1, 1)
            .withComponent(PieceType.TRIANGLE, 6)
            .againstWhite(PieceType.SQUARE, 36, 3, 1)
            .expectPower(PieceType.SQUARE, 36, power(6, PowerRelation.POWER, 2, 36))
            .build(),

        // Target Pyramid component is hit: sqrt(49) = 7
        blackCircleAt(49, 2, 2)
            .againstWhitePyramid(
                3,
                3,
                new ComponentData(PieceType.CIRCLE, 7),
                new ComponentData(PieceType.CIRCLE, 10))
            .expectPartialPower(PieceType.CIRCLE, 7, power(49, PowerRelation.ROOT, 2, 7))
            .build(),

        // Target Pyramid total value is hit: 4^2 = 16 (6+10)
        blackCircleAt(4, 2, 2)
            .againstWhitePyramid(
                3,
                3,
                new ComponentData(PieceType.CIRCLE, 6),
                new ComponentData(PieceType.CIRCLE, 10))
            .expectPower(PieceType.PYRAMID, 16, power(4, PowerRelation.POWER, 2, 16))
            .build());
  }
}
