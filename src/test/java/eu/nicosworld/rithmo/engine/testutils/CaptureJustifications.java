package eu.nicosworld.rithmo.engine.testutils;

import eu.nicosworld.rithmo.engine.capture.justification.*;

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
}
