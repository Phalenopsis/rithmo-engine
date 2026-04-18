package eu.nicosworld.rithmoEngine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Board {
    private final Piece[][] grid;

    public Board(int columns, int lines) {
        grid = new Piece[columns][lines];
    }

    public Board() {
        this(16, 8);
    }

    public boolean isEmpty(Position p) {
        return Objects.isNull(grid[p.getX()][p.getY()]);
    }

    public Piece getPieceAt(Position pos) {
        return getPieceAt(pos.getX(),pos.getY());
    }

    public Piece getPieceAt(int x, int y) {
        return grid[x][y];
    }

    private void set(Position pos, Piece piece) {
        grid[pos.getX()][pos.getY()] = piece;
    }

    public List<PieceAtPosition> getPiecesWithPositions() {
        List<PieceAtPosition> result = new ArrayList<>();
        int width = grid.length;

        for (int x = 0; x < width; x++) {
            int height = grid[x].length;

            for (int y = 0; y < height; y++) {

                Piece p = grid[x][y];

                if (p != null) {
                    result.add(new PieceAtPosition(p, new Position(x, y)));
                }
            }
        }

        return result;
    }

    public List<PieceAtPosition> getPiecesForPlayer(Player player) {
        return getPiecesWithPositions().stream()
                .filter(pap -> pap.piece().getPlayer().equals(player))
                .toList();
    }

    List<Piece> getAllPieces() {
        return getPiecesWithPositions()
                .stream()
                .map(PieceAtPosition::piece)
                .toList();
    }

    public Position findPosition(Piece p) {
        // On parcourt les colonnes (le premier index)
        for (int col = 0; col < grid.length; col++) {
            // On parcourt les lignes (le second index)
            for (int line = 0; line < grid[col].length; line++) {
                // On compare l'objet dans la grille avec la pièce recherchée
                if (grid[col][line] != null && grid[col][line].equals(p)) {
                    return new Position(col, line);
                }
            }
        }
        // Si on arrive ici, la pièce n'a pas été trouvée
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

        return newBoard;
    }

    public Board move(Position from, Position to) {
        Board board = this.copy();
        Piece piece = board.getPieceAt(from);
        board.set(from, null);
        board.set(to, piece);
        return board;
    }

    public Board addPiece(Piece piece, Position position) {
        Board board = this.copy();
        board.set(position, piece);
        return board;
    }

    public Board addPiece(Position position, Piece piece) {
        return this.addPiece(piece, position);
    }

    public Board removePiece(Position position) {
        Board board = this.copy();
        board.set(position, null);
        return board;
    }
}
