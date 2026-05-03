package eu.nicosworld.rithmo.engine.capture;

import eu.nicosworld.rithmo.engine.capture.model.CaptureAction;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;

import java.util.List;

public interface CaptureRule {

    List<CaptureAction> findCaptures(CaptureContext context);
}
