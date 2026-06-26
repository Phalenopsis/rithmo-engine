package eu.nicosworld.rithmo.engine.capture.justification;

import eu.nicosworld.rithmo.engine.math.progression.ProgressionResult;
import java.util.List;
import java.util.Set;

public record ProgressionJustification(List<Integer> values, Set<ProgressionEvidence> evidences)
    implements CaptureJustification {
  public ProgressionJustification {
    values = values.stream().sorted().toList();

    evidences = Set.copyOf(evidences);
  }

  public static ProgressionJustification from(ProgressionResult result) {
    if (!result.isAny()) {
      throw new IllegalArgumentException("No progression detected");
    }

    return new ProgressionJustification(result.values(), ProgressionEvidence.from(result));
  }
}
