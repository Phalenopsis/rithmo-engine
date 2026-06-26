package eu.nicosworld.rithmo.engine.model.victory;

import eu.nicosworld.rithmo.engine.capture.justification.ProgressionJustification;
import eu.nicosworld.rithmo.engine.math.progression.ProgressionResult;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Player;
import java.util.Set;

public record ProperVictory(
    Player winner, Set<PieceAtPosition> pieces, ProgressionJustification justification)
    implements Victory {
  public static ProperVictory from(Set<PieceAtPosition> pieces, ProgressionResult result) {
    Player winner = pieces.stream().findFirst().orElseThrow().piece().getPlayer();
    return new ProperVictory(winner, pieces, ProgressionJustification.from(result));
  }
}
