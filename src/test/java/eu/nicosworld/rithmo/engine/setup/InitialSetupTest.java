package eu.nicosworld.rithmo.engine.setup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import eu.nicosworld.rithmo.engine.model.Board;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import org.junit.jupiter.api.Test;

class InitialSetupTest {

  @Test
  void createBoissiere() {
    Board board = InitialSetup.createBoissiere();

    assertTrue(board.getBlackPyramid().isPresent());
    PieceAtPosition blackPyramid = board.getBlackPyramid().get();
    assertThat(blackPyramid.piece().getValue()).isEqualTo(91);
    assertThat(blackPyramid.position()).isEqualTo(new Position(1, 0));

    assertTrue(board.getWhitePyramid().isPresent());
    PieceAtPosition whitePyramid = board.getWhitePyramid().get();
    assertThat(whitePyramid.piece().getValue()).isEqualTo(190);
    assertThat(whitePyramid.position()).isEqualTo(new Position(14, 6));
  }
}
