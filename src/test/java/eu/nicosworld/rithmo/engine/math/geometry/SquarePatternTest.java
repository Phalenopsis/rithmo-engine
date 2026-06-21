package eu.nicosworld.rithmo.engine.math.geometry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.setup.PreDefinedState;
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

class SquarePatternTest {
  SquarePattern detector = new SquarePattern();

  @Nested
  class UnitaryTest {
    @ParameterizedTest
    @MethodSource("squareData")
    void test_Square_Detector(String stringPositions, boolean expected) {
      Set<Position> positions = StringRepresentationHelper.parsePositions(stringPositions);

      assertThat(detector.matches(positions)).isEqualTo(expected);
    }

    public static Stream<Arguments> squareData() {
      return Stream.of(
          // --- Basic invalid cases ---
          Arguments.of("(0,0), (0,1)", false),
          Arguments.of("", false),
          Arguments.of("(0,0), (1,0), (2,6)", false),
          Arguments.of("(0,0), (1,0), (2,0)", false),
          Arguments.of("(0,1), (1,1), (2,1), (5,1)", false),
          Arguments.of("(0,1), (7,10), (4,1), (9,1)", false),
          Arguments.of("(0,1), (1,2), (2,3), (3,4)", false),
          Arguments.of("(10,10), (6,6), (7,7), (1,1)", false),
          Arguments.of("(10,10), (6,6), (7,7), (1,1), (0,0)", false), // 5 points
          Arguments.of("(5,1), (5,2), (5,3)", false),
          Arguments.of("(2,0), (2,1), (2,4), (2,10)", false),
          Arguments.of("(4,4)", false),
          Arguments.of("(1,1), (1,1), (1,1)", false), // Duplicate points
          Arguments.of("(3,3), (1,1), (2,2)", false), // Unordered line

          // --- Valid Squares ---
          Arguments.of("(0,0), (0,2), (2,0), (2,2)", true), // Standard 2x2 square
          Arguments.of("(0,0), (1,0), (1,1), (0,1)", true), // Standard 1x1 square
          Arguments.of("(0,2), (2,0), (4,2), (2,4)", true), // Rotated square (diamond shape)
          Arguments.of("(1,2), (2,1), (3,2), (2,3)", true), // Small rotated square

          // --- Geometric False Positives (Crucial edge cases) ---
          Arguments.of("(0,0), (4,0), (4,2), (2,0)", false), // Random 4 points
          Arguments.of(
              "(0,0), (3,0), (3,2), (0,2)", false), // Rectangle (Equal angles, unequal sides)
          Arguments.of(
              "(1,0), (3,0), (4,2), (2,2)",
              false), // Rhombus / Losange (Equal sides, unequal angles)
          Arguments.of(
              "(0,0), (4,0), (5,2), (1,2)",
              false), // Parallelogram (Opposite sides equal, unequal angles)

          // Rhombus / Losange (Nearly equal sides and small diagonal, but NOT a square)
          Arguments.of("(0,15), (26,0), (26,30), (52,15)", false));
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

      assertTrue(detector.matches(positions));
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

      assertFalse(detector.matches(positions));
    }
  }
}
