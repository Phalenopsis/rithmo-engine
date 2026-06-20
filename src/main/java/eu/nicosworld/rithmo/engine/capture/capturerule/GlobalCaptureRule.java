package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.model.CaptureAction;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import java.util.List;

public sealed interface GlobalCaptureRule extends CaptureRule permits ImprisonmentRule {
  List<CaptureAction> findPreMoveCaptures(CaptureContext context);

  List<CaptureAction> findPostMoveCaptures(CaptureContext context);

  List<CaptureAction> findEndTurnCaptures(CaptureContext context);
}
