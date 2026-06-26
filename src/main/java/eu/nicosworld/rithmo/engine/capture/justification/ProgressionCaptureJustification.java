package eu.nicosworld.rithmo.engine.capture.justification;

import eu.nicosworld.rithmo.engine.math.progression.ProgressionResult;
import eu.nicosworld.rithmo.engine.math.progression.model.ProgressionAnalysis;

public record ProgressionCaptureJustification(ProgressionAnalysis analysis)
    implements CaptureJustification {
  public static ProgressionCaptureJustification from(ProgressionResult result) {
    return new ProgressionCaptureJustification(ProgressionAnalysis.from(result));
  }
}
