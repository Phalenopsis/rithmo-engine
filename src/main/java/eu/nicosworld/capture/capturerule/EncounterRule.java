package eu.nicosworld.capture.capturerule;

import eu.nicosworld.capture.*;
import eu.nicosworld.model.*;
import eu.nicosworld.move.FreePathMovementValidator;
import eu.nicosworld.move.Move;
import eu.nicosworld.move.RegularMoveGenerator;

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