package eu.nicosworld.rithmo.engine.capture.imprisonmentCapture;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureTest;
import eu.nicosworld.rithmo.engine.capture.CaptureEngine;
import eu.nicosworld.rithmo.engine.capture.CaptureTestCase;
import eu.nicosworld.rithmo.engine.capture.capturerule.ImprisonmentRule;
import eu.nicosworld.rithmo.engine.model.PieceType;
import eu.nicosworld.rithmo.engine.model.Position;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ImprisonmentCaptureTest extends AbstractCaptureTest {
  @BeforeEach
  void setup() {
    this.engine = new CaptureEngine(List.of(new ImprisonmentRule()));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("imprisonmentTestData")
  void should_validate_imprisonment_logic(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("singleDebugCase")
  void should_validate_imprisonment_logic_OneTestCaseForDebug(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  static Stream<Arguments> singleDebugCase() {
    return Stream.of(
        CaptureTestCase.blackTriangleAt(225, 3, 3)
            .againstWhite(PieceType.CIRCLE, 72, 4, 4)
            .withBlackAlly(PieceType.CIRCLE, 3, 3, 5)
            .withBlackAlly(PieceType.TRIANGLE, 35, 5, 5)
            .againstWhite(PieceType.SQUARE, 7, 5, 3)
            .expectImprisonment(
                PieceType.CIRCLE,
                72,
                new Position(4, 4),
                new Position(3, 3),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)))
            .expectImprisonment(
                PieceType.CIRCLE,
                72,
                new Position(4, 4),
                new Position(3, 5),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)))
            .expectImprisonment(
                PieceType.CIRCLE,
                72,
                new Position(4, 4),
                new Position(5, 5),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)))
            .build());
  }

  static Stream<Arguments> imprisonmentTestData() {
    return Stream.of(
        CaptureTestCase.blackCircleAt(15, 1, 1)
            .againstWhite(PieceType.CIRCLE, 3, 0, 0)
            .expectImprisonment(
                PieceType.CIRCLE,
                3,
                new Position(0, 0),
                new Position(1, 1),
                List.of(new Position(1, 1)),
                List.of(new Position(1, 1)))
            .build(),
        CaptureTestCase.blackTriangleAt(225, 0, 3)
            .againstWhite(PieceType.SQUARE, 1, 0, 0)
            .withBlackAlly(PieceType.CIRCLE, 3, 3, 0)
            .expectImprisonment(
                PieceType.SQUARE,
                1,
                new Position(0, 0),
                new Position(0, 3),
                List.of(new Position(0, 3), new Position(3, 0)), // regularMovesTo
                List.of(new Position(3, 0))) // blockersAt
            .expectImprisonment(
                PieceType.SQUARE,
                1,
                new Position(0, 0),
                new Position(3, 0),
                List.of(new Position(0, 3), new Position(3, 0)), // regularMovesTo
                List.of(new Position(0, 3))) // blockersAt
            .build(),
        CaptureTestCase.blackTriangleAt(225, 8, 7)
            .againstWhite(PieceType.SQUARE, 1, 0, 0)
            .againstWhite(PieceType.CIRCLE, 1, 1, 0)
            .againstWhite(PieceType.TRIANGLE, 1, 0, 1)
            .expectNoCapture()
            .build(),
        CaptureTestCase.blackTriangleAt(225, 8, 7)
            .againstWhite(PieceType.SQUARE, 10, 0, 0)
            .withBlackAlly(PieceType.CIRCLE, 1, 3, 0)
            .againstWhite(PieceType.TRIANGLE, 1, 0, 1)
            .expectImprisonment(
                PieceType.SQUARE,
                10,
                new Position(0, 0),
                new Position(3, 0),
                List.of(new Position(0, 3), new Position(3, 0)), // regularMovesTo
                List.of(new Position(3, 0), new Position(0, 1)) // // blockersAt
                )
            .build(),
        CaptureTestCase.blackTriangleAt(225, 0, 3)
            .againstWhite(PieceType.SQUARE, 1, 0, 0)
            .againstWhite(PieceType.CIRCLE, 1, 1, 0)
            .againstWhite(PieceType.TRIANGLE, 1, 0, 1)
            .withBlackAlly(PieceType.CIRCLE, 3, 3, 0)
            .expectNoCapture()
            .build(),
        // no capture, circle have a free path
        CaptureTestCase.blackTriangleAt(225, 3, 3)
            .againstWhite(PieceType.CIRCLE, 72, 4, 4)
            .withBlackAlly(PieceType.CIRCLE, 3, 3, 5)
            .withBlackAlly(PieceType.TRIANGLE, 35, 5, 5)
            .expectNoCapture()
            .build(),
        CaptureTestCase.blackTriangleAt(225, 3, 3)
            .againstWhite(PieceType.CIRCLE, 72, 4, 4)
            .withBlackAlly(PieceType.CIRCLE, 3, 3, 5)
            .withBlackAlly(PieceType.TRIANGLE, 35, 5, 5)
            .withBlackAlly(PieceType.SQUARE, 7, 5, 3)
            .expectImprisonment(
                PieceType.CIRCLE,
                72,
                new Position(4, 4),
                new Position(3, 3),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)))
            .expectImprisonment(
                PieceType.CIRCLE,
                72,
                new Position(4, 4),
                new Position(5, 3),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)))
            .expectImprisonment(
                PieceType.CIRCLE,
                72,
                new Position(4, 4),
                new Position(3, 5),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)))
            .expectImprisonment(
                PieceType.CIRCLE,
                72,
                new Position(4, 4),
                new Position(5, 5),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)))
            .build(),
        CaptureTestCase.blackTriangleAt(225, 3, 3)
            .againstWhite(PieceType.CIRCLE, 72, 4, 4)
            .withBlackAlly(PieceType.CIRCLE, 3, 3, 5)
            .withBlackAlly(PieceType.TRIANGLE, 35, 5, 5)
            .againstWhite(PieceType.SQUARE, 7, 5, 3)
            .expectImprisonment(
                PieceType.CIRCLE,
                72,
                new Position(4, 4),
                new Position(3, 3),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)))
            .expectImprisonment(
                PieceType.CIRCLE,
                72,
                new Position(4, 4),
                new Position(3, 5),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)))
            .expectImprisonment(
                PieceType.CIRCLE,
                72,
                new Position(4, 4),
                new Position(5, 5),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)),
                List.of(
                    new Position(3, 3), new Position(5, 3), new Position(3, 5), new Position(5, 5)))
            .build());
  }
}
