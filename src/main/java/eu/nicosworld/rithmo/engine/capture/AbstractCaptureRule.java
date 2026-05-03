package eu.nicosworld.rithmo.engine.capture;

import eu.nicosworld.rithmo.engine.capture.model.CaptureTarget;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.Pyramid;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public abstract class AbstractCaptureRule implements CaptureRule {

    protected final RegularMoveGenerator regularMoveGenerator;
    protected final FreePathMovementValidator pathValidator;

    protected AbstractCaptureRule(RegularMoveGenerator regularMoveGenerator,
                                  FreePathMovementValidator pathValidator) {
        this.regularMoveGenerator = regularMoveGenerator;
        this.pathValidator = pathValidator;
    }

    protected boolean isEnemy(Piece a, Piece b) {
        return a.getPlayer() != b.getPlayer();
    }

    /**
     * Extracts all capture targets from a piece.
     * - Simple piece → itself
     * - Pyramid → components + total value
     */
    protected List<CaptureTarget> extractTargets(Piece piece) {
        List<CaptureTarget> targets = new ArrayList<>();

        if (piece instanceof Pyramid pyramid) {
            int sum = 0;

            for (Piece comp : pyramid.getComponents()) {
                targets.add(new CaptureTarget(comp, comp.getValue(), false));
                sum += comp.getValue();
            }

            targets.add(new CaptureTarget(piece, sum, true));
        } else {
            targets.add(new CaptureTarget(piece, piece.getValue(), true));
        }

        return targets;
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
        return attacker.value() == target.value();
    }

    protected Predicate<Piece> isEnemyOf(Piece piece) {
        return p -> p.getPlayer() != piece.getPlayer();
    }

    protected Predicate<Piece> isAllyOf(Piece piece) {
        return p -> p.getPlayer() == piece.getPlayer();
    }

    protected Predicate<Piece> isNot(Piece piece) {
        return p -> !p.equals(piece);
    }
}