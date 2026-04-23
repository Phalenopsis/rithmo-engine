package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.GameState;

public interface VictoryRule {
    boolean isSatisfied(GameState state);
}
