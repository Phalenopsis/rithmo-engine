package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoodsVictoryRuleTest {
    @Test
    void testIsSatisfied_notSatisfied_returnFalse() {
        Board board = new Board();
        GameState state = GameState.initial(board, Player.BLACK);
        int targetValue = 5;
        GoodsVictoryRule rule = new GoodsVictoryRule(targetValue);

        boolean result = rule.isSatisfied(state);

        assertFalse(result);
    }

    @Test
    void testIsSatisfied_equalToSumNeeded_returnTrue() {
        Board board = new Board();
        GameState state = GameState.initial(board, Player.BLACK);
        int targetValue = 25;
        GoodsVictoryRule rule = new GoodsVictoryRule(targetValue);
        state = state.withAssets(PlayerColor.BLACK,
                state.assetsOfCurrentPlayer()
                        .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 15))
                        .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 10))
        );

        boolean result = rule.isSatisfied(state);

        assertTrue(result);
    }

    @Test
    void testIsSatisfied_higherThanSumNeeded_returnTrue() {
        Board board = new Board();
        GameState state = GameState.initial(board, Player.BLACK);
        int targetValue = 25;
        GoodsVictoryRule rule = new GoodsVictoryRule(targetValue);
        state = state.withAssets(PlayerColor.BLACK,
                    state.assetsOfCurrentPlayer()
                    .addCaptured(new SimplePiece(PieceType.SQUARE, Player.WHITE, 15))
                    .addCaptured(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 10))
                    .addCaptured(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 4))
        );

        boolean result = rule.isSatisfied(state);

        assertTrue(result);
    }

    @Test
    void testIsSatisfied_capturedCountHigherButValueLower_returnFalse() {
        Board board = new Board();
        GameState state = GameState.initial(board, Player.BLACK);
        int targetValue = 30;
        GoodsVictoryRule rule = new GoodsVictoryRule(targetValue);
        state = state.withAssets(PlayerColor.BLACK,
            state.assetsOfCurrentPlayer()
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

        boolean result = rule.isSatisfied(state);

        assertFalse(result);
    }
}