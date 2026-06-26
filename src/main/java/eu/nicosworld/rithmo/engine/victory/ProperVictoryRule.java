package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.math.SubsetGenerator;
import eu.nicosworld.rithmo.engine.math.geometry.LinePattern;
import eu.nicosworld.rithmo.engine.math.geometry.SquarePattern;
import eu.nicosworld.rithmo.engine.math.geometry.VictoryPattern;
import eu.nicosworld.rithmo.engine.math.progression.ProgressionEngine;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.victory.ProperVictory;
import eu.nicosworld.rithmo.engine.model.victory.Victory;
import java.util.*;
import java.util.stream.Collectors;

public final class ProperVictoryRule {
  private final VictoryPattern pattern;
  private final ProgressionEngine engine;

  public ProperVictoryRule(VictoryPattern pattern, ProgressionEngine engine) {
    this.pattern = pattern;
    this.engine = engine;
  }

  public static ProperVictoryRule fast() {
    VictoryPattern pattern = new VictoryPattern(List.of(new LinePattern(), new SquarePattern()));
    ProgressionEngine engine = ProgressionEngine.fast();
    return new ProperVictoryRule(pattern, engine);
  }

  public List<Victory> evaluate(GameState state) {
    List<PieceAtPosition> pieceInEnemyHome =
        state.board().getPieceInEnemyHome(state.currentPlayer());
    List<Set<PieceAtPosition>> subsets = getSubsets(pieceInEnemyHome);
    if (subsets.isEmpty()) return List.of();
    List<Set<PieceAtPosition>> formations = checkGeometry(subsets);
    if (subsets.isEmpty()) return List.of();

    return checkProgression(formations);
  }

  private List<Set<PieceAtPosition>> getSubsets(List<PieceAtPosition> pieceAtPositions) {
    return SubsetGenerator.generateCombinations(pieceAtPositions);
  }

  private List<Set<PieceAtPosition>> checkGeometry(List<Set<PieceAtPosition>> piecesAtPosition) {
    return piecesAtPosition.stream()
        .filter(
            s ->
                pattern.matches(
                    s.stream().map(PieceAtPosition::position).collect(Collectors.toSet())))
        .toList();
  }

  private List<Victory> checkProgression(List<Set<PieceAtPosition>> formations) {
    List<Victory> victories = new ArrayList<>();
    for (Set<PieceAtPosition> formation : formations) {
      int[] values = formation.stream().mapToInt(p -> p.piece().getValue()).toArray();
      engine.detect(values).map(r -> ProperVictory.from(formation, r)).ifPresent(victories::add);
    }
    return victories;
  }
}
