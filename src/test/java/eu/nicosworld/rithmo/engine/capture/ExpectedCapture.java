package eu.nicosworld.rithmo.engine.capture;

import eu.nicosworld.rithmo.engine.capture.justification.CaptureJustification;
import eu.nicosworld.rithmo.engine.model.PieceType;

public record ExpectedCapture(
    PieceType capturedPieceType,
    int capturedValue,
    boolean isWhole,
    CaptureType captureType,
    CaptureJustification justification) {}
