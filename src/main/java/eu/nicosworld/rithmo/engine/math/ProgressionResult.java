package eu.nicosworld.rithmo.engine.math;

import java.util.Collections;
import java.util.List;

/**
 * Represents the result of a progression detection performed by {@link ProgressionEngine}.
 *
 * <p>This class contains both:
 *
 * <ul>
 *   <li>A global bitmask indicating which progression types were detected
 *   <li>The list of all valid triplets that matched at least one progression rule
 * </ul>
 *
 * <h2>Design intent</h2>
 *
 * <p>The result is immutable and safe to use for:
 *
 * <ul>
 *   <li>game rule evaluation (capture logic)
 *   <li>UI feedback (highlighting valid progressions)
 *   <li>debug / explanation layer
 * </ul>
 *
 * <p>A single result may contain multiple progression types simultaneously (e.g. arithmetic +
 * harmonic on different triplets).
 *
 * <h2>Bitmask usage</h2>
 *
 * <p>The internal mask uses {@link ProgressionMask} constants:
 *
 * <ul>
 *   <li>ARITHMETIC
 *   <li>GEOMETRIC
 *   <li>HARMONIC
 * </ul>
 *
 * <h2>Example</h2>
 *
 * <pre>{@code
 * ProgressionResult result = engine.detect(values);
 *
 * if (result.isArithmetic()) {
 *     // at least one arithmetic progression was found
 * }
 *
 * for (ProgressionTriplet t : result.triplets()) {
 *     // display or process each detected triplet
 * }
 * }</pre>
 */
public record ProgressionResult(int mask, List<ProgressionTriplet> triplets) {

  /**
   * Creates a progression result.
   *
   * @param mask bitmask of detected progression types
   * @param triplets list of detected valid triplets (immutable copy is created internally)
   */
  public ProgressionResult(int mask, List<ProgressionTriplet> triplets) {
    this.mask = mask;
    this.triplets = List.copyOf(triplets);
  }

  /**
   * Returns an empty result (no progression detected).
   *
   * @return a result with empty mask and no triplets
   */
  public static ProgressionResult none() {
    return new ProgressionResult(0, Collections.emptyList());
  }

  /**
   * @return true if at least one progression was detected
   */
  public boolean isAny() {
    return mask != 0;
  }

  /**
   * @return true if at least one arithmetic progression exists in the result
   */
  public boolean isArithmetic() {
    return (mask & ProgressionMask.ARITHMETIC) != 0;
  }

  /**
   * @return true if at least one geometric progression exists in the result
   */
  public boolean isGeometric() {
    return (mask & ProgressionMask.GEOMETRIC) != 0;
  }

  /**
   * @return true if at least one harmonic progression exists in the result
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
   * Returns all detected triplets.
   *
   * <p>Each triplet is normalized and annotated with its own progression mask.
   *
   * @return immutable list of progression triplets
   */
  @Override
  public List<ProgressionTriplet> triplets() {
    return triplets;
  }
}
