package eu.nicosworld.rithmoEngine.capture.capturerule;

import eu.nicosworld.rithmoEngine.capture.AbstractCaptureRule;
import eu.nicosworld.rithmoEngine.capture.CaptureAction;
import eu.nicosworld.rithmoEngine.capture.CaptureContext;
import eu.nicosworld.rithmoEngine.move.FreePathMovementValidator;
import eu.nicosworld.rithmoEngine.move.RegularMoveGenerator;

import java.util.List;

public class PowerRule extends AbstractCaptureRule {
    public PowerRule(RegularMoveGenerator generator,
                         FreePathMovementValidator pathValidator) {
        super(generator, pathValidator);
    }

    @Override
    public List<CaptureAction> findCaptures(CaptureContext context) {
        return List.of();
    }
}
