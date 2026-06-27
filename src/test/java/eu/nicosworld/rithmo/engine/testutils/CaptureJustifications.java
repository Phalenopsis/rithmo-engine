package eu.nicosworld.rithmo.engine.testutils;

import eu.nicosworld.rithmo.engine.capture.justification.*;
import eu.nicosworld.rithmo.engine.math.progression.model.ProgressionAnalysis;
import eu.nicosworld.rithmo.engine.math.progression.model.ProgressionEvidence;
import java.util.List;
import java.util.Set;

public final class CaptureJustifications {

  private CaptureJustifications() {}

  public static AssaultJustification assault(
      int distance, AssaultOperator operator, int actorValue, int targetValue) {
    return new AssaultJustification(distance, operator, actorValue, targetValue);
  }

  public static EncounterJustification encounter(int matchedValue) {
    return new EncounterJustification(matchedValue);
  }

  public static AmbushJustification ambush(
      int actorValue, AmbushOperator operator, int supporterValue, int targetValue) {
    return new AmbushJustification(actorValue, operator, supporterValue, targetValue);
  }

  public static PowerJustification power(
      int actorValue, PowerRelation relation, int degree, int targetValue) {
    return new PowerJustification(actorValue, relation, degree, targetValue);
  }

  public static ProgressionCaptureJustification progression(
      int min, int mid, int max, ProgressionEvidence... evidences) {
    return new ProgressionCaptureJustification(
        new ProgressionAnalysis(List.of(min, mid, max), Set.of(evidences)));
  }

  public static ProgressionCaptureJustification progression(
      List<Integer> values, ProgressionEvidence... evidences) {
    return new ProgressionCaptureJustification(new ProgressionAnalysis(values, Set.of(evidences)));
  }
}
