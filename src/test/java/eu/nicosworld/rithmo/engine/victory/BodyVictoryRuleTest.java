package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BodyVictoryRuleTest {

    @Test
    void testIsSatisfied_notSatisfied_returnFalse() {
        Board board = new Board();
        GameState state = GameState.initial(board, Player.BLACK);
        BodyVictoryRule rule = new BodyVictoryRule(5);

        boolean result = rule.isSatisfied(state);

        assertFalse(result);
    }

    @Test
    void testIsSatisfied_equalNeeded_returnTrue() {
        Board board = new Board();
        GameState state = GameState.initial(board, Player.BLACK);
        BodyVictoryRule rule = new BodyVictoryRule(2);
        state = state.withAssets(PlayerColor.BLACK,
                state.assetsOfCurrentPlayer()
                        .addToReserve(new SimplePiece(PieceType.SQUARE, Player.WHITE, 15))
                        .addToReserve(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 10))
        );

        boolean result = rule.isSatisfied(state);

        assertFalse(result);
    }

    @Test
    void testIsSatisfied_higherThanSumNeeded_returnTrue() {
        Board board = new Board();
        GameState state = GameState.initial(board, Player.BLACK);
        BodyVictoryRule rule = new BodyVictoryRule(25);
        state = state.withAssets(PlayerColor.BLACK,
                state.assetsOfCurrentPlayer()
                        .addToReserve(new SimplePiece(PieceType.SQUARE, Player.WHITE, 15))
                        .addToReserve(new SimplePiece(PieceType.TRIANGLE, Player.WHITE, 10))
                        .addToReserve(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 4))
        );

        boolean result = rule.isSatisfied(state);

        assertFalse(result);
    }
}