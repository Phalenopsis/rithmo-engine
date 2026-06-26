package eu.nicosworld.rithmo.engine.math.geometry;

import eu.nicosworld.rithmo.engine.model.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class VictoryPattern {
  private final List<GeometryPattern> patterns = new ArrayList<>();

  public VictoryPattern(List<GeometryPattern> patterns) {
    this.patterns.addAll(patterns);
  }

  public boolean matches(Set<Position> positions) {
    return patterns.stream().anyMatch(p -> p.matches(positions));
  }
}
