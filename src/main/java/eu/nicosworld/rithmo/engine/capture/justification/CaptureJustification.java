package eu.nicosworld.rithmo.engine.capture.justification;

public sealed interface CaptureJustification
    permits AmbushJustification,
        AssaultJustification,
        EncounterJustification,
        ImprisonmentJustification,
        PowerJustification,
        ProgressionJustification {}
