package eu.nicosworld.math;

public final class ProgressionTriplet {

    private final int min;
    private final int mid;
    private final int max;
    private final int mask;

    public ProgressionTriplet(int min, int mid, int max, int mask) {
        this.min = min;
        this.mid = mid;
        this.max = max;
        this.mask = mask;
    }

    public int min() { return min; }
    public int mid() { return mid; }
    public int max() { return max; }

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

    @Override
    public String toString() {
        return "[" + min + ", " + mid + ", " + max + "] (mask=" + mask + ")";
    }
}