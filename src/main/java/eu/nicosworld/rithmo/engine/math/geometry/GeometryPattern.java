package eu.nicosworld.rithmo.engine.math.geometry;

import eu.nicosworld.rithmo.engine.model.Position;
import java.util.Set;

public sealed interface GeometryPattern permits LinePattern, SquarePattern {
  boolean matches(Set<Position> positions);
}
