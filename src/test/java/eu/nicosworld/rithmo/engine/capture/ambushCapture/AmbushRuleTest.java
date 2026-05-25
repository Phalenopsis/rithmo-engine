package eu.nicosworld.rithmo.engine.capture.ambushCapture;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureTest;
import eu.nicosworld.rithmo.engine.capture.CaptureEngine;
import eu.nicosworld.rithmo.engine.capture.CaptureTestCase;
import eu.nicosworld.rithmo.engine.capture.capturerule.AmbushRule;
import eu.nicosworld.rithmo.engine.capture.justification.AmbushOperator;
import eu.nicosworld.rithmo.engine.model.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static eu.nicosworld.rithmo.engine.testutils.CaptureJustifications.ambush;

public class AmbushRuleTest extends AbstractCaptureTest {

    @BeforeEach
    void setup() {
        this.engine = new CaptureEngine(List.of(new AmbushRule(regularGenerator, pathValidator)));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("ambushTestData")
    void should_validate_ambush_logic(CaptureTestCase testCase) {
        launchTestCase(testCase);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("singleDebugCase")
    void should_validate_ambush_logic_oneTestCaseForDebug(CaptureTestCase testCase) {
        launchTestCase(testCase);
    }

    static Stream<Arguments> singleDebugCase() {
        return Stream.of(
                CaptureTestCase.blackSquareAt(10, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 3, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 3, 5, 4)
                        .expectNoCapture()
                        .build()
        );
    }

    static Stream<Arguments> ambushTestData() {
        return Stream.of(

                // =============================
                // 1. SUM (TRIVIAL)
                // =============================
                // 4 + 4 = 8
                CaptureTestCase.blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 8, 3, 3)
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 4)
                        .expectAmbush(PieceType.CIRCLE, 8, ambush(4, AmbushOperator.ADD, 4, 8))
                        .build(),

                // 6 + 3 = 9
                CaptureTestCase.blackTriangleAt(6, 2, 2)
                        .againstWhite(PieceType.SQUARE, 9, 2, 4)
                        .withBlackAlly(PieceType.CIRCLE, 3, 1, 3)
                        .expectAmbush(PieceType.SQUARE, 9, ambush(6, AmbushOperator.ADD, 3, 9))
                        .build(),


                // =============================
                // 2. DIFFERENCE
                // =============================
                // 10 - 4 = 6
                CaptureTestCase.blackSquareAt(10, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 6, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 3)
                        .expectAmbush(PieceType.CIRCLE, 6, ambush(10, AmbushOperator.SUBTRACT, 4, 6))
                        .build(),
                CaptureTestCase.blackSquareAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 6, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 10, 4, 3)
                        .expectAmbush(PieceType.CIRCLE, 6, ambush(4, AmbushOperator.SUBTRACT_INV, 10, 6))
                        .build(),

                // 8 - 3 = 5
                CaptureTestCase.blackCircleAt(8, 6, 3)
                        .againstWhite(PieceType.TRIANGLE, 5, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 3, 4, 3)
                        .expectAmbush(PieceType.TRIANGLE, 5, ambush(8, AmbushOperator.SUBTRACT, 3, 5))
                        .build(),


                // =============================
                // 3. PRODUCT
                // =============================
                // 2 * 3 = 6
                CaptureTestCase.blackCircleAt(2, 6, 3)
                        .againstWhite(PieceType.SQUARE, 6, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 3, 3, 2)
                        .expectAmbush(PieceType.SQUARE, 6, ambush(2, AmbushOperator.MULTIPLY, 3, 6))
                        .build(),


                // =============================
                // 4. QUOTIENT
                // =============================
                // 12 / 3 = 4
                CaptureTestCase.blackSquareAt(12, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 3, 5, 4)
                        .expectAmbush(PieceType.CIRCLE, 4, ambush(12, AmbushOperator.DIVIDE, 3, 4))
                        .build(),
                // 12 / 3 = 4
                CaptureTestCase.blackSquareAt(3, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 12, 5, 4)
                        .expectAmbush(PieceType.CIRCLE, 4, ambush(3, AmbushOperator.DIVIDE_INV, 12, 4))
                        .build(),

                // =============================
                // 5. NO MATCH VALUE
                // =============================
                CaptureTestCase.blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 7, 3, 3)
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 4)
                        .expectNoCapture()
                        .build(),


                // =============================
                // 6. MULTI SUPPORTS
                // =============================
                CaptureTestCase.blackCircleAt(5, 3, 3)
                        .againstWhite(PieceType.CIRCLE, 10, 4, 4)
                        .withBlackAlly(PieceType.CIRCLE, 5, 3, 5)
                        .withBlackAlly(PieceType.CIRCLE, 5, 5, 5)
                        .expectAmbush(PieceType.CIRCLE, 10, ambush(5, AmbushOperator.ADD, 5, 10))
                        .expectAmbush(PieceType.CIRCLE, 10, ambush(5, AmbushOperator.ADD, 5, 10))
                        .build(),

                CaptureTestCase.blackCircleAt(3, 3, 3)
                        .againstWhite(PieceType.CIRCLE, 6, 4, 4)
                        .withBlackAlly(PieceType.CIRCLE, 3, 3, 5)
                        .withBlackAlly(PieceType.CIRCLE, 2, 5, 5)
                        .expectAmbush(PieceType.CIRCLE, 6, ambush(3, AmbushOperator.ADD, 3, 6))
                        .expectAmbush(PieceType.CIRCLE, 6, ambush(3, AmbushOperator.MULTIPLY, 2, 6))
                        .build(),


                // =============================
                // 7. PYRAMID ATTACKER
                // =============================
                // Attacker (Component 4) * Ally (4) = 16 (Not 40) -> Fail
                // Attacker (Component 6) * Ally (4) = 24 -> Fail
                // Attacker (Total 10) * Ally (4) = 40 -> Success
                CaptureTestCase.blackPyramidAt(6, 3)
                        .withComponent(PieceType.CIRCLE, 4)
                        .withComponent(PieceType.CIRCLE, 6)
                        .againstWhite(PieceType.CIRCLE, 40, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 3)
                        .expectAmbush(PieceType.CIRCLE, 40, ambush(10, AmbushOperator.MULTIPLY, 4, 40))
                        .build(),


                // =============================
                // 8. PYRAMID DEFENDER (PARTIAL MATCH)
                // =============================
                // 1 (Attacker) + 4 (Ally) = 5 -> No match
                // 1 (Attacker) * 4 (Ally) = 4 -> Match with Component(4)
                CaptureTestCase.blackCircleAt(1, 4, 1)
                        .againstWhitePyramid(5, 2,
                                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 4),
                                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 6))
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 3)
                        .expectPartialAmbush(PieceType.CIRCLE, 4, ambush(1, AmbushOperator.MULTIPLY, 4, 4))
                        .build(),


                // =============================
                // 9. PYRAMID VS PYRAMID
                // =============================
                // Attacker component (5) * Ally (5) = 25 -> No match
                // Attacker component (7) - Ally (5) = 2 -> No match
                // Attacker Total (12) - Ally (5) = 7 -> No match
                // Ally (5) matches Target Component (5) -> No (Ambush needs attacker + ally)
                // Attacker component (5) + Ally (5) = 10 -> No match
                // Let's assume: Attacker (Total 12) + Ally (5) = 17 -> No
                // Actually in your case: Attacker(5) matched with Ally(5) to get nothing?
                // Let's adjust to a match: Attacker(5) + Ally(5) = Target component (10)
                CaptureTestCase.blackPyramidAt(2, 2)
                        .withComponent(PieceType.SQUARE, 5)
                        .withComponent(PieceType.TRIANGLE, 7)
                        .againstWhitePyramid(5, 2,
                                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 10),
                                new CaptureTestCase.ComponentData(PieceType.TRIANGLE, 20))
                        .withBlackAlly(PieceType.CIRCLE, 5, 6, 3)
                        .expectPartialAmbush(PieceType.CIRCLE, 10, ambush(5, AmbushOperator.ADD, 5, 10))
                        .build(),


                // =============================
                // 10. ALLY BLOCKED (NO AMBUSH)
                // =============================
                CaptureTestCase.blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 8, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 4, 4, 2)
                        .expectNoCapture()
                        .build(),


                // =============================
                // 11. ALLY OUT OF REACH (NO AMBUSH)
                // =============================
                CaptureTestCase.blackSquareAt(12, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 3, 1, 1)
                        .expectNoCapture()
                        .build(),


                // =============================
                // 12. ONLY ATTACKER MATCHES (NO AMBUSH)
                // =============================
                // 4 = 4 is Encounter, not Ambush
                CaptureTestCase.blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 3, 3)
                        .expectNoCapture()
                        .build(),


                // =============================
                // 13. ONLY ALLY MATCHES (NO AMBUSH)
                // =============================
                CaptureTestCase.blackCircleAt(9, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 8, 5, 2)
                        .withBlackAlly(PieceType.CIRCLE, 8, 4, 3)
                        .expectNoCapture()
                        .build(),

                // =============================
                // 14. NON-INTEGER DIVISION (NO AMBUSH)
                // =============================
                CaptureTestCase.blackSquareAt(10, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 3, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 3, 5, 4)
                        .expectNoCapture()
                        .build(),

                // =============================
                // 15. VALIDATE OPERATOR ORDER
                // =============================
                CaptureTestCase.blackSquareAt(2, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 2, 5, 4)
                        .expectAmbush(PieceType.CIRCLE, 4, ambush(2, AmbushOperator.ADD, 2, 4))
                        .build(),

                CaptureTestCase.blackSquareAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 2, 5, 2)
                        .withBlackAlly(PieceType.TRIANGLE, 2, 5, 4)
                        .expectAmbush(PieceType.CIRCLE, 2, ambush(4, AmbushOperator.SUBTRACT, 2, 2))
                        .build(),

                // =============================
                // 16. IRREGULAR STEP (NO CAPTURE)
                // =============================
                CaptureTestCase.blackTriangleAt(3, 0, 0)
                        .againstWhite(PieceType.CIRCLE, 6, 2,1)
                        .withBlackAlly(PieceType.CIRCLE, 3, 3,2)
                        .expectNoCapture()
                        .build(),

                // =============================
                // 17. WITH PYRAMID ALLY
                // =============================
                CaptureTestCase.blackTriangleAt(3, 0, 0)
                        .againstWhite(PieceType.CIRCLE, 6, 2,0)
                        .withBlackPyramidAlly(3,1,
                                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 2),
                                new CaptureTestCase.ComponentData(PieceType.TRIANGLE, 4))
                        .expectAmbush(PieceType.CIRCLE, 6, ambush(3, AmbushOperator.MULTIPLY, 2, 6))
                        .build()
        );
    }
}