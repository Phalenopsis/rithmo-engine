package eu.nicosworld.rithmo.engine.math;

import static eu.nicosworld.rithmo.engine.math.ProgressionAssert.assertThatProgression;

import org.junit.jupiter.api.Test;

class ProgressionEngineTest {
  @Test
  void should_detect_arithmetic() {
    ProgressionAssert.assertThatProgression(15, 20, 25)
        .isArithmetic()
        .hasTripletArithmetic(15, 20, 25);
  }

  @Test
  void should_detect_geometric() {
    assertThatProgression(2, 6, 18).isGeometric();
  }

  @Test
  void should_detect_harmonic() {
    assertThatProgression(2, 3, 6).isHarmonic();
  }

  @Test
  void should_detect_multiple_progressions() {
    assertThatProgression(4, 6, 12, 36)
        .isGeometric()
        .isHarmonic()
        .isMultiple()
        .hasTripletGeometric(4, 12, 36)
        .hasTripletHarmonic(4, 6, 12);
  }

  @Test
  void should_accept_geometric_ratio_1() {
    ProgressionAssert.assertThatProgression(4, 4, 4).isGeometric().hasTripletGeometric(4, 4, 4);
  }

  @Test
  void should_detect_all_progression() {
    assertThatProgression(4, 6, 12, 9)
        .isArithmetic()
        .isGeometric()
        .isHarmonic()
        .isMultiple()
        .hasTripletArithmetic(6, 9, 12)
        .hasTripletGeometric(4, 6, 9)
        .hasTripletHarmonic(4, 6, 12);
  }

  @Test
  void should_detect_subset_progression() {
    ProgressionAssert.assertThatProgression(3, 7, 15, 20, 25).isArithmetic().hasTriplet(15, 20, 25);
  }

  @Test
  void should_reject_non_progression() {
    assertThatProgression(1, 4, 9, 25).isNotProgression();
  }
}
