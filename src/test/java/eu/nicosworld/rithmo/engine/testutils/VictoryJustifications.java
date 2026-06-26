package eu.nicosworld.rithmo.engine.testutils;

import eu.nicosworld.rithmo.engine.math.progression.model.ProgressionAnalysis;
import eu.nicosworld.rithmo.engine.math.progression.model.ProgressionEvidence;
import java.util.List;
import java.util.Set;

public final class VictoryJustifications {
  public static ProgressionAnalysis progression(
      int min, int mid, int max, ProgressionEvidence... evidences) {
    return new ProgressionAnalysis(List.of(min, mid, max), Set.of(evidences));
  }

  public static ProgressionAnalysis progression(
      List<Integer> values, ProgressionEvidence... evidences) {
    return new ProgressionAnalysis(values, Set.of(evidences));
  }
}
