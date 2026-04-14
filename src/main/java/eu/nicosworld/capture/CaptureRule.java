package eu.nicosworld.capture;

import java.util.List;

public interface CaptureRule {

    List<CaptureAction> findCaptures(CaptureContext context);
}
