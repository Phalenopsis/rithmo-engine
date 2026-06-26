package eu.nicosworld.rithmo.engine.math.geometry;

import eu.nicosworld.rithmo.engine.model.Position;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class SquarePattern implements GeometryPattern {

  /**
   * Detects if 4 positions form a square.
   *
   * @param positions Set of positions
   * @return true if there are exactly 4 positions forming a square. False otherwise.
   */
  public boolean matches(Set<Position> positions) {
    if (positions == null || positions.size() != 4) {
      return false;
    }

    List<Integer> distances = getAllDistances(positions);

    // A square must have 6 distance relations between its 4 points
    if (distances.size() != 6) {
      return false;
    }

    // Sort distances in ascending order
    Collections.sort(distances);

    int side1 = distances.get(0);
    int side2 = distances.get(1);
    int side3 = distances.get(2);
    int side4 = distances.get(3);

    int diagonale1 = distances.get(4);
    int diagonale2 = distances.get(5);

    // 1. Base check: points cannot be on top of each other (side > 0)
    // 2. The 4 sides must be equal
    // 3. The 2 diagonals must be equal
    // 4. Pythagorean theorem: diagonal^2 = 2 * side^2
    return side1 > 0
        && side1 == side2
        && side2 == side3
        && side3 == side4
        && diagonale1 == diagonale2
        && diagonale1 == 2 * side1;
  }

  private List<Integer> getAllDistances(Set<Position> positions) {
    List<Position> list = new ArrayList<>(positions);
    List<Integer> distances = new ArrayList<>();

    // Avoid duplicate pairs (e.g., measuring dist(A,B) and dist(B,A))
    // by starting the inner loop at i + 1
    for (int i = 0; i < list.size(); i++) {
      for (int j = i + 1; j < list.size(); j++) {
        distances.add(getDist(list.get(i), list.get(j)));
      }
    }
    return distances;
  }

  private int getDist(Position a, Position b) {
    return (a.getX() - b.getX()) * (a.getX() - b.getX())
        + (a.getY() - b.getY()) * (a.getY() - b.getY());
  }
}
