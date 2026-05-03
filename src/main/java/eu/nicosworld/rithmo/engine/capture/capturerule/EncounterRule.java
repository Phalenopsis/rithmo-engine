package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureRule;
import eu.nicosworld.rithmo.engine.capture.CaptureType;
import eu.nicosworld.rithmo.engine.capture.model.CaptureAction;
import eu.nicosworld.rithmo.engine.capture.model.CaptureContext;
import eu.nicosworld.rithmo.engine.capture.model.CaptureTarget;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.Move;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Encounter rule:
 * capture occurs when attacker reaches enemy via regular move
 * AND a value match exists.
 */
public class EncounterRule extends AbstractCaptureRule {

    public EncounterRule(RegularMoveGenerator generator,
                         FreePathMovementValidator pathValidator) {
        super(generator, pathValidator);
    }

    @Override
    public List<CaptureAction> findCaptures(CaptureContext ctx) {

        List<CaptureAction> captures = new ArrayList<>();

        PieceAtPosition actor = ctx.actor();
        Piece attacker = actor.piece();

        List<Move> potentialReach =
                regularMoveGenerator.generate(ctx.state(), actor);

        for (Move move : potentialReach) {

            Position targetPos = move.to();
            Piece target = ctx.board().getPieceAt(targetPos);

            if (target == null || !isEnemy(attacker, target)) {
                continue;
            }

            if (pathValidator.isBlocked(ctx.state(), actor.position(), targetPos)) {
                continue;
            }

            List<CaptureTarget> matches = findMatchingTargets(attacker, target);

            for (CaptureTarget match : matches) {
                captures.add(new CaptureAction(
                        attacker,
                        actor.position(),
                        target,
                        targetPos,
                        match.piece(),
                        match.isWholePiece(),
                        CaptureType.ENCOUNTER
                ));
            }
        }

        return captures;
    }
}