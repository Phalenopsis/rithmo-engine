package eu.nicosworld.capture.ambushCapture;

import eu.nicosworld.capture.*;
import eu.nicosworld.capture.capturerule.AmbushRule;
import eu.nicosworld.model.PieceType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static eu.nicosworld.capture.CaptureTestCase.*;

public class AmbushRuleTest extends AbstractCaptureTest {

    AmbushRuleTest() {
        engine = new CaptureEngine(
                List.of(new AmbushRule(regularGenerator, pathValidator))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("ambushTestData")
    void should_validate_ambush_logic(CaptureTestCase testCase) {
        launchTestCase(testCase);
    }

    static  Stream<Arguments> testOneCase() {
        return Stream.of(
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.SQUARE, 5)
                        .withComponent(PieceType.TRIANGLE, 7)
                        .againstWhitePyramid(5, 2,
                                new ComponentData(PieceType.CIRCLE, 5),
                                new ComponentData(PieceType.TRIANGLE, 20))
                        .withBlackAlly(PieceType.CIRCLE, 5, 6, 3)
                        .expectAmbushCount(1)
                        .build()
        );
    }


    static Stream<Arguments> ambushTestData() {
        return Stream.of(

                // =============================
                // 1. SUM (TRIVIAL)
                // =============================

                blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 8, 3, 3)
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 4)
                        .expectAmbush()
                        .build(),

                blackTriangleAt(6, 2, 2)
                        .againstWhite(PieceType.SQUARE, 9, 2, 4)
                        .withBlackAlly(PieceType.CIRCLE, 3, 1, 3)
                        .expectAmbush()
                        .build(),


                // =============================
                // 2. DIFFERENCE
                // =============================

                blackSquareAt(10, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 6, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 3)
                        .expectAmbush()
                        .build(),

                blackCircleAt(8, 6, 3)
                        .againstWhite(PieceType.TRIANGLE, 5, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 3, 4, 3)
                        .expectAmbush()
                        .build(),


                // =============================
                // 3. PRODUCT
                // =============================

                blackCircleAt(2, 6, 3)
                        .againstWhite(PieceType.SQUARE, 6, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 3, 3, 2)
                        .expectAmbush()
                        .build(),


                // =============================
                // 4. QUOTIENT
                // =============================

                blackSquareAt(12, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 3, 5, 4)
                        .expectAmbush()
                        .build(), // plante


                // =============================
                // 5. NO MATCH VALUE
                // =============================

                blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 7, 3, 3)
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 4)
                        .expectNoCapture()
                        .build(),


                // =============================
                // 6. MULTI SUPPORTS
                // =============================

                blackCircleAt(5, 3, 3)
                        .againstWhite(PieceType.CIRCLE, 10, 4, 4)
                        .withBlackAlly(PieceType.CIRCLE, 5, 3, 5)
                        .withBlackAlly(PieceType.CIRCLE, 5, 5, 5)
                        .expectAmbushCount(1)
                        .build(),


                // =============================
                // 7. PYRAMID ATTACKER
                // =============================

                blackPyramidAt(6, 3)
                        .withComponent(PieceType.CIRCLE, 4)
                        .withComponent(PieceType.CIRCLE, 6)
                        .againstWhite(PieceType.CIRCLE, 40, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 3)
                        .expectAmbush()
                        .build(),


                // =============================
                // 8. PYRAMID DEFENDER (PARTIAL MATCH)
                // =============================

                blackCircleAt(1, 4, 1)
                        .againstWhitePyramid(5, 2,
                                new ComponentData(PieceType.CIRCLE, 4),
                                new ComponentData(PieceType.CIRCLE, 6))
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 3)
                        .expectAmbush()
                        .build(),


                // =============================
                // 9. PYRAMID VS PYRAMID
                // =============================

                blackPyramidAt(2, 2)
                        .withComponent(PieceType.SQUARE, 5)
                        .withComponent(PieceType.TRIANGLE, 7)
                        .againstWhitePyramid(5, 2,
                                new ComponentData(PieceType.CIRCLE, 5),
                                new ComponentData(PieceType.TRIANGLE, 20))
                        .withBlackAlly(PieceType.CIRCLE, 5, 6, 3)
                        .expectAmbushCount(1)
                        .build(),


                // =============================
                // 10. ALLY BLOCKED (NO AMBUSH)
                // =============================

                blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 8, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 2) // obstacle in straight line
                        .expectNoCapture()
                        .build(),


                // =============================
                // 11. ALLY OUT OF REACH (NO AMBUSH)
                // =============================

                blackSquareAt(12, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 3, 1, 1) // cannot reach target
                        .expectNoCapture()
                        .build(),


                // =============================
                // 12. ONLY ATTACKER MATCHES (NO AMBUSH)
                // =============================

                blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 3, 3)
                        .expectNoCapture()
                        .build(),


                // =============================
                // 13. ONLY ALLY MATCHES (NO AMBUSH)
                // =============================

                blackCircleAt(9, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 8, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 8, 4, 3)
                        .expectNoCapture()
                        .build()
        );
    }
}