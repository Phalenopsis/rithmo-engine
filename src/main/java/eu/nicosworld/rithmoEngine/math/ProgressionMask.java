package eu.nicosworld.rithmoEngine.math;

/**
 * Utility class representing progression types using a bitmask.
 *
 * <p>A bitmask is an integer where each bit represents a boolean flag.
 * This allows multiple options to be combined efficiently in a single value,
 * using bitwise operations instead of collections or multiple booleans.</p>
 *
 * <p>Each progression type is assigned to a unique bit:</p>
 *
 * <ul>
 *     <li>{@code ARITHMETIC = 1}      → binary {@code 0001}</li>
 *     <li>{@code GEOMETRIC  = 1 << 1} → binary {@code 0010}</li>
 *     <li>{@code HARMONIC   = 1 << 2} → binary {@code 0100}</li>
 * </ul>
 *
 * <p>These values can be combined using the bitwise OR operator ({@code |}):</p>
 *
 * <pre>{@code
 * int mask = ARITHMETIC | GEOMETRIC; // 0011
 * }</pre>
 *
 * <p>To check whether a specific progression type is enabled in a mask,
 * use the bitwise AND operator ({@code &}):</p>
 *
 * <pre>{@code
 * if ((mask & ARITHMETIC) != 0) {
 *     // arithmetic progression is enabled
 * }
 * }</pre>
 *
 * <p>This approach is preferred over collections or multiple boolean fields
 * because it is:</p>
 * <ul>
 *     <li>More memory-efficient (single integer)</li>
 *     <li>Faster (no allocation, no hashing, CPU-friendly)</li>
 *     <li>Easily extensible (new flags can be added with additional bits)</li>
 * </ul>
 *
 * <p>Typical usage in the progression engine is to enable or disable
 * specific progression rules (arithmetic, geometric, harmonic)
 * during evaluation without incurring runtime overhead.</p>
 */
public final class ProgressionMask {

    public static final int ARITHMETIC = 1;
    public static final int GEOMETRIC  = 1 << 1;
    public static final int HARMONIC   = 1 << 2;

    private ProgressionMask() {
        throw new AssertionError("No instances");
    }
}
