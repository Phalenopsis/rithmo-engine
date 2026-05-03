package eu.nicosworld.rithmo.engine.capture;

import eu.nicosworld.rithmo.engine.capture.model.CaptureTarget;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.Pyramid;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;

import java.util.*;
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
        // On utilise un Map pour dédoublonner par valeur
        // La clé est la valeur arithmétique (ex: 64)
        Map<Integer, CaptureTarget> uniqueTargets = new LinkedHashMap<>();

        // 1. On ajoute d'abord la pièce entière (prioritaire)
        uniqueTargets.put(piece.getValue(), new CaptureTarget(piece, piece.getValue(), true));

        // 2. Si c'est une pyramide, on ajoute ses composants
        if (piece instanceof Pyramid pyramid) {
            for (Piece component : pyramid.getComponents()) {
                // putIfAbsent est crucial : si le composant a la même valeur
                // que la pyramide entière, on garde la "pièce entière" (déjà insérée)
                // car capturer la pyramide entière est l'action racine.
                uniqueTargets.putIfAbsent(component.getValue(),
                        new CaptureTarget(component, component.getValue(), false));
            }
        }

        return new ArrayList<>(uniqueTargets.values());
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