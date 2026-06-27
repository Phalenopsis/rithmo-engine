package eu.nicosworld.rithmo.engine.model;

import eu.nicosworld.rithmo.engine.exception.NotEmptyPositionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

public class Board {
  private static final int NUMBER_OF_COLUMNS = 16;
  private static final int NUMBER_OF_LINES = 8;
  private final Piece[][] grid;
  private PieceAtPosition blackPyramid;
  private PieceAtPosition whitePyramid;
  private final BoardHome blackHome;
  private final BoardHome whiteHome;

  public Board(int columns, int lines) {
    grid = new Piece[columns][lines];
    int homeLimit = columns / 4;
    blackHome = new BoardHome(Player.BLACK, 0, homeLimit - 1);
    whiteHome = new BoardHome(Player.WHITE, columns - homeLimit, columns - 1);
  }

  public Board() {
    this(NUMBER_OF_COLUMNS, NUMBER_OF_LINES);
  }

  public int getWidth() {
    return grid.length;
  }

  public int getHeight() {
    return grid[0].length;
  }

  public Optional<PieceAtPosition> getBlackPyramid() {
    return getPyramid(blackPyramid);
  }

  public Optional<PieceAtPosition> getWhitePyramid() {
    return getPyramid(whitePyramid);
  }

  public BoardHome getBlackHome() {
    return blackHome;
  }

  public BoardHome getWhiteHome() {
    return whiteHome;
  }

  private BoardHome getHome(Player player) {
    return player.equals(Player.BLACK) ? getBlackHome() : getWhiteHome();
  }

  private Optional<PieceAtPosition> getPyramid(PieceAtPosition expected) {
    return Optional.ofNullable(expected);
  }

  public boolean isEmpty(Position p) {
    return Objects.isNull(grid[p.getX()][p.getY()]);
  }

  public Piece getPieceAt(Position pos) {
    return getPieceAt(pos.getX(), pos.getY());
  }

  public Piece getPieceAt(int x, int y) {
    return grid[x][y];
  }

  public PieceAtPosition getPieceAtPosition(Position p) {
    return new PieceAtPosition(getPieceAt(p), p);
  }

  /**
   * this method should be only called by :
   *
   * <ul>
   *   <li>addPiece(Piece piece, Position position)
   *   <li>set(Position position)
   * </ul>
   *
   * @param pos position to set piece
   * @param piece piece to set
   */
  private void set(Position pos, Piece piece) {
    grid[pos.getX()][pos.getY()] = piece;
  }

  private void set(Position pos) {
    set(pos, null);
  }

  private List<PieceAtPosition> getPiecesWithPositions(Predicate<Piece> filter) {
    return getPiecesWithPositions(0, grid.length - 1, filter);
  }

  private List<PieceAtPosition> getPiecesWithPositions(
      int startColumn, int endColumn, Predicate<Piece> filter) {
    List<PieceAtPosition> result = new ArrayList<>();

    for (int x = startColumn; x <= endColumn; x++) {
      int height = grid[x].length;

      for (int y = 0; y < height; y++) {

        Piece p = grid[x][y];

        if (Objects.nonNull(p) && filter.test(p)) {
          result.add(new PieceAtPosition(p, new Position(x, y)));
        }
      }
    }

    return result;
  }

  private Predicate<Piece> pieceForPlayer(Player player) {
    return p -> p.getPlayer().equals(player);
  }

  public List<PieceAtPosition> getPiecesWithPositions() {
    return getPiecesWithPositions(0, grid.length - 1, p -> true);
  }

  public List<PieceAtPosition> getPiecesForPlayer(Player player) {
    return getPiecesWithPositions(pieceForPlayer(player));
  }

  public List<PieceAtPosition> getPieceInEnemyHome(Player activePlayer) {
    BoardHome enemyHome = getEnemyHome(activePlayer);
    return getPiecesWithPositions(
        enemyHome.minLimit(), enemyHome.maxLimit(), pieceForPlayer(activePlayer));
  }

  private BoardHome getEnemyHome(Player activePlayer) {
    return getHome(activePlayer.opponent());
  }

  List<Piece> getAllPieces() {
    return getPiecesWithPositions().stream().map(PieceAtPosition::piece).toList();
  }

  public Position findPosition(Piece p) {
    for (int col = 0; col < grid.length; col++) {
      for (int line = 0; line < grid[col].length; line++) {
        if (grid[col][line] != null && grid[col][line].equals(p)) {
          return new Position(col, line);
        }
      }
    }
    return null;
  }

  public boolean isInside(Position position) {
    int maxColumn = grid.length;
    int maxLine = grid[maxColumn - 1].length;
    return position.getX() >= 0
        && position.getY() >= 0
        && position.getX() < maxColumn
        && position.getY() < maxLine;
  }

  public Board copy() {
    Board newBoard = new Board(grid.length, grid[0].length);

    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid[x].length; y++) {
        newBoard.grid[x][y] = grid[x][y];
      }
    }
    newBoard.blackPyramid = this.blackPyramid;
    newBoard.whitePyramid = this.whitePyramid;
    return newBoard;
  }

  public Board move(Position from, Position to) {
    Piece piece = this.getPieceAt(from);
    return this.removePiece(from).addPiece(to, piece);
  }

  public Board addPiece(Position position, Piece piece) {
    return this.addPiece(piece, position);
  }

  public Board addPiece(Piece piece, Position position) {
    if (!isEmpty(position)) {
      throw new NotEmptyPositionException(getPieceAtPosition(position));
    }
    Board board = this.copy();
    board.set(position, piece);
    if (piece instanceof Pyramid pyramid) {
      board.updatePyramid(pyramid, position);
    }

    return board;
  }

  public Board removePiece(Position position) {
    Piece removed = getPieceAt(position);
    Board board = this.copy();
    board.set(position);
    if (removed instanceof Pyramid) {
      board.deletePyramid(removed.getPlayer());
    }
    return board;
  }

  private void updatePyramid(Pyramid pyramid, Position position) {
    PieceAtPosition pap = new PieceAtPosition(pyramid, position);
    if (pyramid.getPlayer().equals(Player.BLACK)) {
      blackPyramid = pap;
    } else {
      whitePyramid = pap;
    }
  }

  private void deletePyramid(Player player) {
    if (player.equals(Player.BLACK)) {
      blackPyramid = null;
    } else {
      whitePyramid = null;
    }
  }

  public Board replacePiece(Position position, Pyramid updatedPyramid) {
    Board newBoard = removePiece(position);
    return newBoard.addPiece(position, updatedPyramid);
  }

  public String prettyPrint() {
    StringBuilder sb = new StringBuilder();

    int width = grid.length;
    int height = grid[0].length;

    // header X axis
    sb.append("    ");
    for (int x = 0; x < width; x++) {
      sb.append(String.format("%-6d", x));
    }
    sb.append("\n");

    for (int y = 0; y < height; y++) {
      sb.append(String.format("%-3d", y));

      for (int x = 0; x < width; x++) {
        Position pos = new Position(x, y);
        Piece piece = getPieceAt(pos);

        if (piece == null) {
          sb.append(String.format("%-6s", "."));
        } else {
          sb.append(String.format("%-6s", formatPiece(piece)));
        }
      }

      sb.append("\n");
    }

    return sb.toString();
  }

  private String formatPiece(Piece piece) {
    String color = piece.getPlayer().getColor().name().charAt(0) + "";
    String type =
        switch (piece.getType()) {
          case CIRCLE -> "C";
          case TRIANGLE -> "T";
          case SQUARE -> "S";
          case PYRAMID -> "P";
        };

    return color + type + piece.getValue();
  }
}
