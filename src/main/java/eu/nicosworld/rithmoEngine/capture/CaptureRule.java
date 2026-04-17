package eu.nicosworld.rithmoEngine.capture;

import java.util.List;

public interface CaptureRule {

    List<CaptureAction> findCaptures(CaptureContext context);
}
