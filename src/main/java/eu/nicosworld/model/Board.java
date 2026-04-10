package eu.nicosworld.model;

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

    public Piece get(Position pos) {
        return grid[pos.getX()][pos.getY()];
    }

    public void set(Position pos, Piece piece) {
        grid[pos.getX()][pos.getY()] = piece;
    }

    List<PieceAtPosition> getPiecesWithPositions() {
        List<PieceAtPosition> result = new ArrayList<>();

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 8; y++) {

                Piece p = grid[x][y];

                if (p != null) {
                    result.add(new PieceAtPosition(p, new Position(x, y)));
                }
            }
        }

        return result;
    }

    List<Piece> getAllPieces() {
        return new ArrayList<>();
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
}
