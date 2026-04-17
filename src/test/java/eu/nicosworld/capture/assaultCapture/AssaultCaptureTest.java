package eu.nicosworld.capture.assaultCapture;

import eu.nicosworld.capture.*;
import eu.nicosworld.capture.capturerule.AssaultRule;
import eu.nicosworld.model.PieceType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static eu.nicosworld.capture.CaptureTestCase.*;


public class AssaultCaptureTest extends AbstractCaptureTest{
    AssaultCaptureTest() {
        engine = new CaptureEngine(
                List.of(new AssaultRule(regularGenerator, pathValidator))
        );
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("assaultTestData")
    void should_validate_ambush_logic(CaptureTestCase testCase) {
        launchTestCase(testCase);
    }

    static Stream<Arguments> assaultTestData() {
        return Stream.of(
                // =============================
                // ASSAUT SIMPLE (Multiplication)
                // =============================
                // Triangle noir 6 attaque Triangle blanc 12
                // Distance 1 : [6] (vide) [12] -> dist = 1. 6 * 1 != 12 (Echec)
                blackTriangleAt(6, 1, 1)
                        .againstWhite(PieceType.TRIANGLE, 12, 3, 1) // (3,1) - (1,1) = 2 cases d'écart, donc 2 cases vides
                        .expectNoCapture()
                        .build(),

                // Distance 2 : [6] (v) (v) [12] -> dist = 2. 6 * 2 = 12 (Succès)
                blackTriangleAt(6, 1, 1)
                        .againstWhite(PieceType.TRIANGLE, 12, 4, 1) // (4,1) - (1,1) = 3 cases d'écart, donc 2 cases vides
                        .expectCapture(PieceType.TRIANGLE, 12)
                        .build(),

                // =============================
                // ASSAUT SIMPLE (Division)
                // =============================
                // Rond noir 36 attaque Triangle blanc 12
                // Distance 3 : [36] (v) (v) (v) [12] -> 36 / 3 = 12 (Succès)
                blackCircleAt(36, 1, 1)
                        .againstWhite(PieceType.TRIANGLE, 12, 5, 1) // (5,1) - (1,1) = 4 cases d'écart, donc 3 cases vides
                        .expectCapture(PieceType.TRIANGLE, 12)
                        .build(),

                // =============================
                // OBSTACLES (Le chemin n'est pas libre)
                // =============================
                blackTriangleAt(6, 1, 1)
                        .againstWhite(PieceType.TRIANGLE, 12, 4, 1)
                        .withObstacleAt(2, 1) // Une pièce bloque le rayon de l'assaut
                        .expectNoCapture()
                        .build(),

                // =============================
                // DIAGONALES
                // =============================
                // Distance 2 en diagonale : (1,1) -> (4,4) = 2 cases vides (2,2 et 3,3)
                blackCircleAt(5, 1, 1)
                        .againstWhite(PieceType.SQUARE, 10, 4, 4)
                        .expectCapture(PieceType.SQUARE, 10)
                        .build(),

                // =============================
                // PYRAMIDES
                // =============================
                // Une pyramide peut attaquer avec l'un de ses composants
                blackPyramidAt(1, 1)
                        .withComponent(PieceType.CIRCLE, 5)
                        .againstWhite(PieceType.TRIANGLE, 15, 5, 1) // 3 cases vides : 5 * 3 = 15
                        .expectCapture(PieceType.TRIANGLE, 15)
                        .build(),

                // Une pyramide cible peut être attaquée sur sa valeur totale
                blackCircleAt(8, 1, 1)
                        .againstWhitePyramid(4, 1,
                                new ComponentData(PieceType.CIRCLE, 10),
                                new ComponentData(PieceType.CIRCLE, 6)) // Total 16. Dist 2: 8 * 2 = 16
                        .expectCapture(PieceType.PYRAMID, 16)
                        .build()
        );
    }
}
