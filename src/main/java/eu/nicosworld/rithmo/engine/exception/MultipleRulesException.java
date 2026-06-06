package eu.nicosworld.rithmo.engine.exception;

public class MultipleRulesException extends RuntimeException {
  public MultipleRulesException(String rule) {
    super("VictoryEngine cannot have more than one " + rule + " rule.");
  }
}
