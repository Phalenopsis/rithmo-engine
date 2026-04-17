package eu.nicosworld.capture.capturerule;

import eu.nicosworld.capture.AbstractCaptureRule;
import eu.nicosworld.capture.CaptureAction;
import eu.nicosworld.capture.CaptureContext;
import eu.nicosworld.move.FreePathMovementValidator;
import eu.nicosworld.move.RegularMoveGenerator;

import java.util.List;

public class AssaultRule extends AbstractCaptureRule {
    public AssaultRule(RegularMoveGenerator generator,
                      FreePathMovementValidator pathValidator) {
        super(generator, pathValidator);
    }

    @Override
    public List<CaptureAction> findCaptures(CaptureContext context) {
        return List.of();
    }
}
