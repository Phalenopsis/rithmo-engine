package eu.nicosworld.rithmo.engine.capture;

import eu.nicosworld.rithmo.engine.capture.capturerule.ActiveCaptureRule;
import eu.nicosworld.rithmo.engine.capture.capturerule.CaptureRule;
import eu.nicosworld.rithmo.engine.capture.capturerule.GlobalCaptureRule;
import eu.nicosworld.rithmo.engine.capture.model.CaptureAction;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CaptureEngine {

  private final Set<ActiveCaptureRule> activeRules = new HashSet<>();
  private final Set<GlobalCaptureRule> globalRules = new HashSet<>();

  public CaptureEngine(List<CaptureRule> rules) {
    for (CaptureRule rule : rules) {
      switch (rule) {
        case ActiveCaptureRule activeRule -> activeRules.add(activeRule);
        case GlobalCaptureRule globalRule -> globalRules.add(globalRule);
      }
    }
  }

  /**
   * Factory for tests
   *
   * @return empty List
   */
  public static CaptureEngine empty() {
    return new CaptureEngine(List.of());
  }

  @Deprecated
  public List<CaptureAction> findCaptures(CaptureContext context) {

    return findActiveCaptures(context);
  }

  public List<CaptureAction> findActiveCaptures(CaptureContext context) {
    List<CaptureAction> result = new ArrayList<>();

    for (ActiveCaptureRule rule : activeRules) {
      result.addAll(rule.findCaptures(context));
    }

    return result;
  }

  public List<CaptureAction> findGlobalCaptures(CaptureContext context) {
    List<CaptureAction> result = new ArrayList<>();

    for (GlobalCaptureRule rule : globalRules) {
      result.addAll(rule.findCaptures(context));
    }

    return result;
  }
}
