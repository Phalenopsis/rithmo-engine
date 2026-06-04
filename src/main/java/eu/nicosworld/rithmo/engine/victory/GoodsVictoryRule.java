package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PlayerAssets;

public class GoodsVictoryRule implements VictoryRule {
    private final int sumOfValueToWin;

    public GoodsVictoryRule(int sumOfValueToWin) {
        this.sumOfValueToWin = sumOfValueToWin;
    }

    @Override
    public boolean isSatisfied(GameState state) {
        PlayerAssets assets = state.assetsOfCurrentPlayer();

        return assets.capturedValue() >= sumOfValueToWin;
    }
}
