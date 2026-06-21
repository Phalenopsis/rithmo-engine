package eu.nicosworld.rithmo.engine.testutils;

import eu.nicosworld.rithmo.engine.model.Position;
import java.util.HashSet;
import java.util.Set;

public class StringRepresentationHelper {

  /** Parses a single position string into a Position object. Expected format: "(x,y)" */
  public static Position parsePosition(String value) {
    try {
      String cleaned = value.replace("(", "").replace(")", "").replace(" ", "").trim();
      String[] split = cleaned.split(",");
      int x = Integer.parseInt(split[0].trim());
      int y = Integer.parseInt(split[1].trim());
      return new Position(x, y);
    } catch (Exception e) {
      throw new IllegalArgumentException(
          "Invalid position format: " + value + ". Expected format: (x,y)");
    }
  }

  /**
   * Parses a string containing multiple positions separated by commas into a Set. Expected format:
   * "(1,2), (3,4), (5,6)"
   *
   * @param input The formatted string containing positions
   * @return A Set of unique Position objects
   */
  public static Set<Position> parsePositions(String input) {
    Set<Position> positions = new HashSet<>();

    if (input == null || input.trim().isEmpty()) {
      return positions; // Return an empty set if input is blank
    }

    // Split the string only at the commas that separate the coordinate pairs.
    // It matches a comma (and optional spaces) that is preceded by ')' and followed by '('.
    String[] tokens = input.split("(?<=\\))\\s*,\\s*(?=\\()");

    for (String token : tokens) {
      // Each token looks like "(1,2)", your parsePosition method will handle the rest
      Position pos = parsePosition(token);
      positions.add(pos);
    }

    return positions;
  }
}
