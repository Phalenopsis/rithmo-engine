package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.exception.MultipleRulesException;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import java.util.*;

public class VictoryEngine {

  private final List<VictoryRule> rules;

  public VictoryEngine(List<VictoryRule> rules) {
    this.rules = rules;
    checkRulesUnicity();
  }

  public List<Victory> evaluate(GameState state) {
    List<Victory> victories = new ArrayList<>();
    for (VictoryRule rule : rules) {
      Optional<Victory> victory = rule.evaluate(state);

      victory.ifPresent(victories::add);
    }
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
