package eu.nicosworld.rithmoEngine.capture;

import eu.nicosworld.rithmoEngine.capture.CaptureType;
import eu.nicosworld.rithmoEngine.model.PieceType;

public record ExpectedCapture(
        PieceType type,
        int value,
        boolean isWhole,
        CaptureType captureType
) {}
