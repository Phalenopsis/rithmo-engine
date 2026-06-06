package eu.nicosworld.rithmo.engine.model.victory;

import eu.nicosworld.rithmo.engine.model.Player;

public record GoodsVictory(Player winner, int capturedValue, int requiredValue)
    implements Victory {}
