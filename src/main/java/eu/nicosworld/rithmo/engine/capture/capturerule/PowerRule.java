package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureRule;
import eu.nicosworld.rithmo.engine.capture.CaptureType;
import eu.nicosworld.rithmo.engine.capture.*;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.Move;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;

import java.util.*;

public class PowerRule extends AbstractCaptureRule {
    public PowerRule(RegularMoveGenerator generator,
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
                        CaptureType.POWER
                ));
            }
        }

        return captures;
    }

    /**
     * Generic matching engine between attacker and target possibilities.
     */
    protected List<CaptureTarget> findMatchingTargets(
            Piece attacker,
            Piece target
    ) {
        List<CaptureTarget> attackerTargets = extractTargets(attacker);
        List<CaptureTarget> targetTargets = extractTargets(target);

        Set<CaptureTarget> matches = new HashSet<>();

        for (CaptureTarget atk : attackerTargets) {
            for (CaptureTarget tgt : targetTargets) {
                if (isMatch(atk, tgt)) {
                    matches.add(tgt);
                }
            }
        }

        return new ArrayList<>(matches);
    }

    /**
     * Default matching rule = equality of values.
     * Can be overridden by specific rules (Embûche, etc.).
     */
    protected boolean isMatch(CaptureTarget attacker, CaptureTarget target) {
        return attacker.value() == target.value() * target.value()
                || attacker.value() == target.value() * target.value() * target.value()
                || attacker.value() * attacker.value() == target.value()
                || attacker.value() * attacker.value() * attacker.value() == target.value();
    }
}
