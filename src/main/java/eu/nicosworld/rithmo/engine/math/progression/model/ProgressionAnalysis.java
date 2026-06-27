package eu.nicosworld.rithmo.engine.math.progression.model;

import eu.nicosworld.rithmo.engine.math.progression.ProgressionResult;
import java.util.List;
import java.util.Set;

public record ProgressionAnalysis(List<Integer> values, Set<ProgressionEvidence> evidences) {
  public ProgressionAnalysis {
    values = values.stream().sorted().toList();

    evidences = Set.copyOf(evidences);
  }

  public static ProgressionAnalysis from(ProgressionResult result) {
    return new ProgressionAnalysis(result.values(), ProgressionEvidence.from(result));
  }
}
