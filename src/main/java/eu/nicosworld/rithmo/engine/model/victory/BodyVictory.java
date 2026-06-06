package eu.nicosworld.rithmo.engine.model.victory;

import eu.nicosworld.rithmo.engine.model.Player;

public record BodyVictory(Player winner, int capturedCount, int requiredCount) implements Victory {}
