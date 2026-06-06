package eu.nicosworld.rithmo.engine.capture.justification;

public record PowerJustification(
    int actorValue, PowerRelation relation, int degree, int targetValue)
    implements CaptureJustification {}
