package eu.nicosworld.rithmo.engine.capture.imprisonment;

import eu.nicosworld.rithmo.engine.capture.justification.ImprisonmentJustification;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import java.util.List;
import java.util.stream.Stream;

public record Imprisonment(
    PieceAtPosition target,
    List<PieceAtPosition> enemyBlockers,
    List<PieceAtPosition> allyBlockers,
    List<Position> regularMovesTo) {
  public List<PieceAtPosition> allBlockers() {
    return Stream.concat(allyBlockers.stream(), enemyBlockers.stream()).toList();
  }

  public ImprisonmentJustification toJustification() {
    return new ImprisonmentJustification(
        regularMovesTo(), allBlockers().stream().map(PieceAtPosition::position).toList());
  }
}
