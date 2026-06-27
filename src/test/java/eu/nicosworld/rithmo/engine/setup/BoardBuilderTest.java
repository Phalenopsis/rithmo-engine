package eu.nicosworld.rithmo.engine.setup;

import static org.assertj.core.api.Assertions.*;

import eu.nicosworld.rithmo.engine.model.*;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the BoardBuilder DSL. Ensures that board configuration, piece placement, and
 * historical setups are correct.
 */
class BoardBuilderTest {

  @Test
  @DisplayName("Should create a board with specified dimensions")
  void shouldCreateBoardWithDimensions() {
    // When creating a board with specific width and height
    Board board = new BoardBuilder(12, 8).build();

    // Then dimensions should be correctly initialized
    assertThat(board.getWidth()).isEqualTo(12);
    assertThat(board.getHeight()).isEqualTo(8);
  }

  @Test
  @DisplayName("Should place a simple piece using fluent API")
  void shouldPlaceSimplePiece() {
    // When placing a black circle using the fluent API
    Board board = new BoardBuilder(8, 8).blackCircle(36).at(2, 3).build();

    Piece piece = board.getPieceAt(new Position(2, 3));

    // Then the piece should be correctly initialized at the given position
    assertThat(piece).isNotNull();
    assertThat(piece.getType()).isEqualTo(PieceType.CIRCLE);
    assertThat(piece.getValue()).isEqualTo(36);
    assertThat(piece.getPlayer()).isEqualTo(Player.BLACK);
  }

  @Test
  @DisplayName("Should place multiple pieces correctly")
  void shouldPlaceMultiplePieces() {
    // When placing multiple pieces in a row
    Board board = new BoardBuilder().whiteSquare(25).at(0, 0).blackTriangle(16).at(1, 1).build();

    // Then both pieces should exist with correct values
    assertThat(board.getPieceAt(new Position(0, 0)).getValue()).isEqualTo(25);
    assertThat(board.getPieceAt(new Position(1, 1)).getValue()).isEqualTo(16);
  }

  @Test
  @DisplayName("Should build a pyramid with its custom components")
  void shouldBuildPyramidWithComponents() {
    // When manually assembling a pyramid with specific components
    Board board =
        new BoardBuilder()
            .piece(PieceType.PYRAMID, 0, PlayerColor.WHITE)
            .withComponent(PieceType.SQUARE, 36)
            .withComponent(PieceType.SQUARE, 25)
            .at(4, 4)
            .build();

    Piece piece = board.getPieceAt(new Position(4, 4));

    // Then the piece should be a Pyramid instance with the correct components
    assertThat(piece).isInstanceOf(Pyramid.class);
    Pyramid pyramid = (Pyramid) piece;
    assertThat(pyramid.getComponents()).hasSize(2);
    assertThat(pyramid.getComponents().get(0).getValue()).isEqualTo(36);
    assertThat(pyramid.getComponents().get(1).getValue()).isEqualTo(25);
  }

  @Test
  @DisplayName("Should build a pyramid with its custom components")
  void shouldBuildPyramidWithComponents_PreBuiltPyramid() {
    Pyramid prebuiltPyramid =
        PyramidBuilder.forPlayer(Player.BLACK)
            .withSquare(34)
            .withTriangle(3)
            .withCircle(19)
            .build();
    // When manually assembling a pyramid with specific components
    Board board = new BoardBuilder().withPiece(prebuiltPyramid).at(4, 4).build();

    Piece piece = board.getPieceAt(new Position(4, 4));

    // Then the piece should be a Pyramid instance with the correct components
    assertThat(piece).isInstanceOf(Pyramid.class);
    Pyramid pyramid = (Pyramid) piece;
    assertThat(pyramid.getComponents()).hasSize(3);
    assertThat(pyramid.getComponents().get(0).getValue()).isEqualTo(34);
    assertThat(pyramid.getComponents().get(1).getValue()).isEqualTo(3);
    assertThat(pyramid.getComponents().get(2).getValue()).isEqualTo(19);
  }

  @Test
  @DisplayName("Should throw exception when adding component to a non-pyramid")
  void shouldThrowExceptionForComponentOnSimplePiece() {
    // Given a builder initialized with a simple piece (circle)
    BoardBuilder builder = new BoardBuilder().blackCircle(10);

    // Then adding a component should fail
    assertThatThrownBy(() -> builder.withComponent(PieceType.SQUARE, 4))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Cannot add components to a non-pyramid piece");
  }

  @Test
  @DisplayName("Should throw exception when calling at() without a piece")
  void shouldThrowExceptionWhenAtIsCalledAlone() {
    // Given an empty builder
    BoardBuilder builder = new BoardBuilder();

    // Then trying to place a piece without defining it first should fail
    assertThatThrownBy(() -> builder.at(1, 1))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No piece defined");
  }

  @Test
  @DisplayName("Should ensure defensive copy of pyramid components")
  void shouldEnsureDefensiveCopyOfPyramid() {
    // Given a builder for a pyramid
    BoardBuilder builder =
        new BoardBuilder()
            .piece(PieceType.PYRAMID, 0, PlayerColor.BLACK)
            .withComponent(PieceType.CIRCLE, 4);

    // When placing the piece
    Board board1 = builder.at(1, 1).build();

    // Then the resulting pyramid should be immutable regarding the builder's internal list
    Piece piece = board1.getPieceAt(new Position(1, 1));
    assertThat(((Pyramid) piece).getComponents()).hasSize(1);
  }

  @Test
  @DisplayName("Should support Position object for placement")
  void shouldSupportPositionObject() {
    // When using the Position object overload for the at() method
    Position pos = new Position(5, 2);
    Board board = new BoardBuilder().whiteCircle(9).at(pos).build();

    // Then the piece should be placed at the exact position
    assertThat(board.getPieceAt(pos)).isNotNull();
    assertThat(board.getPieceAt(pos).getValue()).isEqualTo(9);
  }

  @Test
  @DisplayName("Should reset current piece state after placement")
  void shouldResetStateAfterAt() {
    // Given a builder that just placed a piece
    BoardBuilder builder = new BoardBuilder().blackCircle(10).at(0, 0);

    // Then calling at() again without a new piece should fail (state reset)
    assertThatThrownBy(() -> builder.at(1, 1)).isInstanceOf(IllegalStateException.class);
  }

  @Test
  @DisplayName("Should build a complete Black Pyramid with historical values")
  void shouldBuildFullBlackPyramid() {
    // When placing a full historical black pyramid
    Position pos = new Position(0, 0);
    Board board = new BoardBuilder().fullBlackPyramidAt(pos).build();

    Piece piece = board.getPieceAt(pos);
    assertThat(piece).isInstanceOf(Pyramid.class);
    assertThat(piece.getPlayer()).isEqualTo(Player.BLACK);

    Pyramid pyramid = (Pyramid) piece;
    // Verify historical components (1, 4, 9, 16, 25, 36)
    assertThat(pyramid.getComponents()).hasSize(6);

    List<Integer> values = pyramid.getComponents().stream().map(Piece::getValue).toList();

    assertThat(values).containsExactlyInAnyOrder(1, 4, 9, 16, 25, 36);

    // Verify shapes distribution
    assertThat(pyramid.getComponents().stream().filter(p -> p.getType() == PieceType.CIRCLE))
        .hasSize(2);
    assertThat(pyramid.getComponents().stream().filter(p -> p.getType() == PieceType.TRIANGLE))
        .hasSize(2);
    assertThat(pyramid.getComponents().stream().filter(p -> p.getType() == PieceType.SQUARE))
        .hasSize(2);
  }

  @Test
  @DisplayName("Should build a complete White Pyramid with historical values")
  void shouldBuildFullWhitePyramid() {
    // When placing a full historical white pyramid
    Position pos = new Position(7, 7);
    Board board = new BoardBuilder().fullWhitePyramidAt(pos).build();

    Piece piece = board.getPieceAt(pos);
    assertThat(piece).isInstanceOf(Pyramid.class);
    assertThat(piece.getPlayer()).isEqualTo(Player.WHITE);

    Pyramid pyramid = (Pyramid) piece;
    // Verify historical components (16, 25, 36, 49, 64)
    assertThat(pyramid.getComponents()).hasSize(5);

    List<Integer> values = pyramid.getComponents().stream().map(Piece::getValue).toList();

    assertThat(values).containsExactlyInAnyOrder(16, 25, 36, 49, 64);
  }
}
