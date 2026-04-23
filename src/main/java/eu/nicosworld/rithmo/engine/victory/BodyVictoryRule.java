package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PlayerAssets;

public class BodyVictoryRule implements VictoryRule {
    private final int captureQuantityToWin;

    public BodyVictoryRule(int captureQuantityToWin) {
        this.captureQuantityToWin = captureQuantityToWin;
    }

    @Override
    public boolean isSatisfied(GameState state) {
        PlayerAssets assets = state.assetsOfCurrentPlayer();

        return assets.capturedCount() >= captureQuantityToWin;
    }
}
