package eu.nicosworld.rithmo.engine.testutils.victory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.nicosworld.rithmo.engine.capture.justification.ProgressionJustification;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.victory.ProperVictory;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import eu.nicosworld.rithmo.engine.testutils.CompareHelper;
import java.util.List;
import java.util.Set;

public class ProperVictoryAssertion {
  private final List<ProperVictory> victories;

  private ProperVictoryAssertion(List<ProperVictory> victories) {
    this.victories = victories;
  }

  public static ProperVictoryAssertion from(List<Victory> victories) {
    List<ProperVictory> properVictories =
        victories.stream()
            .filter(v -> v instanceof ProperVictory)
            .map(v -> (ProperVictory) v)
            .toList();
    return new ProperVictoryAssertion(properVictories);
  }

  public ProperVictoryAssertion hasVictoryWithPieces(Set<PieceAtPosition> expectedPieces) {
    boolean match =
        victories.stream()
            .anyMatch(v -> CompareHelper.comparePieceAtPositionSets(v.pieces(), expectedPieces));
    assertTrue(
        match,
        "expected : "
            + expectedPieces
            + "\n  actual : "
            + victories.stream().map(ProperVictory::pieces).toList());
    return this;
  }

  public ProperVictoryAssertion hasWinner(Player player) {
    assertThat(victories.getFirst().winner()).isEqualTo(player);

    return this;
  }

  public ProperVictoryAssertion hasJustification(ProgressionJustification expectedJustification) {
    boolean match =
        victories.stream().anyMatch(v -> v.justification().equals(expectedJustification));
    assertTrue(
        match,
        "expected : "
            + expectedJustification
            + "\n  actual : "
            + victories.stream().map(ProperVictory::justification).toList());
    return this;
  }

  public void hasNoVictory() {
    assertThat(victories).isEmpty();
  }
}
