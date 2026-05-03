package eu.nicosworld.rithmo.engine.capture.model;

import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.model.Pyramid;

public record InvolvedPiece(
        Piece parentPiece,      // La pièce sur le board (Pyramid ou SimplePiece)
        Position position,      // Sa position
        Piece specificComponent // Le composant précis (ex: Triangle 36)
) {
    /**
     * Crée une InvolvedPiece représentant une pièce entière (Cercle, Carré, Triangle, ou Pyramide complète).
     * Dans ce cas, le parent et le composant spécifique sont identiques.
     */
    public static InvolvedPiece whole(Piece piece, Position position) {
        return new InvolvedPiece(piece, position, piece);
    }

    /**
     * Crée une InvolvedPiece représentant un composant spécifique à l'intérieur d'une pyramide.
     */
    public static InvolvedPiece component(Pyramid pyramid, Piece component, Position position) {
        return new InvolvedPiece(pyramid, position, component);
    }

    /**
     * Utilitaire pour vérifier si l'implication concerne la pièce dans sa globalité.
     */
    public boolean isWhole() {
        return parentPiece.equals(specificComponent);
    }
}
