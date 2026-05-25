package eu.nicosworld.rithmo.engine.model;

import eu.nicosworld.rithmo.engine.setup.PyramidBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PyramidTest {

    @Test
    void getValue_fullBlackPyramid_shouldBe91() {
        Pyramid pyramid = PyramidBuilder
                .fullBlack()
                .build();

        int value = pyramid.getValue();

        assertEquals(91, value);
    }

    @Test
    void getValue_fullWhitePyramid_shouldBe190() {
        Pyramid pyramid = PyramidBuilder
                .fullWhite()
                .build();

        int value = pyramid.getValue();

        assertEquals(190, value);
    }

    @Test
    void getValue_35PointsPyramid_shouldReturn35() {
        Pyramid pyramid = PyramidBuilder
                .forPlayer(Player.WHITE)
                .withSquare(20)
                .withTriangle(10)
                .withCircle(5)
                .build();

        int value = pyramid.getValue();

        assertEquals(35, value);
    }

    @Test
    void hasSquare_hasSquareType_shouldReturnTrue() {
        Pyramid pyramid = PyramidBuilder
                .fullBlack()
                .build();

        boolean hasSquare = pyramid.hasSquare();

        assertTrue(hasSquare);
    }

    @Test
    void hasSquare_hasNotSquareType_shouldReturnFalse() {
        Pyramid pyramid = PyramidBuilder
                .forPlayer(Player.WHITE)
                .withTriangle(10)
                .withCircle(5)
                .build();

        boolean hasSquare = pyramid.hasSquare();

        assertFalse(hasSquare);
    }

    @Test
    void removeComponent() {
        Pyramid pyramid = PyramidBuilder
                .forPlayer(Player.WHITE)
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

    @Test
    void shouldProtectInternalComponentsList() {
        List<SimplePiece> components = new ArrayList<>();

        Pyramid pyramid = new Pyramid(Player.WHITE, components);

        components.add(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 1));

        assertTrue(pyramid.getComponents().isEmpty());
    }

    @Test
    void shouldExposeImmutableComponentsList() {
        List<SimplePiece> components = new ArrayList<>();

        Pyramid pyramid = new Pyramid(Player.WHITE, components);

        assertThrows(
                UnsupportedOperationException.class,
                () -> pyramid.getComponents().add(new SimplePiece(PieceType.CIRCLE, Player.WHITE, 1))
    );
    }
}