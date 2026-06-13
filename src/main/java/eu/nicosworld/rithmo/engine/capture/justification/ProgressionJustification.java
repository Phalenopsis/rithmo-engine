package eu.nicosworld.rithmo.engine.capture.justification;

import eu.nicosworld.rithmo.engine.math.ProgressionResult;
import eu.nicosworld.rithmo.engine.math.ProgressionTriplet;
import java.util.HashSet;
import java.util.Set;

public record ProgressionJustification(
    int min, int mid, int max, Set<ProgressionEvidence> evidences) implements CaptureJustification {
  public static ProgressionJustification from(ProgressionResult result) {

    if (!result.isAny()) {
      throw new IllegalArgumentException("No progression detected");
    }

    ProgressionTriplet triplet = result.triplets().getFirst();

    Set<ProgressionEvidence> evidences = new HashSet<>();

    if (triplet.isArithmetic()) {
      evidences.add(new ArithmeticJustification(triplet.mid() - triplet.min()));
    }

    if (triplet.isGeometric()) {
      evidences.add(new GeometricJustification(triplet.mid() / triplet.min()));
    }

    if (triplet.isHarmonic()) {
      evidences.add(new HarmonicJustification());
    }

    return new ProgressionJustification(triplet.min(), triplet.mid(), triplet.max(), evidences);
  }
}
