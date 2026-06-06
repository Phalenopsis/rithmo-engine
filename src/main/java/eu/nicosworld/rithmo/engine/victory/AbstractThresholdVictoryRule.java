package eu.nicosworld.rithmo.engine.victory;

public abstract class AbstractThresholdVictoryRule {
  protected int required;

  AbstractThresholdVictoryRule(int required) {
    this.required = required;
    validate();
  }

  private void validate() {
    if (required <= 0) {
      throw new IllegalArgumentException(getValidityErrorMessage());
    }
  }

  protected abstract String getValidityErrorMessage();
}
