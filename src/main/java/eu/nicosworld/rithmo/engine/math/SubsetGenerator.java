package eu.nicosworld.rithmo.engine.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Utility class to generate combinations (subsets) of a specific size from a source list. This
 * class is designed to be fully immutable and thread-safe.
 */
public final class SubsetGenerator {

  // Suppress default constructor for non-instantiability
  private SubsetGenerator() {
    throw new AssertionError("No SubsetGenerator instances for you!");
  }

  /**
   * Generates all subsets of size 3 and 4 from the provided source list.
   *
   * @param <T> the type of elements in the list
   * @param src the source list from which combinations are generated
   * @return a completely immutable list containing the generated subsets as immutable sets
   * @throws NullPointerException if src is null
   */
  public static <T> List<Set<T>> generateCombinations(List<T> src) {
    if (src == null) {
      throw new NullPointerException("Source list cannot be null");
    }
    return generateCombinations(src, 0, List.of());
  }

  private static <T> List<Set<T>> generateCombinations(List<T> src, int index, List<T> current) {
    // Base case: if we reach size 4, we stop diving deeper in this branch
    if (current.size() == 4) {
      return List.of(Set.copyOf(current));
    }

    // Initialize a list to gather results from the current level and sub-branches
    List<Set<T>> results = new ArrayList<>();

    // If the subset reaches size 3, it's a valid combination, we collect it
    if (current.size() == 3) {
      results.add(Set.copyOf(current));
    }

    // Explore subsequent elements by creating brand-new immutable lists (no backtracking needed)
    for (int i = index; i < src.size(); i++) {
      // Create a new immutable list combining 'current' and the new element
      List<T> nextCurrent = Stream.concat(current.stream(), Stream.of(src.get(i))).toList();

      // Recursive call returns a new list, we append it to our local results
      results.addAll(generateCombinations(src, i + 1, nextCurrent));
    }

    // Return a completely immutable snapshot of the results gathered
    return List.copyOf(results);
  }
}
