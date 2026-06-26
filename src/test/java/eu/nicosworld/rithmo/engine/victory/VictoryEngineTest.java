package eu.nicosworld.rithmo.engine.victory;

import static eu.nicosworld.rithmo.engine.testutils.CaptureJustifications.progression;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import eu.nicosworld.rithmo.engine.capture.justification.ArithmeticJustification;
import eu.nicosworld.rithmo.engine.capture.justification.GeometricJustification;
import eu.nicosworld.rithmo.engine.capture.justification.HarmonicJustification;
import eu.nicosworld.rithmo.engine.exception.MultipleRulesException;
import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.model.victory.BodyVictory;
import eu.nicosworld.rithmo.engine.model.victory.GoodsVictory;
import eu.nicosworld.rithmo.engine.model.victory.LawsuitVictory;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import eu.nicosworld.rithmo.engine.testutils.PieceAtPositionHelper;
import eu.nicosworld.rithmo.engine.testutils.PreDefinedState;
import eu.nicosworld.rithmo.engine.testutils.victory.ProperVictoryAssertion;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class VictoryEngineTest {
  @Test
  void testEvaluate_noRules_returnEmpty() {
    VictoryEngine engine = new VictoryEngine(List.of());

    GameState state = GameState.initial(Player.WHITE);

    List<Victory> result = engine.evaluate(state);

    assertThat(result).isEmpty();
  }

  @Test
  void testEvaluate_noVictory_returnEmpty() {
    GameState state = GameState.initial(Player.BLACK);
    BodyVictoryRule rule = new BodyVictoryRule(5);
    VictoryEngine engine = new VictoryEngine(List.of(rule));

    List<Victory> result = engine.evaluate(state);

    assertThat(result).isEmpty();
  }

  @Test
  void testCreateEngine_withMultipleBodyRules_throwException() {
    BodyVictoryRule rule1 = new BodyVictoryRule(5);
    BodyVictoryRule rule2 = new BodyVictoryRule(10);

    assertThatThrownBy(() -> new VictoryEngine(List.of(rule1, rule2)))
        .isInstanceOf(MultipleRulesException.class)
        .hasMessage("VictoryEngine cannot have more than one BODY rule.");
  }

  @Test
  void testEvaluate_LawsuitAndBody_return2Victories() {
    LawsuitVictoryRule lawsuitVictoryRule = new LawsuitVictoryRule(4);
    BodyVictoryRule bodyVictoryRule = new BodyVictoryRule(2);

    VictoryEngine engine = new VictoryEngine(List.of(lawsuitVictoryRule, bodyVictoryRule));

    GameState state =
        GameState.initial(Player.WHITE)
            .withAssets(
                PlayerColor.WHITE,
                PlayerAssets.empty()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.BLACK, 15))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.BLACK, 100)));

    List<Victory> result = engine.evaluate(state);

    assertThat(result)
        .isNotEmpty()
        .hasSize(2)
        .anyMatch(v -> v instanceof LawsuitVictory)
        .anyMatch(v -> v instanceof BodyVictory);
  }

  @Test
  void testEvaluate_LawsuitAndBody_return1Victory() {
    LawsuitVictoryRule lawsuitVictoryRule = new LawsuitVictoryRule(4);
    BodyVictoryRule bodyVictoryRule = new BodyVictoryRule(6);

    VictoryEngine engine = new VictoryEngine(List.of(lawsuitVictoryRule, bodyVictoryRule));

    GameState state =
        GameState.initial(Player.WHITE)
            .withAssets(
                PlayerColor.WHITE,
                PlayerAssets.empty()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.BLACK, 15))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.BLACK, 100)));

    List<Victory> result = engine.evaluate(state);

    assertThat(result).isNotEmpty().hasSize(1).anyMatch(v -> v instanceof LawsuitVictory);
  }

  @Test
  void testEvaluate_GoodsLawsuitAndBody_return3Victories() {
    LawsuitVictoryRule lawsuitVictoryRule = new LawsuitVictoryRule(4);
    BodyVictoryRule bodyVictoryRule = new BodyVictoryRule(2);
    GoodsVictoryRule goodsVictoryRule = new GoodsVictoryRule(110);

    VictoryEngine engine =
        new VictoryEngine(List.of(lawsuitVictoryRule, bodyVictoryRule, goodsVictoryRule));

    GameState state =
        GameState.initial(Player.WHITE)
            .withAssets(
                PlayerColor.WHITE,
                PlayerAssets.empty()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.BLACK, 15))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.BLACK, 100)));

    List<Victory> result = engine.evaluate(state);

    assertThat(result)
        .isNotEmpty()
        .hasSize(3)
        .anyMatch(v -> v instanceof LawsuitVictory)
        .anyMatch(v -> v instanceof GoodsVictory)
        .anyMatch(v -> v instanceof BodyVictory);
  }

  @Test
  void testEvaluate_opponentMeetsVictoryConditions_returnEmpty() {
    LawsuitVictoryRule lawsuitVictoryRule = new LawsuitVictoryRule(4);
    BodyVictoryRule bodyVictoryRule = new BodyVictoryRule(2);
    GoodsVictoryRule goodsVictoryRule = new GoodsVictoryRule(110);

    VictoryEngine engine =
        new VictoryEngine(List.of(lawsuitVictoryRule, bodyVictoryRule, goodsVictoryRule));

    GameState state =
        GameState.initial(Player.BLACK)
            .withAssets(
                PlayerColor.WHITE,
                PlayerAssets.empty()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.BLACK, 15))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.BLACK, 100)));

    List<Victory> result = engine.evaluate(state);

    assertThat(result).isEmpty();
  }

  @Test
  void testEvaluate_excellentVictory() {
    GameState state = PreDefinedState.progressionVictoryState_classicBoard_excellent(Player.BLACK);
    ProperVictoryRule rule = ProperVictoryRule.fast();
    VictoryEngine engine = new VictoryEngine(List.of(), rule);
    List<Victory> victories = engine.evaluate(state);

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
                new HarmonicJustification(),
                new ArithmeticJustification(3),
                new GeometricJustification(1.5)));
  }
}
