package eu.nicosworld.rithmo.engine.capture;

import eu.nicosworld.rithmo.engine.capture.justification.CaptureJustification;
import eu.nicosworld.rithmo.engine.model.PieceType;
import eu.nicosworld.rithmo.engine.model.Position;
import java.util.List;

public record ExpectedCapture(
    PieceType capturedPieceType,
    int capturedValue,
    boolean isWhole,
    CaptureType captureType,
    CaptureJustification justification,
    List<Position> blockersPosition,
    Position attackerPos,
    Position targetPosition) {}
