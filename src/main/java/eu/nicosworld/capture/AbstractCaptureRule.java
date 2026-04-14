package eu.nicosworld.capture;

import eu.nicosworld.model.Piece;
import eu.nicosworld.move.FreePathMovementValidator;
import eu.nicosworld.move.RegularMoveGenerator;

public abstract class AbstractCaptureRule implements CaptureRule {

    protected final RegularMoveGenerator regularMoveGenerator;
    protected final FreePathMovementValidator pathValidator;

    protected AbstractCaptureRule(RegularMoveGenerator regularMoveGenerator, FreePathMovementValidator pathValidator) {
        this.regularMoveGenerator = regularMoveGenerator;
        this.pathValidator = pathValidator;
    }

    protected boolean isEnemy(Piece a, Piece b) {
        return a.getPlayer() != b.getPlayer();
    }
}
