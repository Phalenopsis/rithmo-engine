package eu.nicosworld.math;

/**
 * Represents a triplet of values that forms one or more mathematical progressions
 * in the context of the Rithmomachie game engine.
 *
 * <p>A triplet is always stored in normalized form:</p>
 * <ul>
 *     <li>{@code min} → smallest value</li>
 *     <li>{@code mid} → middle value</li>
 *     <li>{@code max} → largest value</li>
 * </ul>
 *
 * <p>This normalization ensures that progression checks (arithmetic, geometric, harmonic)
 * are independent of input order.</p>
 *
 * <p>The {@code mask} field encodes the detected progression types using
 * {@link ProgressionMask} bit flags. Multiple progression types can coexist
 * in the same triplet (e.g. arithmetic + harmonic).</p>
 *
 * <h2>Example</h2>
 * <pre>{@code
 * Triplet: 3, 6, 12
 *
 * - Arithmetic: 3 → 6 → 9 (false here unless specific context)
 * - Geometric: 3 → 6 → 12 (ratio 2)
 * - Harmonic: may also apply depending on relation rules
 * }</pre>
 *
 * <p>This class is immutable and thread-safe.</p>
 *
 * <p>It is primarily used by {@code ProgressionEngine} and
 * {@code ProgressionExplain} for detection and explanation purposes.</p>
 */
public record ProgressionTriplet(int min, int mid, int max, int mask) {

    /**
     * Creates a new normalized progression triplet.
     *
     * @param min  smallest value in the triplet
     * @param mid  middle value in the triplet
     * @param max  largest value in the triplet
     * @param mask bitmask representing detected progression types
     */
    public ProgressionTriplet {
    }

    /**
     * @return smallest value of the triplet
     */
    @Override
    public int min() {
        return min;
    }

    /**
     * @return middle value of the triplet
     */
    @Override
    public int mid() {
        return mid;
    }

    /**
     * @return largest value of the triplet
     */
    @Override
    public int max() {
        return max;
    }

    /**
     * Checks whether this triplet forms an arithmetic progression.
     *
     * @return true if arithmetic progression is detected
     */
    public boolean isArithmetic() {
        return (mask & ProgressionMask.ARITHMETIC) != 0;
    }

    /**
     * Checks whether this triplet forms a geometric progression.
     *
     * @return true if geometric progression is detected
     */
    public boolean isGeometric() {
        return (mask & ProgressionMask.GEOMETRIC) != 0;
    }

    /**
     * Checks whether this triplet forms a harmonic progression.
     *
     * <p>A harmonic progression is detected using the cross-multiplication rule:
     * {@code (c - b) * a == (b - a) * c}</p>
     *
     * @return true if harmonic progression is detected
     */
    public boolean isHarmonic() {
        return (mask & ProgressionMask.HARMONIC) != 0;
    }

    /**
     * @return raw bitmask representing all detected progression types
     */
    @Override
    public int mask() {
        return mask;
    }

    /**
     * Returns a human-readable representation of this triplet.
     *
     * <p>Mainly used for debugging and logs.</p>
     *
     * @return formatted string "[min, mid, max] (mask=X)"
     */
    @Override
    public String toString() {
        return "[" + min + ", " + mid + ", " + max + "] (mask=" + mask + ")";
    }
}