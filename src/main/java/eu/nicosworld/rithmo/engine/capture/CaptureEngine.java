package eu.nicosworld.rithmo.engine.capture;

import eu.nicosworld.rithmo.engine.capture.capturerule.CaptureRule;
import eu.nicosworld.rithmo.engine.capture.model.CaptureAction;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import java.util.ArrayList;
import java.util.List;

public class CaptureEngine {

  private final List<CaptureRule> rules;

  public CaptureEngine(List<CaptureRule> rules) {
    this.rules = rules;
  }

  /**
   * Factory for tests
   *
   * @return empty List
   */
  public static CaptureEngine empty() {
    return new CaptureEngine(List.of());
  }

  public List<CaptureAction> findCaptures(CaptureContext context) {

    List<CaptureAction> result = new ArrayList<>();

    for (CaptureRule rule : rules) {
      result.addAll(rule.findCaptures(context));
    }

    return result;
  }
}
