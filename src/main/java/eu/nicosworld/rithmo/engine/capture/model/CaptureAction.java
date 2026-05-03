package eu.nicosworld.rithmo.engine.capture.model;

import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.Pyramid;
import eu.nicosworld.rithmo.engine.capture.CaptureType;
import java.util.List;

public record CaptureAction(
        InvolvedPiece actor,
        InvolvedPiece target,
        List<InvolvedPiece> supporters,
        CaptureType type
) {
    public CaptureAction {
        // Sécurité immuabilité
        supporters = List.copyOf(supporters);
    }

    public static CaptureAction assault(InvolvedPiece actor,
                                        InvolvedPiece target) {
        return new CaptureAction(actor, target, List.of(), CaptureType.ASSAULT);
    }

    public static CaptureAction ambush(InvolvedPiece actor,
                                       InvolvedPiece target,
                                       InvolvedPiece supporter) {
        return new CaptureAction(actor, target, List.of(supporter), CaptureType.AMBUSH);
    }

    public static CaptureAction encounter(InvolvedPiece actor,
                                          InvolvedPiece target) {
        return new CaptureAction(actor, target, List.of(), CaptureType.ENCOUNTER);
    }

    public static CaptureAction power(InvolvedPiece actor,
                                      InvolvedPiece target) {
        return new CaptureAction(actor, target, List.of(), CaptureType.POWER);
    }

    // Utilitaires pour le moteur et l'UI
    public boolean isWholeCapture() {
        return target.parentPiece().equals(target.specificComponent());
    }

    public boolean isReversible() {
        return !(target.parentPiece() instanceof Pyramid);
    }

    // Ces méthodes permettent de garder ton CaptureApplier tel quel
    public Piece targetPiece() { return target.parentPiece(); }
    public Position targetPosition() { return target.position(); }
    public Piece capturedPiece() { return target.specificComponent(); }
}