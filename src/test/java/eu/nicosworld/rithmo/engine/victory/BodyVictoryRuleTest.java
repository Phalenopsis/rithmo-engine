package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import eu.nicosworld.rithmo.engine.testutils.victory.VictoryAssertion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BodyVictoryRuleTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void testConstructor_nonPositiveRequiredCount_throwException(int value) {
        assertThatThrownBy(() -> new BodyVictoryRule(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Required capture count must be greater than 0.");
    }

    @Test
    void testEvaluate_returnFalse() {
        Board board = new Board();
        GameState state = GameState.initial(board, Player.BLACK);
        BodyVictoryRule rule = new BodyVictoryRule(5);

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isNotVictory();
    }

    @Test
    void testEvaluate_equalNeeded_returnTrue() {
        Board board = new Board();
        GameState state = GameState.initial(board, Player.BLACK);
        BodyVictoryRule rule = new BodyVictoryRule(2);
        state = state.withAssets(PlayerColor.BLACK,
                state.assetsOfCurrentPlayer()
                        .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 15))
                        .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 10))
        );

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isVictory()
            .isByBody()
            .hasWinner(Player.BLACK)
            .hasCapturedCount(2)
            .hasRequiredCount(2);
    }

    @Test
    void testEvaluate_higherThanRequiredCount_returnVictory() {
        GameState state = GameState.initial(Player.BLACK)
            .withAssets(PlayerColor.BLACK,
                PlayerAssets.empty()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 1))
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 1))
            );

        BodyVictoryRule rule = new BodyVictoryRule(2);

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isVictory()
            .isByBody()
            .hasCapturedCount(3)
            .hasRequiredCount(2);
    }

    @Test
    void testEvaluate_piecesInReserve_doNotCountForBodyVictory() {
        GameState state = GameState.initial(Player.BLACK)
            .withAssets(PlayerColor.BLACK,
                PlayerAssets.empty()
                    .addToReserve(new SimplePiece(PieceType.SQUARE, Player.WHITE, 100))
                    .addToReserve(new SimplePiece(PieceType.SQUARE, Player.WHITE, 100))
                    .addToReserve(new SimplePiece(PieceType.SQUARE, Player.WHITE, 100))
            );

        BodyVictoryRule rule = new BodyVictoryRule(1);

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isNotVictory();
    }

    @Test
    void testEvaluate_capturedPiecesAddedThroughCaptureAndStore_returnVictory() {
        GameState state = GameState.initial(Player.BLACK)
            .withAssets(PlayerColor.BLACK,
                PlayerAssets.empty()
                    .captureAndStore(new SimplePiece(PieceType.SQUARE, Player.WHITE, 1))
                    .captureAndStore(new SimplePiece(PieceType.SQUARE, Player.WHITE, 1))
                    .captureAndStore(new SimplePiece(PieceType.SQUARE, Player.WHITE, 1))
            );

        BodyVictoryRule rule = new BodyVictoryRule(2);

        Optional<Victory> result = rule.evaluate(state);

        VictoryAssertion.from(result)
            .isVictory()
            .isByBody()
            .hasCapturedCount(3)
            .hasRequiredCount(2);
    }
}