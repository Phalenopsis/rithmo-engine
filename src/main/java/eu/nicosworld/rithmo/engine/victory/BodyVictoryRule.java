package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PlayerAssets;
import eu.nicosworld.rithmo.engine.model.victory.BodyVictory;
import eu.nicosworld.rithmo.engine.model.victory.Victory;

import java.util.Optional;

public final class BodyVictoryRule extends AbstractThresholdVictoryRule implements VictoryRule {

    public BodyVictoryRule(int requiredCaptured) {
        super(requiredCaptured);
    }

    @Override
    protected String getValidityErrorMessage() {
        return "Required capture count must be greater than 0.";
    }

    @Override
    public Optional<Victory> evaluate(GameState state) {
        PlayerAssets assets = state.assetsOfCurrentPlayer();
        int captured = assets.capturedCount();
        if(captured >= required) {
            return Optional.of(new BodyVictory(
                state.currentPlayer(),
                captured,
                required
            ));
        }
        return Optional.empty();
    }


}
