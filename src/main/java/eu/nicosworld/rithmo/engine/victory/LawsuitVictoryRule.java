package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.victory.LawsuitVictory;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import java.util.Optional;

public final class LawsuitVictoryRule extends AbstractThresholdVictoryRule implements VictoryRule {

  public LawsuitVictoryRule(int requiredDigitsCount) {
    super(requiredDigitsCount);
  }

  @Override
  protected String getValidityErrorMessage() {
    return "Required digit count must be greater than 0.";
  }

  @Override
  public Optional<Victory> evaluate(GameState state) {
    int actualDigits = state.assetsOfCurrentPlayer().capturedDigitCount();

    if (actualDigits >= required) {
      return Optional.of(new LawsuitVictory(state.currentPlayer(), actualDigits, required));
    }

    return Optional.empty();
  }
}
