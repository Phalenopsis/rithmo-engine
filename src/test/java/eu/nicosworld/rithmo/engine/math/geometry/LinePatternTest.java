package eu.nicosworld.rithmo.engine.math.geometry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.testutils.PreDefinedState;
import eu.nicosworld.rithmo.engine.testutils.StringRepresentationHelper;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LinePatternTest {
  LinePattern detector = new LinePattern();

  @Nested
  class UnitaryTest {
    @ParameterizedTest
    @MethodSource("lineData")
    void test_Line_Detector(String stringPositions, boolean expected) {
      Set<Position> positions = StringRepresentationHelper.parsePositions(stringPositions);

      assertThat(detector.matches(positions)).isEqualTo(expected);
    }

    public static Stream<Arguments> lineData() {
      return Stream.of(
          // --- Existing cases ---
          Arguments.of("(0,0), (0,1)", false),
          Arguments.of("", false),
          Arguments.of("(0,0), (1,0), (2,6)", false),
          Arguments.of("(0,0), (1,0), (2,0)", true), // Horizontal 3 points
          Arguments.of("(0,1), (1,1), (2,1), (5,1)", true), // Horizontal 4 points
          Arguments.of("(0,1), (7,10), (4,1), (9,1)", false),
          Arguments.of("(0,1), (1,2), (2,3), (3,4)", true), // Diagonal 4 points
          Arguments.of("(1,0), (2,1), (3,2), (5,4)", true),
          Arguments.of("(0,1), (1,3), (2,5), (3,7)", true),
          Arguments.of("(7,1), (6,2), (5,3), (4,4)", true),
          Arguments.of("(10,10), (6,6), (7,7), (1,1)", true), // Negative diagonal
          Arguments.of("(10,10), (6,6), (7,7), (1,2)", false),
          Arguments.of(
              "(10,10), (6,6), (7,7), (1,1), (0,0)", false), // 5 points (Strictly 3 or 4 expected)

          // --- New Edge Cases & Additions ---
          // Vertical lines (X is constant, tests division by zero / undefined slope)
          Arguments.of("(5,1), (5,2), (5,3)", true),
          Arguments.of("(2,0), (2,1), (2,4), (2,10)", true),

          // Count limits (Total elements in Set)
          Arguments.of("(4,4)", false), // Single point
          Arguments.of(
              "(0,0), (0,2), (2,0), (2,2)", false), // 4 points forming a square (not a line)
          Arguments.of(
              "(1,1), (1,1), (1,1)", false), // Duplicate points resulting in a single Set element

          // Unordered points
          Arguments.of("(3,3), (1,1), (2,2)", true) // Out of order diagonal
          );
    }
  }

  @Nested
  class IntegrationTest {
    @Test
    void testDetect_WithBoard_isSquare() {
      GameState state =
          PreDefinedState.progressionVictoryState_classicBoard_excellent(Player.BLACK);
      List<PieceAtPosition> papList = state.board().getPieceInEnemyHome(Player.BLACK);
      Set<Position> positions =
          papList.stream().map(PieceAtPosition::position).collect(Collectors.toSet());

      assertFalse(detector.matches(positions));
    }

    @Test
    void testDetect_WithBoard_NotASquare() {
      GameState state = PreDefinedState.predefinedVerySimpleGame(Player.BLACK);
      List<PieceAtPosition> papList = state.board().getPieceInEnemyHome(Player.BLACK);
      Set<Position> positions =
          papList.stream().map(PieceAtPosition::position).collect(Collectors.toSet());

      assertFalse(detector.matches(positions));
    }

    @Test
    void testDetect_WithBoard_isLine() {
      GameState state =
          PreDefinedState.progressionVictoryState_mediumBoard_arithmetic(Player.BLACK);
      List<PieceAtPosition> papList = state.board().getPieceInEnemyHome(Player.BLACK);
      Set<Position> positions =
          papList.stream().map(PieceAtPosition::position).collect(Collectors.toSet());

      assertTrue(detector.matches(positions));
    }
  }
}
