package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PlayerAssets;
import eu.nicosworld.rithmo.engine.model.victory.GoodsVictory;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import java.util.Optional;

public final class GoodsVictoryRule extends AbstractThresholdVictoryRule implements VictoryRule {
  public GoodsVictoryRule(int requiredValue) {
    super(requiredValue);
  }

  @Override
  protected String getValidityErrorMessage() {
    return "Required captured value must be greater than 0.";
  }

  @Override
  public Optional<Victory> evaluate(GameState state) {
    PlayerAssets assets = state.assetsOfCurrentPlayer();
    if (assets.capturedValue() >= required) {
      return Optional.of(new GoodsVictory(state.currentPlayer(), assets.capturedValue(), required));
    }
    return Optional.empty();
  }
}
