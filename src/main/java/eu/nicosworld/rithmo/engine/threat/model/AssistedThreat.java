package eu.nicosworld.rithmo.engine.threat.model;

import eu.nicosworld.rithmo.engine.capture.model.InvolvedPiece;

public record AssistedThreat(InvolvedPiece actor, InvolvedPiece ally, InvolvedPiece target) {
  public int getActorValue() {
    return actor.specificComponent().getValue();
  }

  public int getAllyValue() {
    return ally.specificComponent().getValue();
  }

  public int getTargetValue() {
    return target.specificComponent().getValue();
  }
}
