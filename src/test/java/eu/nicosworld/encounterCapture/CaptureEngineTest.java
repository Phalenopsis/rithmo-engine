package eu.nicosworld.encounterCapture;

import eu.nicosworld.capture.*;
import eu.nicosworld.capture.capturerule.EncounterRule;
import eu.nicosworld.model.PieceType;
import eu.nicosworld.model.PlayerColor;
import eu.nicosworld.move.FreePathMovementValidator;
import eu.nicosworld.move.RegularMoveGenerator;
import eu.nicosworld.setup.BoardBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static eu.nicosworld.capture.CaptureTestCase.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaptureEngineTest {

    private final RegularMoveGenerator regularGenerator = new RegularMoveGenerator();
    private final FreePathMovementValidator pathValidator = new FreePathMovementValidator();

    @ParameterizedTest(name = "{0}")
    @MethodSource("encounterTestData")
    void should_validate_capture_logic(CaptureTestCase testCase) {
        BoardBuilder bb = new BoardBuilder();

        // 1. Setup Attacker
        bb.piece(testCase.getAttackerType(), testCase.getAttackerValue(), PlayerColor.BLACK);
        for (CaptureTestCase.ComponentData comp : testCase.getAttackerComponents()) {
            bb.withComponent(comp.type(), comp.value());
        }
        bb.at(testCase.getAttackerPos().getX(), testCase.getAttackerPos().getY());

        // 2. Setup Others
        for (CaptureTestCase.ExtraPiece p : testCase.getOtherPieces()) {
            bb.piece(p.type(), p.value(), p.color());
            for (CaptureTestCase.ComponentData comp : p.components()) {
                bb.withComponent(comp.type(), comp.value());
            }
            bb.at(p.pos().getX(), p.pos().getY());
        }

        CaptureContext ctx = new CaptureContextBuilder()
                .from(bb)
                .at(testCase.getAttackerPos().getX(), testCase.getAttackerPos().getY())
                .build();

        CaptureEngine engine = new CaptureEngine(List.of(new EncounterRule(regularGenerator, pathValidator)));
        List<CaptureAction> captures = engine.findCaptures(ctx);

// 1. Count
        assertEquals(
                testCase.getExpectedCaptureCount(),
                captures.size(),
                "Failure on test case: " + testCase
        );

// 2. Details
        for (ExpectedCapture expected : testCase.getExpectedCaptures()) {

            boolean found = captures.stream().anyMatch(c ->
                    c.capturedPiece().getType() == expected.type()
                            && c.capturedPiece().getValue() == expected.value()
                            && c.isWholeCapture() == expected.isWhole()
            );

            assertTrue(found, "Missing expected capture: " + expected + " in " + testCase);
        }
    }

    static Stream<Arguments> encounterTestData() {
        return Stream.of(

                // Basic Encounter
                blackCircleAt(4, 1, 1)
                        .againstWhite(PieceType.CIRCLE, 4, 2, 2)
                        .expectCapture(PieceType.CIRCLE, 4)
                        .build(),

                // Multiple Capture
                blackCircleAt(25, 4, 4)
                        .againstWhite(PieceType.TRIANGLE, 25, 5, 5)
                        .againstWhite(PieceType.SQUARE, 25, 3, 3)
                        .expectCapture(PieceType.TRIANGLE, 25)
                        .expectCapture(PieceType.SQUARE, 25)
                        .build(),

                // No Friendly Fire
                blackCircleAt(4, 1, 1)
                        .withBlackAlly(PieceType.CIRCLE, 4, 2, 2)
                        .expectNoCapture()
                        .build(),

                // Pyramid Component Capture
                blackTriangleAt(36, 1, 1)
                        .againstWhitePyramid(3, 1,
                                new ComponentData(PieceType.SQUARE, 36),
                                new ComponentData(PieceType.CIRCLE, 10))
                        .expectPartialCapture(PieceType.SQUARE, 36)
                        .build(),

                // =============================
                // CAS TRIVIAUX
                // =============================
                blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 3, 3)
                        .expectCapture(PieceType.CIRCLE, 4)
                        .build(),

                blackTriangleAt(9, 2, 2)
                        .againstWhite(PieceType.SQUARE, 9, 2, 4)
                        .expectCapture(PieceType.SQUARE, 9)
                        .build(),

                blackSquareAt(16, 2, 2)
                        .againstWhite(PieceType.TRIANGLE, 16, 5, 2)
                        .expectCapture(PieceType.TRIANGLE, 16)
                        .build(),

                // =============================
                // MAUVAISE DISTANCE
                // =============================
                blackCircleAt(4, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 4, 4, 4)
                        .expectNoCapture()
                        .build(),

                // =============================
                // MAUVAIS MOUVEMENT
                // =============================
                blackCircleAt(5, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 5, 2, 3)
                        .expectNoCapture()
                        .build(),

                blackTriangleAt(7, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 7, 3, 3)
                        .expectNoCapture()
                        .build(),

                blackSquareAt(10, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 10, 4, 3)
                        .expectNoCapture()
                        .build(),

                // =============================
                // OBSTACLES
                // =============================
                blackSquareAt(8, 1, 1)
                        .againstWhite(PieceType.CIRCLE, 8, 4, 1)
                        .withObstacleAt(2, 1)
                        .expectNoCapture()
                        .build(),

                blackTriangleAt(6, 1, 1)
                        .againstWhite(PieceType.CIRCLE, 6, 1, 3)
                        .withObstacleAt(1, 2)
                        .expectNoCapture()
                        .build(),

                // =============================
                // MAUVAISE VALEUR
                // =============================
                blackCircleAt(5, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 6, 3, 3)
                        .expectNoCapture()
                        .build(),

                blackSquareAt(12, 1, 1)
                        .againstWhite(PieceType.TRIANGLE, 11, 4, 1)
                        .expectNoCapture()
                        .build(),

                // =============================
                // MULTI-CAPTURES
                // =============================
                blackCircleAt(5, 3, 3)
                        .againstWhite(PieceType.CIRCLE, 5, 2, 2)
                        .againstWhite(PieceType.TRIANGLE, 5, 4, 4)
                        .expectCapture(PieceType.CIRCLE, 5)
                        .expectCapture(PieceType.TRIANGLE, 5)
                        .build(),

                blackTriangleAt(7, 3, 3)
                        .againstWhite(PieceType.CIRCLE, 7, 3, 5)
                        .againstWhite(PieceType.CIRCLE, 7, 3, 1)
                        .expectCapture(PieceType.CIRCLE, 7)
                        .expectCapture(PieceType.CIRCLE, 7)
                        .build(),

                // =============================
                // PYRAMIDE - COMPOSANT
                // =============================
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 4)
                        .withComponent(PieceType.SQUARE, 9)
                        .againstWhite(PieceType.TRIANGLE, 4, 3, 3)
                        .expectCapture(PieceType.TRIANGLE, 4)
                        .build(),

                // =============================
                // PYRAMIDE - VALEUR TOTALE
                // =============================
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 4)
                        .withComponent(PieceType.SQUARE, 6)
                        .againstWhite(PieceType.CIRCLE, 10, 3, 3)
                        .expectCapture(PieceType.CIRCLE, 10)
                        .build(),

                // =============================
                // PYRAMIDE MULTI-CAPTURES
                // =============================
                blackPyramidAt(3, 3)
                        .withComponent(PieceType.CIRCLE, 5)
                        .withComponent(PieceType.TRIANGLE, 7)
                        .againstWhite(PieceType.CIRCLE, 5, 2, 2)
                        .againstWhite(PieceType.CIRCLE, 7, 3, 5)
                        .expectCapture(PieceType.CIRCLE, 5)
                        .expectCapture(PieceType.CIRCLE, 7)
                        .build(),

                // =============================
                // PYRAMIDE - MAUVAIS MOUVEMENT
                // =============================
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 4)
                        .againstWhite(PieceType.CIRCLE, 4, 2, 3)
                        .expectNoCapture()
                        .build(),

                // =============================
                // CIBLE = PYRAMIDE (VALEUR TOTALE)
                // =============================
                blackCircleAt(6, 2, 2)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.CIRCLE, 2),
                                new ComponentData(PieceType.CIRCLE, 4))
                        .expectCapture(PieceType.PYRAMID, 6)
                        .build(),

                // =============================
                // CIBLE = PYRAMIDE (COMPOSANT)
                // =============================
                blackCircleAt(4, 2, 2)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.CIRCLE, 4),
                                new ComponentData(PieceType.SQUARE, 5))
                        .expectPartialCapture(PieceType.CIRCLE, 4)
                        .build(),

                // =============================
                // MÊME COULEUR
                // =============================
                blackCircleAt(5, 2, 2)
                        .withBlackAlly(PieceType.CIRCLE, 5, 3, 3)
                        .expectNoCapture()
                        .build(),

                // =============================
                // HORS PORTÉE
                // =============================
                blackCircleAt(5, 1, 1)
                        .againstWhite(PieceType.CIRCLE, 5, 3, 3)
                        .expectNoCapture()
                        .build(),

                // =============================
                // BORD DE PLATEAU
                // =============================
                blackCircleAt(5, 1, 1)
                        .againstWhite(PieceType.CIRCLE, 5, 2, 2)
                        .expectCapture(PieceType.CIRCLE, 5)
                        .build(),

                // =============================
                // MULTI-DIRECTIONS
                // =============================
                blackSquareAt(9, 4, 4)
                        .againstWhite(PieceType.CIRCLE, 9, 7, 4)
                        .againstWhite(PieceType.CIRCLE, 9, 4, 7)
                        .expectCapture(PieceType.CIRCLE, 9)
                        .expectCapture(PieceType.CIRCLE, 9)
                        .build(),
                // =============================
                // PYRAMIDE vs PYRAMIDE - COMPOSANT -> COMPOSANT
                // =============================
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 5)
                        .withComponent(PieceType.SQUARE, 9)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.TRIANGLE, 5),
                                new ComponentData(PieceType.CIRCLE, 12))
                        .expectPartialCapture(PieceType.TRIANGLE, 5)
                        .build(),

                // =============================
                // PYRAMIDE vs PYRAMIDE - COMPOSANT -> VALEUR TOTALE
                // =============================
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 10)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.CIRCLE, 4),
                                new ComponentData(PieceType.CIRCLE, 6)) // total = 10
                        .expectCapture(PieceType.PYRAMID, 10)
                        .build(),

                // =============================
                // PYRAMIDE vs PYRAMIDE - VALEUR TOTALE -> COMPOSANT
                // =============================
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 4)
                        .withComponent(PieceType.CIRCLE, 6) // total = 10
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.SQUARE, 10),
                                new ComponentData(PieceType.CIRCLE, 3))
                        .expectPartialCapture(PieceType.SQUARE, 10)
                        .build(),

                // =============================
                // PYRAMIDE vs PYRAMIDE - MULTI MATCH
                // =============================
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 5)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.CIRCLE, 5),
                                new ComponentData(PieceType.TRIANGLE, 5))
                        .expectPartialCapture(PieceType.CIRCLE, 5)
                        .expectPartialCapture(PieceType.TRIANGLE, 5)
                        .build(),

                // =============================
                // PYRAMIDE vs PYRAMIDE - AUCUNE CORRESPONDANCE
                // =============================
                blackPyramidAt(2, 2)
                        .withComponent(PieceType.CIRCLE, 5)
                        .againstWhitePyramid(3, 3,
                                new ComponentData(PieceType.CIRCLE, 6),
                                new ComponentData(PieceType.TRIANGLE, 7))
                        .expectNoCapture()
                        .build()
        );
    }
}