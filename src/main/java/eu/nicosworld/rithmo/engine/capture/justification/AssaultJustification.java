package eu.nicosworld.rithmo.engine.capture.justification;

public record AssaultJustification(
        int distance,
        AssaultOperator operator,
        int actorValue,
        int targetValue
) implements CaptureJustification {}