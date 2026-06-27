package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.exception.MultipleRulesException;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import java.util.*;

public class VictoryEngine {

  private final List<VictoryRule> rules;
  private final ProperVictoryRule properRule;

  public VictoryEngine(List<VictoryRule> rules, ProperVictoryRule properRule) {
    this.rules = rules;
    this.properRule = properRule;
    checkRulesUnicity();
  }

  public VictoryEngine(List<VictoryRule> rules) {
    this.rules = rules;
    this.properRule = null;
    checkRulesUnicity();
  }

  public List<Victory> evaluate(GameState state) {
    List<Victory> victories = new ArrayList<>();
    for (VictoryRule rule : rules) {
      Optional<Victory> victory = rule.evaluate(state);

      victory.ifPresent(victories::add);
    }
    if (Objects.nonNull(properRule)) victories.addAll(properRule.evaluate(state));
    return victories;
  }

  private void checkRulesUnicity() {
    Set<VictoryType> seen = new HashSet<>();

    for (VictoryRule rule : rules) {
      VictoryType type = VictoryType.from(rule);

      if (!seen.add(type)) {
        throw new MultipleRulesException(type.name());
      }
    }
  }
}
