package eu.nicosworld.rithmo.engine.capture.model;

import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.Position;

public record InvolvedPiece(
        Piece parentPiece,      // La pièce sur le board (Pyramid ou SimplePiece)
        Position position,      // Sa position
        Piece specificComponent // Le composant précis (ex: Triangle 36)
) {}
