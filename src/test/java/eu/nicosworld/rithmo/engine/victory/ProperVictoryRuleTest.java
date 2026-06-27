package eu.nicosworld.rithmo.engine.victory;

import static eu.nicosworld.rithmo.engine.testutils.VictoryJustifications.progression;
import static org.assertj.core.api.Assertions.assertThat;

import eu.nicosworld.rithmo.engine.math.progression.model.ArithmeticEvidence;
import eu.nicosworld.rithmo.engine.math.progression.model.GeometricEvidence;
import eu.nicosworld.rithmo.engine.math.progression.model.HarmonicEvidence;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.PieceType;
import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import eu.nicosworld.rithmo.engine.testutils.PieceAtPositionHelper;
import eu.nicosworld.rithmo.engine.testutils.PreDefinedState;
import eu.nicosworld.rithmo.engine.testutils.victory.ProperVictoryAssertion;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProperVictoryRuleTest {
  private ProperVictoryRule rule;

  @BeforeEach
  void setup() {
    rule = ProperVictoryRule.fast();
  }

  @Test
  void testEvaluate_NoVictory() {
    GameState state = PreDefinedState.predefinedVerySimpleGame(Player.BLACK);

    List<Victory> victories = rule.evaluate(state);

    assertThat(victories).isEmpty();
  }

  @Test
  void testEvaluate_arithmeticVictory() {
    GameState state = PreDefinedState.progressionVictoryState_mediumBoard_arithmetic(Player.BLACK);

    List<Victory> victories = rule.evaluate(state);

    assertThat(victories).hasSize(1);

    Set<PieceAtPosition> expectedPap =
        Set.of(
            PieceAtPositionHelper.create(PieceType.TRIANGLE, Player.BLACK, 56, "(7,0)"),
            PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 16, "(7,1)"),
            PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 36, "(7,3)"));

    ProperVictoryAssertion.from(victories)
        .hasWinner(Player.BLACK)
        .hasVictoryWithPieces(expectedPap)
        .hasJustification(progression(16, 36, 56, new ArithmeticEvidence(20)));
  }

  @Test
  void testEvaluate_geometricVictory() {
    GameState state = PreDefinedState.progressionVictoryState_classicBoard_geometric(Player.BLACK);

    List<Victory> victories = rule.evaluate(state);

    assertThat(victories).hasSize(1);

    Set<PieceAtPosition> expectedPap =
        Set.of(
            PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 4, "(12,0)"),
            PieceAtPositionHelper.create(PieceType.TRIANGLE, Player.BLACK, 12, "(12,1)"),
            PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 36, "(12,2)"));

    ProperVictoryAssertion.from(victories)
        .hasWinner(Player.BLACK)
        .hasVictoryWithPieces(expectedPap)
        .hasJustification(progression(4, 12, 36, new GeometricEvidence(3)));
  }

  @Test
  void testEvaluate_harmonicVictory() {
    GameState state = PreDefinedState.progressionVictoryState_classicBoard_harmonic(Player.BLACK);

    List<Victory> victories = rule.evaluate(state);

    assertThat(victories).hasSize(1);

    Set<PieceAtPosition> expectedPap =
        Set.of(
            PieceAtPositionHelper.create(PieceType.SQUARE, Player.BLACK, 6, "(14,0)"),
            PieceAtPositionHelper.create(PieceType.TRIANGLE, Player.BLACK, 12, "(14,2)"),
            PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 8, "(14,1)"));

    ProperVictoryAssertion.from(victories)
        .hasWinner(Player.BLACK)
        .hasVictoryWithPieces(expectedPap)
        .hasJustification(progression(6, 8, 12, new HarmonicEvidence()));
  }

  @Test
  void testEvaluate_harmonicAndArithmeticVictory() {
    GameState state =
        PreDefinedState.progressionVictoryState_classicBoard_doubleCombination(Player.BLACK);

    List<Victory> victories = rule.evaluate(state);

    assertThat(victories).hasSize(3);

    PieceAtPosition bs6 = PieceAtPositionHelper.create(PieceType.SQUARE, Player.BLACK, 6, "(15,0)");
    PieceAtPosition bt12 =
        PieceAtPositionHelper.create(PieceType.TRIANGLE, Player.BLACK, 12, "(15,3)");
    PieceAtPosition bc8 = PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 8, "(15,1)");
    PieceAtPosition bc9 = PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 9, "(15,2)");

    Set<PieceAtPosition> expectedHarmonicPap = Set.of(bs6, bc8, bt12);
    Set<PieceAtPosition> expectedArithmeticPap = Set.of(bs6, bc9, bt12);
    Set<PieceAtPosition> expectedCombinationPap = Set.of(bs6, bc9, bt12, bc8);

    ProperVictoryAssertion.from(victories)
        .hasWinner(Player.BLACK)
        .hasVictoryWithPieces(expectedHarmonicPap)
        .hasJustification(progression(6, 8, 12, new HarmonicEvidence()))
        .hasVictoryWithPieces(expectedArithmeticPap)
        .hasJustification(progression(6, 9, 12, new ArithmeticEvidence(3)))
        .hasVictoryWithPieces(expectedCombinationPap)
        .hasJustification(
            progression(List.of(6, 9, 8, 12), new HarmonicEvidence(), new ArithmeticEvidence(3)));
  }

  @Test
  void testEvaluate_excellentVictory() {
    GameState state = PreDefinedState.progressionVictoryState_classicBoard_excellent(Player.BLACK);

    List<Victory> victories = rule.evaluate(state);

    assertThat(victories).hasSize(1);

    PieceAtPosition bs6 = PieceAtPositionHelper.create(PieceType.SQUARE, Player.BLACK, 6, "(15,3)");
    PieceAtPosition bt12 =
        PieceAtPositionHelper.create(PieceType.TRIANGLE, Player.BLACK, 12, "(15,0)");
    PieceAtPosition bc4 = PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 4, "(12,0)");
    PieceAtPosition bc9 = PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 9, "(12,3)");

    Set<PieceAtPosition> expectedCombinationPap = Set.of(bs6, bc9, bt12, bc4);

    ProperVictoryAssertion.from(victories)
        .hasWinner(Player.BLACK)
        .hasVictoryWithPieces(expectedCombinationPap)
        .hasJustification(
            progression(
                List.of(4, 9, 6, 12),
                new HarmonicEvidence(),
                new ArithmeticEvidence(3),
                new GeometricEvidence(1.5)));
  }

  @Test
  void testEvaluate_NoVictoryWithPyramid() {
    GameState state =
        PreDefinedState.progressionVictoryState_negative_mediumBoard_arithmetic_withPyramid(
            Player.BLACK);

    List<Victory> victories = rule.evaluate(state);

    ProperVictoryAssertion.from(victories).hasNoVictory();
  }

  @Test
  void testEvaluate_arithmeticVictoryWithPyramid() {
    GameState state =
        PreDefinedState.progressionVictoryState_mediumBoard_arithmetic_withPyramid(Player.BLACK);

    List<Victory> victories = rule.evaluate(state);

    assertThat(victories).hasSize(1);

    PieceAtPosition bp56 = state.board().getBlackPyramid().orElseThrow();
    PieceAtPosition bc16 =
        PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 16, "(7,1)");
    PieceAtPosition bc36 =
        PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 36, "(7,3)");

    Set<PieceAtPosition> expectedArithmeticPap = Set.of(bp56, bc36, bc16);

    ProperVictoryAssertion.from(victories)
        .hasWinner(Player.BLACK)
        .hasVictoryWithPieces(expectedArithmeticPap)
        .hasJustification(progression(16, 36, 56, new ArithmeticEvidence(20)));
  }
}
