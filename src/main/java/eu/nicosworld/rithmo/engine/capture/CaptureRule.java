package eu.nicosworld.rithmo.engine.capture;

import java.util.List;

public interface CaptureRule {

    List<CaptureAction> findCaptures(CaptureContext context);
}
