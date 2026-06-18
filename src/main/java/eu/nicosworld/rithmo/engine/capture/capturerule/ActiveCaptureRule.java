package eu.nicosworld.rithmo.engine.capture.capturerule;

public sealed interface ActiveCaptureRule extends CaptureRule
    permits AmbushRule, AssaultRule, EncounterRule, PowerRule, ProgressionRule {}
