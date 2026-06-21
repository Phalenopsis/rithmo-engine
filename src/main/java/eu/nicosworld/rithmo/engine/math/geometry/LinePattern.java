package eu.nicosworld.rithmo.engine.math.geometry;

import eu.nicosworld.rithmo.engine.model.Position;
import java.util.List;
import java.util.Set;

public class LinePattern {

  /**
   * Detect if 3 or 4 positions are in line
   *
   * @param positions Set of positions
   * @return true if there are 3 or 4 positions in line. False in other case.
   */
  public boolean matches(Set<Position> positions) {
    // Strict check on the size of the set (must be exactly 3 or 4)
    if (positions == null || positions.size() < 3 || positions.size() > 4) {
      return false;
    }

    return isLineHorizontal(positions) || isLineVertical(positions) || isLineDiagonal(positions);
  }

  private boolean isLineHorizontal(Set<Position> positions) {
    Position first = positions.iterator().next();
    return positions.stream().allMatch(p -> p.getY() == first.getY());
  }

  private boolean isLineVertical(Set<Position> positions) {
    Position first = positions.iterator().next();
    // Fixed: checking X instead of Y
    return positions.stream().allMatch(p -> p.getX() == first.getX());
  }

  private boolean isLineDiagonal(Set<Position> positions) {
    List<Position> positionList = positions.stream().toList();
    Position pos1 = positionList.get(0);
    Position pos2 = positionList.get(1);

    int deltaX = pos2.getX() - pos1.getX();
    int deltaY = pos2.getY() - pos1.getY();

    // If the first two points are horizontally or vertically aligned,
    // it's not a strict diagonal line.
    if (deltaX == 0 || deltaY == 0) {
      return false;
    }

    // Cartesian equation coefficients: ax + by + c = 0
    // For a line passing through (x1, y1) and (x2, y2):
    // a = y2 - y1
    // b = -(x2 - x1)
    // c = x2*y1 - x1*y2
    int a = deltaY;
    int b = -deltaX;
    int c = pos2.getX() * pos1.getY() - pos1.getX() * pos2.getY();

    // Check if ALL positions in the set satisfy the line equation
    return positions.stream().allMatch(p -> (a * p.getX() + b * p.getY() + c) == 0);
  }
}
