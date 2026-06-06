package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import java.util.Optional;

public sealed interface VictoryRule permits BodyVictoryRule, GoodsVictoryRule, LawsuitVictoryRule {
  Optional<Victory> evaluate(GameState state);
}
