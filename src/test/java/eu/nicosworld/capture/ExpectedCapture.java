package eu.nicosworld.capture;

import eu.nicosworld.model.PieceType;

public record ExpectedCapture(
        PieceType type,
        int value,
        boolean isWhole,
        CaptureType captureType
) {}
