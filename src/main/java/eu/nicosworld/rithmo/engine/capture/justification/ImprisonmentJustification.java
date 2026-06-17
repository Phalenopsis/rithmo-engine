package eu.nicosworld.rithmo.engine.capture.justification;

import eu.nicosworld.rithmo.engine.model.Position;
import java.util.List;

/**
 * {@code CaptureJustification} for Imprisonment
 *
 * @param regularMovesTo Possibles regular destinations for target
 * @param blockedAt positions where paths are blocked
 */
public record ImprisonmentJustification(List<Position> regularMovesTo, List<Position> blockedAt)
    implements CaptureJustification {}
