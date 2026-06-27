package eu.nicosworld.rithmo.engine.capture.progressionCapture;

import static eu.nicosworld.rithmo.engine.capture.CaptureTestCase.blackSquareAt;
import static eu.nicosworld.rithmo.engine.capture.CaptureTestCase.blackTriangleAt;
import static eu.nicosworld.rithmo.engine.testutils.CaptureJustifications.progression;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureTest;
import eu.nicosworld.rithmo.engine.capture.CaptureEngine;
import eu.nicosworld.rithmo.engine.capture.CaptureTestCase;
import eu.nicosworld.rithmo.engine.capture.capturerule.ProgressionRule;
import eu.nicosworld.rithmo.engine.math.progression.model.ArithmeticEvidence;
import eu.nicosworld.rithmo.engine.math.progression.model.GeometricEvidence;
import eu.nicosworld.rithmo.engine.math.progression.model.HarmonicEvidence;
import eu.nicosworld.rithmo.engine.model.PieceType;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ProgressionCaptureTest extends AbstractCaptureTest {
  @BeforeEach
  void setup() {
    this.engine = new CaptureEngine(List.of(new ProgressionRule()));
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("progressionTestData")
  void should_validate_progression_logic(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("singleDebugCase")
  void should_validate_progression_logic_oneTestCaseForDebug(CaptureTestCase testCase) {
    launchTestCase(testCase);
  }

  static Stream<Arguments> singleDebugCase() {
    return Stream.of(
        CaptureTestCase.blackSquareAt(4, 0, 0)
            .againstWhite(PieceType.CIRCLE, 6, 0, 3)
            .withBlackAlly(PieceType.CIRCLE, 9, 1, 2)
            .expectProgression(
                PieceType.CIRCLE, 6, progression(4, 6, 9, new GeometricEvidence(1.5)))
            .build());
  }

  static Stream<Arguments> progressionTestData() {
    return Stream.of(
        blackSquareAt(1, 1, 1)
            .againstWhite(PieceType.CIRCLE, 4, 4, 1)
            .withBlackAlly(PieceType.CIRCLE, 7, 3, 2)
            .expectProgression(PieceType.CIRCLE, 4, progression(1, 4, 7, new ArithmeticEvidence(3)))
            .build(),
        blackTriangleAt(4, 0, 2)
            .againstWhite(PieceType.CIRCLE, 4, 2, 2)
            .withBlackAlly(PieceType.TRIANGLE, 4, 2, 0)
            .expectProgression(
                PieceType.CIRCLE,
                4,
                progression(
                    4,
                    4,
                    4,
                    new ArithmeticEvidence(0),
                    new GeometricEvidence(1),
                    new HarmonicEvidence()))
            .build(),
        // =========================================================================
        // 1. PROGRESSION ARITHMÉTIQUE
        // =========================================================================
        // Suite : 3, 5, 7 (Raison: 2)
        // Attaquant (Cercle: 3) en (0,0) -> Diagonale de 1 vers la cible
        // Cible (Cercle Blanc: 5) en (1,1)
        // Allié (Triangle: 7) en (3,1) -> Ligne droite de 2 vers la cible (chemin libre)
        CaptureTestCase.blackCircleAt(3, 0, 0)
            .againstWhite(PieceType.CIRCLE, 5, 1, 1)
            .withBlackAlly(PieceType.TRIANGLE, 7, 3, 1)
            .expectProgression(PieceType.CIRCLE, 5, progression(3, 5, 7, new ArithmeticEvidence(2)))
            .build(),

        // Suite Décroissante : 12, 8, 4 (Raison: 4)
        // Attaquant (Square: 12) en (4,4) -> Ligne droite de 3 vers la cible (y=4 -> y=1)
        // Cible (Circle: 8) en (4,1)
        // Allié (Circle: 4) en (5,0) -> Diagonale de 1 vers la cible (4+1, 1-1)
        CaptureTestCase.blackSquareAt(12, 4, 4)
            .againstWhite(PieceType.CIRCLE, 8, 4, 1)
            .withBlackAlly(PieceType.CIRCLE, 4, 5, 0)
            .expectProgression(
                PieceType.CIRCLE, 8, progression(4, 8, 12, new ArithmeticEvidence(4)))
            .build(),

        // =========================================================================
        // 2. PROGRESSION GÉOMÉTRIQUE
        // =========================================================================
        // Suite : 2, 4, 8 (Raison: 2)
        // Attaquant (Triangle: 2) en (2,2) -> Ligne droite de 2 vers la cible (x=2 -> x=4)
        // Cible (Circle Blanc: 4) en (4,2)
        // Allié (Circle: 8) en (5,3) -> Diagonale de 1 vers la cible
        CaptureTestCase.blackTriangleAt(2, 2, 2)
            .againstWhite(PieceType.CIRCLE, 4, 4, 2)
            .withBlackAlly(PieceType.CIRCLE, 8, 5, 3)
            .expectProgression(PieceType.CIRCLE, 4, progression(2, 4, 8, new GeometricEvidence(2)))
            .build(),

        // =========================================================================
        // 3. PROGRESSION HARMONIQUE
        // =========================================================================
        // Suite Harmonique classique : 3, 4, 6 (car 1/3 - 1/4 = 1/12 et 1/4 - 1/6 = 1/12)
        // Attaquant (Circle: 3) en (2,2) -> Diagonale de 1 vers la cible (3,3)
        // Cible (Square Blanc: 4) en (3,3)
        // Allié (Square: 6) en (3,6) -> Ligne droite de 3 vers la cible (y=3 -> y=6)
        CaptureTestCase.blackCircleAt(3, 2, 2)
            .againstWhite(PieceType.SQUARE, 4, 3, 3)
            .withBlackAlly(PieceType.SQUARE, 6, 3, 6)
            .expectProgression(PieceType.SQUARE, 4, progression(3, 4, 6, new HarmonicEvidence()))
            .build(),

        // Autre Suite Harmonique : 2, 3, 6 (1/2 - 1/3 = 1/6 et 1/3 - 1/6 = 1/6)
        // Attaquant (Triangle: 2) en (1,1) -> Ligne droite de 2 vers la cible (1,3)
        // Cible (Circle Blanc: 3) en (1,3)
        // Allié (Circle: 6) en (2,4) -> Diagonale de 1 vers la cible
        CaptureTestCase.blackTriangleAt(2, 1, 1)
            .againstWhite(PieceType.CIRCLE, 3, 1, 3)
            .withBlackAlly(PieceType.CIRCLE, 6, 2, 4)
            .expectProgression(PieceType.CIRCLE, 3, progression(2, 3, 6, new HarmonicEvidence()))
            .build(),

        // =========================================================================
        // 4. LES CAS AVEC PYRAMIDE (Capture multiple / Composants)
        // =========================================================================
        // Attaquant : Pyramide Noire en (0,0) contenant un Cercle(4) et un Triangle(6). Total = 10.
        // Cible : Pyramide Blanche en (1,1) contenant un Cercle(5) et un Square(7). Total = 12.
        // Allié : Carré Noir(14) en (1,4) -> Ligne droite de 3 vers la cible en (1,1).
        // Math : L'attaquant utilise la SOMME de ses composants (10). Suite : 10, 12, 14
        // (Arithmétique raison 2)
        // Portée : La pyramide est en (0,0), cible en (1,1). Elle attaque via son composant Cercle
        // (diagonale de 1).
        CaptureTestCase.blackPyramidAt(0, 0)
            .withComponent(PieceType.CIRCLE, 4)
            .withComponent(PieceType.TRIANGLE, 6)
            .againstWhitePyramid(
                1,
                1,
                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 5),
                new CaptureTestCase.ComponentData(PieceType.SQUARE, 7))
            .withBlackAlly(PieceType.SQUARE, 14, 1, 4)
            // On s'attend à capturer la pyramide blanche entière (valeur globale 12),
            // mais aussi ses composants individuellement si le moteur le permet !
            .expectProgression(
                PieceType.PYRAMID, 12, progression(10, 12, 14, new ArithmeticEvidence(2)))
            .build(),

        // Autre cas Pyramide : Capture d'un composant spécifique par progression géométrique
        // Attaquant : Pyramide Noire en (2,2) avec Cercle(3) et Triangle(5)
        // Cible : Pyramide Blanche en (3,3) avec Circle(6) et Square(10)
        // Allié : Cercle(12) en (4,2) -> Diagonale de 1 vers la cible
        // Ici, le composant Cercle(3) de l'attaquant s'aligne géométriquement avec le Cercle(6) de
        // la cible et l'allié(12).
        // Suite : 3, 6, 12 (Géométrique raison 2). Portée ok car (2,2) à (3,3) = pas de diagonale.
        CaptureTestCase.blackPyramidAt(2, 2)
            .withComponent(PieceType.CIRCLE, 3)
            .withComponent(PieceType.TRIANGLE, 5)
            .againstWhitePyramid(
                3,
                3,
                new CaptureTestCase.ComponentData(PieceType.CIRCLE, 6),
                new CaptureTestCase.ComponentData(PieceType.SQUARE, 10))
            .withBlackAlly(PieceType.CIRCLE, 12, 4, 2)
            .expectPartialProgression(
                PieceType.CIRCLE, 6, progression(3, 6, 12, new GeometricEvidence(2)))
            .expectProgression(
                PieceType.PYRAMID, 16, progression(8, 12, 16, new ArithmeticEvidence(4)))
            .expectPartialProgression(
                PieceType.CIRCLE, 6, progression(6, 8, 12, new HarmonicEvidence()))
            .expectPartialProgression(
                PieceType.SQUARE, 10, progression(8, 10, 12, new ArithmeticEvidence(2)))
            .build(),

        // =========================================================================
        // 5. CAS NON PASSANTS (Obstacles sur les pas glissants)
        // =========================================================================
        // Échec Attaquant bloqué :
        // Même logique arithmétique (3, 5, 7) mais un obstacle se trouve en (2,1) entre
        // l'allié(3,1) et la cible(1,1).
        // Le pas du Triangle (ligne droite de 2) est bloqué.
        CaptureTestCase.blackCircleAt(3, 0, 0)
            .againstWhite(PieceType.CIRCLE, 5, 1, 1)
            .withBlackAlly(PieceType.TRIANGLE, 7, 3, 1)
            .withObstacleAt(2, 1) // Bloque la trajectoire de l'allié vers la cible
            .expectNoCapture()
            .build(),

        // Échec Allié bloqué :
        // Attaquant (Square: 6) en (1,1), Cible en (4,1). Distance de 3 (pas régulier du Square).
        // Obstacle positionné sur la trajectoire en (2,1) -> le Square ne peut pas "glisser"
        // jusqu'à la cible.
        CaptureTestCase.blackSquareAt(6, 1, 1)
            .againstWhite(PieceType.CIRCLE, 8, 4, 1)
            .withBlackAlly(PieceType.CIRCLE, 10, 5, 2) // L'allié est à portée (diagonale)
            .withObstacleAt(2, 1) // Bloque le glissement de l'attaquant
            .expectNoCapture()
            .build(),
        // floating ratio
        CaptureTestCase.blackSquareAt(4, 0, 0)
            .againstWhite(PieceType.CIRCLE, 6, 0, 3)
            .withBlackAlly(PieceType.CIRCLE, 9, 1, 2)
            .expectProgression(
                PieceType.CIRCLE, 6, progression(4, 6, 9, new GeometricEvidence(1.5)))
            .build());
  }
}
