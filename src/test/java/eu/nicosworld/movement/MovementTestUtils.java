package eu.nicosworld.movement;

import eu.nicosworld.model.Board;
import eu.nicosworld.model.Piece;
import eu.nicosworld.model.PieceAtPosition;
import eu.nicosworld.model.Position;
import eu.nicosworld.move.Move;
import eu.nicosworld.move.MoveNature;

class MovementTestUtils {

    static Board emptyBoard() {
        return new Board();
    }

    static PieceAtPosition pap(Piece piece, int x, int y) {
        return new PieceAtPosition(piece, new Position(x, y));
    }

    static Move move(int fx, int fy, int tx, int ty, MoveNature nature) {
        return new Move(
                new Position(fx, fy),
                new Position(tx, ty),
                nature
        );
    }
}
