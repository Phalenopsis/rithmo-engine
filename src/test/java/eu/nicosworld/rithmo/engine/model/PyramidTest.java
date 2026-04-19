package eu.nicosworld.rithmo.engine.model;

import eu.nicosworld.rithmo.engine.testbuilder.PyramidTestCaseBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PyramidTest {

    @Test
    void getValue_fullBlackPyramid_shouldBe91() {
        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(new Player(PlayerColor.BLACK))
                .full()
                .build();

        int value = pyramid.getValue();

        assertEquals(91, value);
    }

    @Test
    void getValue_35PointsPyramid_shouldReturn35() {
        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(new Player(PlayerColor.WHITE))
                .withSquare(20)
                .withTriangle(10)
                .withCircle(5)
                .build();

        int value = pyramid.getValue();

        assertEquals(35, value);
    }

    @Test
    void hasSquare_hasSquareType_shouldReturnTrue() {
        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(new Player(PlayerColor.BLACK))
                .full()
                .build();

        boolean hasSquare = pyramid.hasSquare();

        assertTrue(hasSquare);
    }

    @Test
    void hasSquare_hasNotSquareType_shouldReturnFalse() {
        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(new Player(PlayerColor.WHITE))
                .withTriangle(10)
                .withCircle(5)
                .build();

        boolean hasSquare = pyramid.hasSquare();

        assertFalse(hasSquare);
    }

    @Test
    void removeComponent() {
        Pyramid pyramid = PyramidTestCaseBuilder
                .forPlayer(new Player(PlayerColor.WHITE))
                .withSquare(20)
                .withTriangle(10)
                .withCircle(5)
                .build();

        List<SimplePiece> pieces = pyramid.getComponents();

        SimplePiece square = pieces.stream().filter(c -> c.getType().equals(PieceType.SQUARE)).findFirst().orElseThrow();

        Pyramid reducedPyramid = pyramid.removeComponent(square);

        assertNotSame(pyramid, reducedPyramid);
        assertEquals(pyramid.getId(), reducedPyramid.getId());
        assertEquals(2, reducedPyramid.getComponents().size());
        assertEquals(15, reducedPyramid.getValue());
        assertTrue(reducedPyramid.hasCircle());
        assertTrue(reducedPyramid.hasTriangle());
        assertFalse(reducedPyramid.hasSquare());
    }
}