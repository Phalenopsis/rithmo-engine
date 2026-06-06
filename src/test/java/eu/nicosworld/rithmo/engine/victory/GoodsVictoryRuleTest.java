package eu.nicosworld.rithmo.engine.victory;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import eu.nicosworld.rithmo.engine.testutils.victory.VictoryAssertion;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GoodsVictoryRuleTest {
  @ParameterizedTest
  @ValueSource(ints = {0, -1, -10})
  void testConstructor_nonPositiveRequiredCount_throwException(int value) {
    assertThatThrownBy(() -> new GoodsVictoryRule(value))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Required captured value must be greater than 0.");
  }

  @Test
  void testEvaluate_returnEmpty() {
    GameState state = GameState.initial(Player.BLACK);
    int targetValue = 5;
    GoodsVictoryRule rule = new GoodsVictoryRule(targetValue);

    Optional<Victory> result = rule.evaluate(state);

    VictoryAssertion.from(result).isNotVictory();
  }

  @Test
  void testEvaluate_equalToSumNeeded_returnTrue() {
    int targetValue = 25;
    GoodsVictoryRule rule = new GoodsVictoryRule(targetValue);

    GameState state =
        GameState.initial(Player.BLACK)
            .withAssets(
                PlayerColor.BLACK,
                PlayerAssets.empty()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 15))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 10)));

    Optional<Victory> result = rule.evaluate(state);

    VictoryAssertion.from(result)
        .isVictory()
        .isByGoods()
        .hasVictorious(Player.BLACK)
        .hasCapturedValue(25)
        .hasRequiredValue(25);
  }

  @Test
  void testEvaluate_higherThanSumNeeded_returnTrue() {
    int targetValue = 25;
    GoodsVictoryRule rule = new GoodsVictoryRule(targetValue);
    GameState state =
        GameState.initial(Player.BLACK)
            .withAssets(
                PlayerColor.BLACK,
                PlayerAssets.empty()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 15))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 10))
                    .addCaptured(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 4)));

    Optional<Victory> result = rule.evaluate(state);

    VictoryAssertion.from(result)
        .isVictory()
        .isByGoods()
        .hasVictorious(Player.BLACK)
        .hasCapturedValue(29)
        .hasRequiredValue(25);
  }

  @Test
  void testEvaluate_manyCapturedPiecesButInsufficientValue_returnFalse() {
    int targetValue = 30;
    GoodsVictoryRule rule = new GoodsVictoryRule(targetValue);
    GameState state =
        GameState.initial(Player.BLACK)
            .withAssets(
                PlayerColor.BLACK,
                PlayerAssets.empty()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 1)));

    Optional<Victory> result = rule.evaluate(state);

    VictoryAssertion.from(result).isNotVictory();
  }

  @Test
  void testEvaluate_captureAndStoreCountsCapturedValue() {
    int targetValue = 25;

    GoodsVictoryRule rule = new GoodsVictoryRule(targetValue);

    GameState state =
        GameState.initial(Player.BLACK)
            .withAssets(
                PlayerColor.BLACK,
                PlayerAssets.empty()
                    .captureAndStore(new SimplePiece(PieceType.SQUARE, Player.WHITE, 15))
                    .captureAndStore(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 10)));

    Optional<Victory> result = rule.evaluate(state);

    VictoryAssertion.from(result).isVictory().isByGoods().hasCapturedValue(25);
  }
}
