package eu.nicosworld.rithmo.engine.testutils.victory;

import static org.assertj.core.api.Assertions.assertThat;

import eu.nicosworld.rithmo.engine.model.victory.LawsuitVictory;

public class LawsuitVictoryAssertion
    extends AbstractVictoryAssertion<LawsuitVictory, LawsuitVictoryAssertion> {

  protected LawsuitVictoryAssertion(LawsuitVictory victory) {
    super(victory);
  }

  @Override
  protected LawsuitVictoryAssertion self() {
    return this;
  }

  public LawsuitVictoryAssertion hasDigitCount(int expected) {
    assertThat(victory.capturedDigitCount()).isEqualTo(expected);
    return this;
  }

  public LawsuitVictoryAssertion hasRequiredDigits(int expected) {
    assertThat(victory.requiredDigitCount()).isEqualTo(expected);
    return this;
  }
}
