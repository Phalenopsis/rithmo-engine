package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.PlayerColor;

import java.util.Map;

public record VictoryState(Map<PlayerColor, Integer> captures) {

    public int get(PlayerColor color) {
        return captures.getOrDefault(color, 0);
    }
}