package eu.nicosworld.rithmo.engine.math.progression;

import java.util.Collections;
import java.util.List;

/**
 * Represents the result of a progression detection performed by {@link ProgressionEngine}.
 *
 * <p>A progression result encapsulates both the analyzed values and every progression detected
 * among them.
 *
 * <p>It contains:
 *
 * <ul>
 *   <li>the ordered values that were analyzed
 *   <li>a global bitmask indicating which progression types were detected
 *   <li>the list of all valid triplets matching at least one progression rule
 * </ul>
 *
 * <h2>Design intent</h2>
 *
 * <p>The result is immutable and can safely be reused by:
 *
 * <ul>
 *   <li>game rule evaluation
 *   <li>victory justification and explanation
 *   <li>UI feedback (highlighting detected progressions)
 *   <li>debugging and testing
 * </ul>
 *
 * <p>A single result may contain several progression types simultaneously (for example, arithmetic
 * and harmonic progressions detected on different triplets).
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
 * List<Integer> analyzedValues = result.values();
 *
 * for (ProgressionTriplet triplet : result.triplets()) {
 *     // inspect each detected progression
 * }
 * }</pre>
 */
public record ProgressionResult(List<Integer> values, int mask, List<ProgressionTriplet> triplets) {
  /**
   * Creates an immutable progression result.
   *
   * @param values the ordered values that were analyzed
   * @param mask bitmask of detected progression types
   * @param triplets detected progression triplets (defensively copied)
   */
  public ProgressionResult(List<Integer> values, int mask, List<ProgressionTriplet> triplets) {
    this.values = List.copyOf(values);
    this.mask = mask;
    this.triplets = List.copyOf(triplets);
  }

  /**
   * Returns an empty result (no progression detected).
   *
   * @return a result with empty mask and no triplets
   */
  public static ProgressionResult none() {
    return new ProgressionResult(List.of(), 0, Collections.emptyList());
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
