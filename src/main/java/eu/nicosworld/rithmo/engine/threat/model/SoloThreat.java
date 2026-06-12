package eu.nicosworld.rithmo.engine.threat.model;

import eu.nicosworld.rithmo.engine.capture.model.InvolvedPiece;

public record SoloThreat(InvolvedPiece actor, InvolvedPiece target) {
  public int getActorValue() {
    return actor.specificComponent().getValue();
  }

  public int getTargetValue() {
    return target.specificComponent().getValue();
  }
}
