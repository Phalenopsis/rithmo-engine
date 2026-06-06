package eu.nicosworld.rithmo.engine.model.victory;

import eu.nicosworld.rithmo.engine.model.Player;

public record LawsuitVictory(
    Player winner,
    int capturedDigitCount,
    int requiredDigitCount
) implements Victory { }

