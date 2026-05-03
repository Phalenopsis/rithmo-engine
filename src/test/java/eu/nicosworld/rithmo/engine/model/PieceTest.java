package eu.nicosworld.rithmo.engine.model;

import eu.nicosworld.rithmo.engine.setup.PyramidBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceTest {

    @Test
    void isPyramid_isNot() {
        Piece piece = new SimplePiece(PieceType.TRIANGLE, Player.BLACK, 15);

        boolean isPyramid = piece.isPyramid();

        assertFalse(isPyramid);
    }

    @Test
    void isPyramid_is() {
        Piece piece = PyramidBuilder.fullBlack().build();

        boolean isPyramid = piece.isPyramid();

        assertTrue(isPyramid);
    }
}