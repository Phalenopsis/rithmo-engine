package eu.nicosworld.rithmo.engine.capture.justification;

public record AmbushJustification(
    int actorValue, AmbushOperator operator, int supporterValue, int targetValue)
    implements CaptureJustification {}
