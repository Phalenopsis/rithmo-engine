package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import eu.nicosworld.rithmo.engine.testutils.victory.VictoryAssertion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LawsuitVictoryRuleTest {
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void testConstructor_nonPositiveRequiredCount_throwException(int value) {
        assertThatThrownBy(() -> new LawsuitVictoryRule(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Required digit count must be greater than 0.");
    }

    @Test
    void testEvaluate_returnEmpty() {
        GameState state = GameState.initial(Player.BLACK);
        LawsuitVictoryRule rule = new LawsuitVictoryRule(5);

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isNotVictory();
    }

    @Test
    void testEvaluate_digitCountIsSumOfDigitsAcrossCapturedPieces_returnVictory() {
        LawsuitVictoryRule rule = new LawsuitVictoryRule(4);
        GameState state = GameState.initial(Player.WHITE)
            .withAssets(PlayerColor.WHITE,
                PlayerAssets.empty()
                .addCaptured(new SimplePiece(PieceType.SQUARE, Player.BLACK, 15))
                .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.BLACK, 100))
        );

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isVictory()
            .isByLawsuit()
            .hasWinner(Player.WHITE)
            .hasDigitCount(5)
            .hasRequiredDigits(4);
    }

    @Test
    void testEvaluate_singleCaptureWithEnoughDigits_returnVictory() {
        LawsuitVictoryRule rule = new LawsuitVictoryRule(4);
        GameState state = GameState.initial(Player.WHITE)
            .withAssets(PlayerColor.WHITE,
                PlayerAssets.empty()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.BLACK, 15123))
            );

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isVictory()
            .isByLawsuit()
            .hasWinner(Player.WHITE)
            .hasDigitCount(5)
            .hasRequiredDigits(4);
    }

    @Test
    void testEvaluate_singleCaptureWithInsufficientDigits_returnEmpty() {
        LawsuitVictoryRule rule = new LawsuitVictoryRule(3);
        GameState state = GameState.initial(Player.WHITE)
            .withAssets(PlayerColor.WHITE,
                PlayerAssets.empty()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.BLACK, 15))
            );

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isNotVictory();
    }

    @Test
    void testEvaluate_manyCaptures_returnVictory() {
        LawsuitVictoryRule rule = new LawsuitVictoryRule(12);
        GameState state = GameState.initial(Player.WHITE)
            .withAssets(PlayerColor.WHITE,
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
                    .addCaptured(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 1))
            );

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isVictory()
            .isByLawsuit()
            .hasWinner(Player.WHITE)
            .hasDigitCount(12)
            .hasRequiredDigits(12);
    }

    @Test
    void testEvaluate_onlyOneCaptureAndStore_returnVictory() {
        LawsuitVictoryRule rule = new LawsuitVictoryRule(4);
        GameState state = GameState.initial(Player.WHITE)
            .withAssets(PlayerColor.WHITE,
                PlayerAssets.empty()
                    .captureAndStore(new SimplePiece(PieceType.SQUARE, Player.BLACK, 15123))
            );

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isVictory()
            .isByLawsuit()
            .hasWinner(Player.WHITE)
            .hasDigitCount(5)
            .hasRequiredDigits(4);
    }
}
