package eu.nicosworld.rithmo.engine.capture.model;

import eu.nicosworld.rithmo.engine.model.Board;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.threat.model.AssistedThreat;
import eu.nicosworld.rithmo.engine.threat.model.RegularThreats;
import eu.nicosworld.rithmo.engine.threat.model.SoloThreat;
import java.util.List;

/**
 * Snapshot of the game state focused on a specific piece to evaluate its capture opportunities at
 * its current location.
 */
public record CaptureContext(GameState state, PieceAtPosition actor, RegularThreats regular) {
  public Board board() {
    return state.board();
  }

  public List<AssistedThreat> regularAssistedThreat() {
    return regular.assistedThreats();
  }

  public List<SoloThreat> regularSoloThreat() {
    return regular.soloThreats();
  }
}
