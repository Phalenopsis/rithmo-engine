package eu.nicosworld.rithmo.engine.math.progression;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

public class ProgressionAssert {

  private final int[] values;
  private final ProgressionResult result;

  private ProgressionAssert(ProgressionEngine engine, int... values) {
    this.values = values;
    this.result = engine.detect(values);
  }

  // ===== ENTRY POINT =====

  public static ProgressionAssert assertThatProgression(int... values) {
    return new ProgressionAssert(ProgressionEngine.fast(), values);
  }

  public static ProgressionAssert assertThatProgression(ProgressionEngine engine, int... values) {
    return new ProgressionAssert(engine, values);
  }

  // ===== ASSERTIONS =====

  public ProgressionAssert isArithmetic() {
    assertTrue(result.isArithmetic(), () -> error("Expected arithmetic progression"));
    return this;
  }

  public ProgressionAssert isGeometric() {
    assertTrue(result.isGeometric(), () -> error("Expected geometric progression"));
    return this;
  }

  public ProgressionAssert isHarmonic() {
    assertTrue(result.isHarmonic(), () -> error("Expected harmonic progression"));
    return this;
  }

  public ProgressionAssert isNotProgression() {
    assertTrue(!result.isAny(), () -> error("Expected no progression"));
    return this;
  }

  public ProgressionAssert hasAny() {
    assertTrue(result.isAny(), () -> error("Expected at least one progression"));
    return this;
  }

  public ProgressionAssert isMultiple() {
    int count = Integer.bitCount(result.mask());
    assertTrue(count >= 2, () -> error("Expected multiple progression types"));
    return this;
  }

  // ===== TRIPLETS =====

  public ProgressionAssert hasTriplet(int a, int b, int c) {

    int min = Math.min(a, Math.min(b, c));
    int max = Math.max(a, Math.max(b, c));
    int mid = a + b + c - min - max;

    boolean found =
        result.triplets().stream()
            .anyMatch(t -> t.min() == min && t.mid() == mid && t.max() == max);

    assertTrue(found, () -> error("Expected triplet [" + min + "," + mid + "," + max + "]"));

    return this;
  }

  public ProgressionAssert hasTripletArithmetic(int a, int b, int c) {
    return hasTripletOfType(a, b, c, ProgressionMask.ARITHMETIC, "arithmetic");
  }

  public ProgressionAssert hasTripletGeometric(int a, int b, int c) {
    return hasTripletOfType(a, b, c, ProgressionMask.GEOMETRIC, "geometric");
  }

  public ProgressionAssert hasTripletHarmonic(int a, int b, int c) {
    return hasTripletOfType(a, b, c, ProgressionMask.HARMONIC, "harmonic");
  }

  private ProgressionAssert hasTripletOfType(int a, int b, int c, int typeMask, String label) {

    int min = Math.min(a, Math.min(b, c));
    int max = Math.max(a, Math.max(b, c));
    int mid = a + b + c - min - max;

    boolean found =
        result.triplets().stream()
            .anyMatch(
                t ->
                    t.min() == min
                        && t.mid() == mid
                        && t.max() == max
                        && (t.mask() & typeMask) != 0);

    assertTrue(
        found, () -> error("Expected " + label + " triplet [" + min + "," + mid + "," + max + "]"));

    return this;
  }

  // ===== DEBUG =====

  private String error(String message) {
    return message
        + " for values "
        + Arrays.toString(values)
        + " but got mask="
        + result.mask()
        + " triplets="
        + result.triplets();
  }
}
