package eu.nicosworld.rithmo.engine.math;

import java.util.ArrayList;
import java.util.List;

/**
 * ProgressionEngine is a lightweight rule engine used to detect numeric progressions inside a set
 * of integer values.
 *
 * <p>It supports three types of progressions:
 *
 * <ul>
 *   <li>Arithmetic progression (AP)
 *   <li>Geometric progression (GP)
 *   <li>Harmonic progression (HP)
 * </ul>
 *
 * <p>The engine evaluates all 3-combinations of the input values (nC3) and checks whether they form
 * at least one valid progression type depending on the configured mask.
 *
 * <p>Typical usage:
 *
 * <pre>{@code
 * ProgressionEngine engine = ProgressionEngine.fast();
 * ProgressionResult result = engine.detect(values);
 * }</pre>
 *
 * <p>The engine is immutable: configuration methods return new instances.
 *
 * @author eu.nicosworld
 */
public final class ProgressionEngine {

  private final int mask;

  /**
   * Creates a new engine with a given progression mask.
   *
   * <p>The mask defines which progression types are enabled (arithmetic, geometric, harmonic) using
   * bit flags.
   *
   * @param mask bitmask combining allowed progression types
   */
  private ProgressionEngine(int mask) {
    this.mask = mask;
  }

  /**
   * Creates a fully enabled engine.
   *
   * <p>All progression types are activated: arithmetic, geometric, and harmonic.
   *
   * @return a fully configured ProgressionEngine
   */
  public static ProgressionEngine fast() {
    return new ProgressionEngine(
        ProgressionMask.ARITHMETIC | ProgressionMask.GEOMETRIC | ProgressionMask.HARMONIC);
  }

  /**
   * Enables arithmetic progression detection.
   *
   * <p>Returns a new immutable engine instance.
   *
   * @return a new ProgressionEngine with arithmetic enabled
   */
  public ProgressionEngine allowArithmetic() {
    return new ProgressionEngine(mask | ProgressionMask.ARITHMETIC);
  }

  /**
   * Enables geometric progression detection.
   *
   * <p>Returns a new immutable engine instance.
   *
   * @return a new ProgressionEngine with geometric enabled
   */
  public ProgressionEngine allowGeometric() {
    return new ProgressionEngine(mask | ProgressionMask.GEOMETRIC);
  }

  /**
   * Enables harmonic progression detection.
   *
   * <p>Returns a new immutable engine instance.
   *
   * @return a new ProgressionEngine with harmonic enabled
   */
  public ProgressionEngine allowHarmonic() {
    return new ProgressionEngine(mask | ProgressionMask.HARMONIC);
  }

  /**
   * Detects all valid progressions inside the given values.
   *
   * <p>The method evaluates all combinations of 3 values (i, j, k) and checks whether they form:
   *
   * <ul>
   *   <li>An arithmetic progression: 2 * mid == min + max
   *   <li>A geometric progression: mid² == min * max
   *   <li>A harmonic progression: (max - mid) * min == (mid - min) * max
   * </ul>
   *
   * <p>Only progression types enabled by the engine mask are considered.
   *
   * <p>The result contains:
   *
   * <ul>
   *   <li>A global mask of detected progression types
   *   <li>All matching triplets (useful for debugging, UI, explanations)
   * </ul>
   *
   * <p>Optimization: the search stops early when all enabled progression types have been found.
   *
   * @param v input values (size ≥ 3, typically ≤ 8)
   * @return a ProgressionResult containing detected progressions and matching triplets
   */
  public ProgressionResult detect(int[] v) {

    int n = v.length;

    int globalMask = 0;
    List<ProgressionTriplet> triplets = new ArrayList<>();

    // max 56 combinations if n = 8
    for (int i = 0; i < n - 2; i++) {
      int a = v[i];

      for (int j = i + 1; j < n - 1; j++) {
        int b = v[j];

        for (int k = j + 1; k < n; k++) {
          int c = v[k];

          int min = Math.min(a, Math.min(b, c));
          int max = Math.max(a, Math.max(b, c));
          int mid = a + b + c - min - max;

          int localMask = 0;

          // ARITHMETIC
          if ((mask & ProgressionMask.ARITHMETIC) != 0) {
            if ((mid << 1) == min + max) {
              localMask |= ProgressionMask.ARITHMETIC;
            }
          }

          // GEOMETRIC
          if ((mask & ProgressionMask.GEOMETRIC) != 0) {
            if ((long) mid * mid == (long) min * max) {
              localMask |= ProgressionMask.GEOMETRIC;
            }
          }

          // HARMONIC
          if ((mask & ProgressionMask.HARMONIC) != 0) {
            if ((max - mid) * min == (mid - min) * max) {
              localMask |= ProgressionMask.HARMONIC;
            }
          }

          if (localMask != 0) {
            globalMask |= localMask;
            triplets.add(new ProgressionTriplet(min, mid, max, localMask));

            if (globalMask == mask) {
              return new ProgressionResult(globalMask, triplets);
            }
          }
        }
      }
    }

    if (globalMask == 0) {
      return ProgressionResult.none();
    }

    return new ProgressionResult(globalMask, triplets);
  }
}
