package eu.nicosworld.rithmo.engine.testutils;

import static eu.nicosworld.rithmo.engine.testutils.PieceAtPositionHelper.samePieceAtPosition;

import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import java.util.Set;

public class CompareHelper {
  /**
   * Asserts that two sets of PieceAtPosition contain the structurally identical pieces at the same
   * positions. * @param actualSet the set produced by the engine
   *
   * @param expectedSet the set of expected pieces
   * @return true if both sets match structurally, false otherwise
   */
  public static boolean comparePieceAtPositionSets(
      Set<PieceAtPosition> actualSet, Set<PieceAtPosition> expectedSet) {
    if (actualSet == null || expectedSet == null) {
      return actualSet == expectedSet;
    }

    if (actualSet.size() != expectedSet.size()) {
      return false;
    }

    return expectedSet.stream()
        .allMatch(expected -> actualSet.stream().anyMatch(samePieceAtPosition(expected)));
  }
}
