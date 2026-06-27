package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.justification.ProgressionCaptureJustification;
import eu.nicosworld.rithmo.engine.capture.model.CaptureAction;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import eu.nicosworld.rithmo.engine.math.progression.ProgressionEngine;
import eu.nicosworld.rithmo.engine.threat.model.AssistedThreat;
import java.util.ArrayList;
import java.util.List;

public final class ProgressionRule implements ActiveCaptureRule {
  private final ProgressionEngine engine;

  public ProgressionRule() {
    engine = ProgressionEngine.fast();
  }

  @Override
  public List<CaptureAction> findCaptures(CaptureContext context) {
    List<CaptureAction> captures = new ArrayList<>();

    for (AssistedThreat assistedThreat : context.regularAssistedThreat()) {
      int[] values = {
        assistedThreat.getActorValue(),
        assistedThreat.getTargetValue(),
        assistedThreat.getAllyValue()
      };

      engine
          .detect(values)
          .map(
              r ->
                  CaptureAction.progression(
                      assistedThreat.actor(),
                      assistedThreat.target(),
                      assistedThreat.ally(),
                      ProgressionCaptureJustification.from(r)))
          .ifPresent(captures::add);
    }

    return captures;
  }
}
