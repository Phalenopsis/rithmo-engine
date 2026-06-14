package eu.nicosworld.rithmo.engine.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import eu.nicosworld.rithmo.engine.exception.NotEmptyPositionException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class BoardTest {

  @Test
  void getWidth() {
    int width = 8;
    int height = 16;
    Board board = new Board(width, height);

    int result = board.getWidth();

    assertEquals(result, width);
  }

  @Test
  void getHeight() {
    int width = 8;
    int height = 16;
    Board board = new Board(width, height);

    int result = board.getHeight();

    assertEquals(result, height);
  }

  @Test
  void isInside_positionIsInside_returnTrue() {
    int width = 8;
    int height = 16;
    Board board = new Board(width, height);

    Position position = new Position(5, 13);

    assertTrue(board.isInside(position));
  }

  @Test
  void isInside_positionIsOutside_returnFalse() {
    int width = 8;
    int height = 16;
    Board board = new Board(width, height);

    Position position = new Position(5, 16);

    assertFalse(board.isInside(position));
  }

  @Test
  void addPiece_verifyImmutability() {
    Board board = new Board();
    Piece piece = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 4);
    Position position = new Position(0, 0);
    Board board2 = board.addPiece(piece, position);

    assertNotSame(board, board2);
  }

  @Test
  void removePiece_verifyImmutability() {
    Board board = new Board();
    Piece piece = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 4);
    Position position = new Position(0, 0);
    Board board2 = board.addPiece(piece, position);
    Board board3 = board2.removePiece(position);

    assertNotSame(board3, board2);
    assertNotSame(board, board3);

    assertNull(board3.getPieceAt(position));
    assertThat(board2.getPieceAt(position)).isEqualTo(piece);
  }

  @Test
  void addPiece_pyramid_VerifyGetBlackPyramid() {
    Board board = new Board();
    SimplePiece component = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 4);
    Pyramid pyramid = new Pyramid(Player.BLACK, List.of(component));
    Position position = new Position(0, 0);
    Board board2 = board.addPiece(pyramid, position);

    Optional<PieceAtPosition> optPyramid = board2.getBlackPyramid();

    assertTrue(optPyramid.isPresent());
    assertThat(optPyramid.get()).isEqualTo(new PieceAtPosition(pyramid, position));
  }

  @Test
  void removePiece_pyramid_VerifyGetBlackPyramid() {
    Board board = new Board();
    SimplePiece component = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 4);
    Pyramid pyramid = new Pyramid(Player.BLACK, List.of(component));
    Position position = new Position(0, 0);
    Board board2 = board.addPiece(pyramid, position);

    Board board3 = board2.removePiece(position);

    Optional<PieceAtPosition> optPyramid = board3.getBlackPyramid();

    assertTrue(optPyramid.isEmpty());
  }

  @Test
  void addPiece_pyramid_VerifyGetWhitePyramid() {
    Board board = new Board();
    SimplePiece component = new SimplePiece(PieceType.CIRCLE, Player.WHITE, 4);
    Pyramid pyramid = new Pyramid(Player.WHITE, List.of(component));
    Position position = new Position(0, 0);
    Board board2 = board.addPiece(pyramid, position);

    Optional<PieceAtPosition> optPyramid = board2.getWhitePyramid();

    assertTrue(optPyramid.isPresent());
    assertThat(optPyramid.get()).isEqualTo(new PieceAtPosition(pyramid, position));
  }

  @Test
  void removePiece_pyramid_VerifyGetWhitePyramid() {
    Board board = new Board();
    SimplePiece component = new SimplePiece(PieceType.CIRCLE, Player.WHITE, 4);
    Pyramid pyramid = new Pyramid(Player.WHITE, List.of(component));
    Position position = new Position(0, 0);
    Board board2 = board.addPiece(pyramid, position);

    Board board3 = board2.removePiece(position);

    Optional<PieceAtPosition> optPyramid = board3.getWhitePyramid();

    assertTrue(optPyramid.isEmpty());
  }

  @Test
  void movePiece() {
    Board board = new Board();
    SimplePiece component = new SimplePiece(PieceType.CIRCLE, Player.WHITE, 4);
    Pyramid pyramid = new Pyramid(Player.WHITE, List.of(component));
    Position position = new Position(0, 0);
    Board board2 = board.addPiece(pyramid, position);

    Position newPosition = new Position(1, 1);

    Board board3 = board2.move(position, newPosition);

    Optional<PieceAtPosition> optPyramid = board3.getWhitePyramid();

    assertNotSame(board, board2);
    assertNotSame(board2, board3);
    assertNotSame(board, board3);

    assertTrue(optPyramid.isPresent());
    assertThat(optPyramid.get()).isEqualTo(new PieceAtPosition(pyramid, newPosition));
  }

  @Test
  void move_simplePiece_shouldMoveCorrectlyWithoutAffectingCaches() {
    Board board = new Board();
    SimplePiece piece = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 4);
    Position from = new Position(2, 2);
    Position to = new Position(2, 3);

    Board board2 = board.addPiece(piece, from).move(from, to);

    assertNull(board2.getPieceAt(from));
    assertThat(board2.getPieceAt(to)).isEqualTo(piece);
    assertTrue(board2.getBlackPyramid().isEmpty());
  }

  @Test
  void isInside_coordinatesAreNegative_returnFalse() {
    Board board = new Board(8, 16);

    assertFalse(board.isInside(new Position(-1, 5)));
    assertFalse(board.isInside(new Position(5, -1)));
    assertFalse(board.isInside(new Position(-1, -1)));
  }

  @Test
  void getPyramids_whenBoardIsEmpty_returnEmptyOptionals() {
    Board board = new Board();

    assertTrue(board.getBlackPyramid().isEmpty());
    assertTrue(board.getWhitePyramid().isEmpty());
  }

  @Test
  void addPiece_overwritingAPyramidWithSimplePiece_shouldThrowException() {
    Board board = new Board();
    SimplePiece component = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 4);
    Pyramid pyramid = new Pyramid(Player.BLACK, List.of(component));
    Position position = new Position(0, 0);

    // On ajoute la pyramide, puis on pose un pion simple au même endroit
    Board boardWithPyramid = board.addPiece(pyramid, position);
    SimplePiece attacker = new SimplePiece(PieceType.SQUARE, Player.WHITE, 6);
    assertThatThrownBy(() -> boardWithPyramid.addPiece(attacker, position))
        .isInstanceOf(NotEmptyPositionException.class)
        .hasMessage("There is already PYRAMID BLACK (4) at position (0, 0).");
  }

  @Test
  void getPiecesForPlayer_shouldReturnOnlyPlayersPieces() {
    Board board = new Board();
    SimplePiece blackCircle = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 4);
    SimplePiece whiteSquare = new SimplePiece(PieceType.SQUARE, Player.WHITE, 6);

    Board board2 =
        board.addPiece(blackCircle, new Position(0, 0)).addPiece(whiteSquare, new Position(1, 1));

    List<PieceAtPosition> blackPieces = board2.getPiecesForPlayer(Player.BLACK);
    List<PieceAtPosition> whitePieces = board2.getPiecesForPlayer(Player.WHITE);

    assertThat(blackPieces).hasSize(1);
    assertThat(blackPieces.get(0).piece()).isEqualTo(blackCircle);

    assertThat(whitePieces).hasSize(1);
    assertThat(whitePieces.get(0).piece()).isEqualTo(whiteSquare);
  }

  @Test
  void replacePiece_shouldUpdatePyramidCacheWithNewInstance() {
    Board board = new Board();
    SimplePiece c4 = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 4);
    SimplePiece c6 = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 6);
    Position position = new Position(4, 4);

    Pyramid initialPyramid = new Pyramid(Player.BLACK, List.of(c4, c6));
    Board board2 = board.addPiece(initialPyramid, position);

    // On crée la pyramide modifiée (par exemple après la perte du composant c4)
    Pyramid updatedPyramid = new Pyramid(Player.BLACK, List.of(c6));
    Board board3 = board2.replacePiece(position, updatedPyramid);

    Optional<PieceAtPosition> cachedPyramid = board3.getBlackPyramid();
    assertTrue(cachedPyramid.isPresent());
    assertThat(cachedPyramid.get().piece()).isEqualTo(updatedPyramid);
    assertThat(((Pyramid) cachedPyramid.get().piece()).getComponents()).hasSize(1);
  }
}
