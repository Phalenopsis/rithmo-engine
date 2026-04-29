package eu.nicosworld.rithmo.engine.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
}