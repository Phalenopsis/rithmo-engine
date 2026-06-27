package eu.nicosworld.rithmo.engine.math;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SubsetGeneratorTest {
  @Test
  void generateCombinations_NoCombination() {
    List<String> source = List.of("A", "B");
    List<Set<String>> result = SubsetGenerator.generateCombinations(source);

    assertThat(result).isEmpty();
  }

  @Test
  void generateCombinations_1Combination() {
    List<String> source = List.of("A", "B", "C");
    List<Set<String>> result = SubsetGenerator.generateCombinations(source);

    assertThat(result).containsExactlyInAnyOrder(Set.of("A", "B", "C"));
  }

  @Test
  void generateCombinations_3Combinations() {
    List<String> source = List.of("A", "B", "C", "D");
    List<Set<String>> result = SubsetGenerator.generateCombinations(source);

    assertThat(result)
        .containsExactlyInAnyOrder(
            Set.of("A", "B", "C"),
            Set.of("A", "B", "D"),
            Set.of("A", "C", "D"),
            Set.of("B", "C", "D"),
            Set.of("A", "B", "C", "D"));
  }

  @Test
  void generateCombinations_15Combinations() {
    List<String> source = List.of("A", "B", "C", "D", "E");
    List<Set<String>> result = SubsetGenerator.generateCombinations(source);

    assertThat(result)
        .containsExactlyInAnyOrder(
            Set.of("A", "B", "C"),
            Set.of("A", "B", "D"),
            Set.of("A", "B", "E"),
            Set.of("A", "C", "D"),
            Set.of("A", "C", "E"),
            Set.of("A", "D", "E"),
            Set.of("B", "C", "D"),
            Set.of("B", "C", "E"),
            Set.of("B", "D", "E"),
            Set.of("C", "D", "E"),
            Set.of("A", "B", "C", "D"),
            Set.of("A", "B", "C", "E"),
            Set.of("A", "B", "D", "E"),
            Set.of("A", "C", "D", "E"),
            Set.of("B", "C", "D", "E"));
  }
}
