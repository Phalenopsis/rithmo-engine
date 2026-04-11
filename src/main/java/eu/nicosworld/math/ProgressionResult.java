package eu.nicosworld.math;

import java.util.Collections;
import java.util.List;

public final class ProgressionResult {

    private final int mask;
    private final List<ProgressionTriplet> triplets;

    public ProgressionResult(int mask, List<ProgressionTriplet> triplets) {
        this.mask = mask;
        this.triplets = List.copyOf(triplets);
    }

    public static ProgressionResult none() {
        return new ProgressionResult(0, Collections.emptyList());
    }

    public boolean isAny() {
        return mask != 0;
    }

    public boolean isArithmetic() {
        return (mask & ProgressionMask.ARITHMETIC) != 0;
    }

    public boolean isGeometric() {
        return (mask & ProgressionMask.GEOMETRIC) != 0;
    }

    public boolean isHarmonic() {
        return (mask & ProgressionMask.HARMONIC) != 0;
    }

    public int mask() {
        return mask;
    }

    public List<ProgressionTriplet> triplets() {
        return triplets;
    }
}