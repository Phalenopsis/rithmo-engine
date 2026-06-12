package eu.nicosworld.rithmo.engine.capture;

import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import eu.nicosworld.rithmo.engine.model.*;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;
import eu.nicosworld.rithmo.engine.setup.BoardBuilder;
import eu.nicosworld.rithmo.engine.setup.GameStateFactory;
import eu.nicosworld.rithmo.engine.threat.RegularThreatEngine;
import eu.nicosworld.rithmo.engine.threat.model.RegularThreats;

public class CaptureContextBuilder {

  private BoardBuilder boardBuilder = new BoardBuilder();
  private Position actorPosition;

  public CaptureContextBuilder from(BoardBuilder builder) {
    this.boardBuilder = builder;
    return this;
  }

  public CaptureContextBuilder at(int x, int y) {
    this.actorPosition = new Position(x, y);
    return this;
  }

  public CaptureContext build() {
    Board board = boardBuilder.build();
    Piece piece = board.getPieceAt(actorPosition);

    if (piece == null) {
      throw new IllegalStateException("No piece found at " + actorPosition + " to build context.");
    }

    GameState state = GameStateFactory.from(board, piece.getPlayer());
    RegularThreatEngine regularThreatEngine =
        new RegularThreatEngine(new RegularMoveGenerator(), new FreePathMovementValidator());
    PieceAtPosition actor = new PieceAtPosition(piece, actorPosition);
    RegularThreats candidates = regularThreatEngine.findRegularThreats(state, actor);

    return new CaptureContext(state, actor, candidates);
  }
}
