package eu.nicosworld.capture.capturerule;

import eu.nicosworld.capture.*;
import eu.nicosworld.model.*;
import eu.nicosworld.move.FreePathMovementValidator;
import eu.nicosworld.move.Move;
import eu.nicosworld.move.RegularMoveGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Encounter (Rencontre) capture rule.
 *
 * <p>An encounter occurs when a piece can reach an enemy piece using a regular movement
 * (according to its movement rules), and both share a common value.</p>
 *
 * <p>This implementation fully supports pyramids:</p>
 * <ul>
 *   <li>A pyramid can capture using any of its component values or its total value.</li>
 *   <li>A pyramid can be captured either entirely (using its total value), or partially
 *       by capturing one of its components.</li>
 * </ul>
 *
 * <p>The rule works by generating all reachable positions, filtering valid enemy targets,
 * and then matching all possible "capture targets" (whole piece or components) based on value equality.</p>
 */
public class EncounterRule extends AbstractCaptureRule {

    public EncounterRule(RegularMoveGenerator generator, FreePathMovementValidator pathValidator) {
        super(generator, pathValidator);
    }

    @Override
    public List<CaptureAction> findCaptures(CaptureContext ctx) {
        List<CaptureAction> captures = new ArrayList<>();

        PieceAtPosition actor = ctx.actor();
        Piece attacker = actor.piece();

        // 1. Generate all reachable positions using regular movement
        List<Move> potentialReach = regularMoveGenerator.generate(ctx.state(), actor);

        for (Move move : potentialReach) {
            Position targetPos = move.to();
            Piece target = ctx.board().getPieceAt(targetPos);

            // 2. Check enemy presence
            if (target == null || !isEnemy(attacker, target)) {
                continue;
            }

            // 3. Check path clearance
            if (pathValidator.isBlocked(ctx.state(), actor.position(), targetPos)) {
                continue;
            }

            // 4. Find matching capture targets (handles pyramids correctly)
            List<CaptureTarget> matches = findMatchingTargets(attacker, target);

            if (matches.isEmpty()) {
                continue;
            }

            // 5. Create capture actions
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

    /**
     * Represents a potential capture target:
     * either a whole piece or a component of a pyramid.
     */
    private record CaptureTarget(Piece piece, int value, boolean isWholePiece) {}

    /**
     * Extracts all possible capture targets from a piece.
     *
     * <ul>
     *   <li>If the piece is not a pyramid, it returns a single target (the piece itself).</li>
     *   <li>If the piece is a pyramid, it returns:
     *     <ul>
     *       <li>each component as an individual target</li>
     *       <li>the whole pyramid using the sum of its components</li>
     *     </ul>
     *   </li>
     * </ul>
     */
    private List<CaptureTarget> extractTargets(Piece piece) {
        List<CaptureTarget> targets = new ArrayList<>();

        if (piece instanceof Pyramid) {
            int sum = 0;

            for (Piece comp : ((Pyramid) piece).getComponents()) {
                targets.add(new CaptureTarget(comp, comp.getValue(), false));
                sum += comp.getValue();
            }

            // Whole pyramid as a capture target
            targets.add(new CaptureTarget(piece, sum, true));
        } else {
            targets.add(new CaptureTarget(piece, piece.getValue(), true));
        }

        return targets;
    }

    /**
     * Finds all matching capture targets between attacker and target.
     *
     * <p>A match occurs when any value from the attacker equals any value
     * from the target (including pyramid components and total values).</p>
     *
     * @return list of valid capture targets on the defender side
     */
    private List<CaptureTarget> findMatchingTargets(Piece attacker, Piece target) {
        List<CaptureTarget> attackerTargets = extractTargets(attacker);
        List<CaptureTarget> targetTargets = extractTargets(target);

        List<CaptureTarget> matches = new ArrayList<>();

        for (CaptureTarget atk : attackerTargets) {
            for (CaptureTarget tgt : targetTargets) {
                if (atk.value() == tgt.value()) {
                    matches.add(tgt);
                }
            }
        }

        return matches;
    }
}