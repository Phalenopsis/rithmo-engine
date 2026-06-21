package eu.nicosworld.rithmo.engine.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import eu.nicosworld.rithmo.engine.exception.NotEmptyPositionException;
import eu.nicosworld.rithmo.engine.setup.PreDefinedState;
import eu.nicosworld.rithmo.engine.testutils.PieceAtPositionHelper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BoardTest {

  @Nested
  class GenericTest {
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
    void isInside_coordinatesAreNegative_returnFalse() {
      Board board = new Board(8, 16);

      assertFalse(board.isInside(new Position(-1, 5)));
      assertFalse(board.isInside(new Position(5, -1)));
      assertFalse(board.isInside(new Position(-1, -1)));
    }
  }

  @Nested
  class PieceGestion {
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

    @Nested
    class AddTest {
      @Test
      void addPiece_verifyImmutability() {
        Board board = new Board();
        Piece piece = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 4);
        Position position = new Position(0, 0);
        Board board2 = board.addPiece(piece, position);

        assertNotSame(board, board2);
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
    }

    @Nested
    class RemoveTest {
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
    }

    @Nested
    class MoveTest {
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
    }

    @Nested
    class GetPieceTest {
      @Test
      void getPyramids_whenBoardIsEmpty_returnEmptyOptionals() {
        Board board = new Board();

        assertTrue(board.getBlackPyramid().isEmpty());
        assertTrue(board.getWhitePyramid().isEmpty());
      }

      @Test
      void getPiecesForPlayer_shouldReturnOnlyPlayersPieces() {
        Board board = new Board();
        SimplePiece blackCircle = new SimplePiece(PieceType.CIRCLE, Player.BLACK, 4);
        SimplePiece whiteSquare = new SimplePiece(PieceType.SQUARE, Player.WHITE, 6);

        Board board2 =
            board
                .addPiece(blackCircle, new Position(0, 0))
                .addPiece(whiteSquare, new Position(1, 1));

        List<PieceAtPosition> blackPieces = board2.getPiecesForPlayer(Player.BLACK);
        List<PieceAtPosition> whitePieces = board2.getPiecesForPlayer(Player.WHITE);

        assertThat(blackPieces).hasSize(1);
        assertThat(blackPieces.getFirst().piece()).isEqualTo(blackCircle);

        assertThat(whitePieces).hasSize(1);
        assertThat(whitePieces.getFirst().piece()).isEqualTo(whiteSquare);
      }
    }
  }

  @Nested
  class BoardHomeTest {

    @Nested
    class LimitTest {
      @Test
      void test_GetBlackHome_classicBoard() {
        Board board = new Board();
        BoardHome blackHome = board.getBlackHome();
        assertThat(blackHome.player()).isEqualTo(Player.BLACK);
        assertThat(blackHome.minLimit()).isEqualTo(0);
        assertThat(blackHome.maxLimit()).isEqualTo(3);
      }

      @Test
      void test_GetWhiteHome_classicBoard() {
        Board board = new Board();
        BoardHome whiteHome = board.getWhiteHome();
        assertThat(whiteHome.player()).isEqualTo(Player.WHITE);
        assertThat(whiteHome.minLimit()).isEqualTo(12);
        assertThat(whiteHome.maxLimit()).isEqualTo(15);
      }

      @Test
      void test_GetBlackHome_4per4Board() {
        Board board = new Board(4, 4);
        BoardHome blackHome = board.getBlackHome();
        assertThat(blackHome.player()).isEqualTo(Player.BLACK);
        assertThat(blackHome.minLimit()).isEqualTo(0);
        assertThat(blackHome.maxLimit()).isEqualTo(0);
      }

      @Test
      void test_GetWhiteHome_4per4Board() {
        Board board = new Board(4, 4);
        BoardHome whiteHome = board.getWhiteHome();
        assertThat(whiteHome.player()).isEqualTo(Player.WHITE);
        assertThat(whiteHome.minLimit()).isEqualTo(3);
        assertThat(whiteHome.maxLimit()).isEqualTo(3);
      }

      @Test
      void test_GetBlackHome_8per4Board() {
        Board board = new Board(8, 4);
        BoardHome blackHome = board.getBlackHome();
        assertThat(blackHome.player()).isEqualTo(Player.BLACK);
        assertThat(blackHome.minLimit()).isEqualTo(0);
        assertThat(blackHome.maxLimit()).isEqualTo(1);
      }

      @Test
      void test_GetWhiteHome_4per8Board() {
        Board board = new Board(8, 4);
        BoardHome whiteHome = board.getWhiteHome();
        assertThat(whiteHome.player()).isEqualTo(Player.WHITE);
        assertThat(whiteHome.minLimit()).isEqualTo(6);
        assertThat(whiteHome.maxLimit()).isEqualTo(7);
      }
    }

    @Nested
    class PieceInEnemyHome {
      @Nested
      class NoPiece {
        @Test
        void test_getPieceInEnemyHome_WhitePlayer_NoPiece() {
          GameState state = PreDefinedState.predefinedVerySimpleGame(Player.WHITE);
          assertThat(state.board().getPieceInEnemyHome(Player.WHITE)).isEmpty();
        }

        @Test
        void test_getPieceInEnemyHome_BlackPlayer_NoPiece() {
          GameState state = PreDefinedState.predefinedVerySimpleGame(Player.WHITE);
          assertThat(state.board().getPieceInEnemyHome(Player.BLACK)).isEmpty();
        }
      }

      @Nested
      class MediumBoard {
        @Test
        void test_getPieceInEnemyHome_WhitePlayer_3Pieces() {
          GameState state =
              PreDefinedState.progressionVictoryState_mediumBoard_arithmetic(Player.WHITE);
          PieceAtPosition expected1 =
              PieceAtPositionHelper.create(PieceType.CIRCLE, Player.WHITE, 16, "(0,1)");
          PieceAtPosition expected2 =
              PieceAtPositionHelper.create(PieceType.CIRCLE, Player.WHITE, 36, "(0,3)");
          PieceAtPosition expected3 =
              PieceAtPositionHelper.create(PieceType.TRIANGLE, Player.WHITE, 56, "(0,0)");

          assertThat(state.board().getPieceInEnemyHome(Player.WHITE))
              .hasSize(3)
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected1))
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected2))
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected3));
        }

        @Test
        void test_getPieceInEnemyHome_BlackPlayer_3Pieces() {
          GameState state =
              PreDefinedState.progressionVictoryState_mediumBoard_arithmetic(Player.BLACK);
          PieceAtPosition expected1 =
              PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 16, "(7,1)");
          PieceAtPosition expected2 =
              PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 36, "(7,3)");
          PieceAtPosition expected3 =
              PieceAtPositionHelper.create(PieceType.TRIANGLE, Player.BLACK, 56, "(7,0)");

          assertThat(state.board().getPieceInEnemyHome(Player.BLACK))
              .hasSize(3)
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected1))
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected2))
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected3));
        }
      }

      @Nested
      class ClassicBoard {
        @Test
        void test_getPieceInEnemyHome_BlackPlayer_4Pieces() {
          GameState state =
              PreDefinedState.progressionVictoryState_classicBoard_excellent(Player.BLACK);
          PieceAtPosition expected1 =
              PieceAtPositionHelper.create(PieceType.TRIANGLE, Player.BLACK, 12, "(15,0)");
          PieceAtPosition expected2 =
              PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 4, "(12,0)");
          PieceAtPosition expected3 =
              PieceAtPositionHelper.create(PieceType.CIRCLE, Player.BLACK, 9, "(12,3)");
          PieceAtPosition expected4 =
              PieceAtPositionHelper.create(PieceType.SQUARE, Player.BLACK, 6, "(15,3)");

          assertThat(state.board().getPieceInEnemyHome(Player.BLACK))
              .hasSize(4)
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected1))
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected2))
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected3))
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected4));
        }

        @Test
        void test_getPieceInEnemyHome_WhitePlayer_4Pieces() {
          GameState state =
              PreDefinedState.progressionVictoryState_classicBoard_excellent(Player.WHITE);
          PieceAtPosition expected1 =
              PieceAtPositionHelper.create(PieceType.TRIANGLE, Player.WHITE, 12, "(0,0)");
          PieceAtPosition expected2 =
              PieceAtPositionHelper.create(PieceType.CIRCLE, Player.WHITE, 4, "(3,0)");
          PieceAtPosition expected3 =
              PieceAtPositionHelper.create(PieceType.CIRCLE, Player.WHITE, 9, "(3,3)");
          PieceAtPosition expected4 =
              PieceAtPositionHelper.create(PieceType.SQUARE, Player.WHITE, 6, "(0,3)");

          assertThat(state.board().getPieceInEnemyHome(Player.WHITE))
              .hasSize(4)
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected1))
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected2))
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected3))
              .anyMatch(PieceAtPositionHelper.samePieceAtPosition(expected4));
        }
      }
    }
  }
}
