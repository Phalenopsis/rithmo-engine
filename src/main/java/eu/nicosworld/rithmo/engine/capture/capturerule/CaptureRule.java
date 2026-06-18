package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.model.CaptureAction;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import java.util.List;

public sealed interface CaptureRule permits GlobalCaptureRule, ActiveCaptureRule {

  List<CaptureAction> findCaptures(CaptureContext context);
}
