package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.GameState;

import java.util.List;

public class VictoryEngine {

    private final List<VictoryRule> rules;

    public VictoryEngine(List<VictoryRule> rules) {
        this.rules = rules;
    }

    public boolean check(GameState state) {
        return rules.stream().anyMatch(r -> r.isSatisfied(state));
    }
}