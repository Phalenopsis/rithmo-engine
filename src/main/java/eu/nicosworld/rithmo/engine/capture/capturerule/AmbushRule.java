package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.*;
import eu.nicosworld.rithmo.engine.model.GameState;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.Move;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;

import java.util.*;


public class AmbushRule extends AbstractCaptureRule {

    public AmbushRule(RegularMoveGenerator generator,
                      FreePathMovementValidator pathValidator) {
        super(generator, pathValidator);
    }

    @Override
    public List<CaptureAction> findCaptures(CaptureContext ctx) {

        Set<CaptureAction> captures = new HashSet<>();

        PieceAtPosition actor = ctx.actor();
        Piece attacker = actor.piece();

        // 1. All reachable positions (movement constraint anchor)
        List<Move> moves = regularMoveGenerator.generate(ctx.state(), actor);

        for (Move move : moves) {

            Position targetPos = move.to();
            Piece target = ctx.board().getPieceAt(targetPos);

            if (target == null || !isEnemy(attacker, target)) {
                continue;
            }

            // 2. Path constraint
            if (pathValidator.isBlocked(ctx.state(), actor.position(), targetPos)) {
                continue;
            }
            List<CaptureTarget> attackerOptions = extractTargets(attacker);

            List<CaptureTarget> targetOptions = extractTargets(target);

            List<CaptureTarget> allyOptions = findAllyVs(ctx.state(), attacker, target, targetPos);

            // 5. Try all target values
            for(CaptureTarget attackerOption : attackerOptions) {
                for (CaptureTarget t : targetOptions) {
                    for(CaptureTarget a : allyOptions) {

                        if(matches(attackerOption.value(), a.value(), t.value()))
                            captures.add(new CaptureAction(
                                    attacker,
                                    actor.position(),
                                    target,
                                    targetPos,
                                    t.piece(),
                                    t.isWholePiece(),
                                    CaptureType.AMBUSH
                            ));
                    }
                }
            }

        }

        return new ArrayList<>(captures);
    }


    private List<CaptureTarget> findAllyVs(GameState state, Piece attacker, Piece target, Position targetPos) {
        Map<Position, Piece> allies = regularMoveGenerator.getAllPieceAround(state, targetPos, isEnemyOf(target).and(isNot(attacker)));

        List<CaptureTarget> allyOptions = new ArrayList<>();
        for (Map.Entry<Position, Piece> entry : allies.entrySet()) {
            Position pos = entry.getKey();
            Piece ally = entry.getValue();
            if(!pathValidator.isBlocked(state, pos, targetPos)) {
                allyOptions.addAll(extractTargets(ally));
            }
        }

        return allyOptions;
    }

    /**
     * Arithmetic validation.
     */
    private boolean matches(int a, int b, int target) {

        return a + b == target
                || Math.abs(a - b) == target
                || a * b == target
                || (b != 0 && a % b == 0 && a / b == target)
                || (a != 0 && b % a == 0 && b / a == target);
    }
}