package eu.nicosworld.rithmo.engine.model.victory;

import eu.nicosworld.rithmo.engine.model.Player;

public sealed interface Victory permits BodyVictory, GoodsVictory, LawsuitVictory, ProperVictory {
  Player winner();
}
