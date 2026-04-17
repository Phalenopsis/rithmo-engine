package eu.nicosworld.capture.capturerule;

import eu.nicosworld.capture.*;
import eu.nicosworld.model.*;
import eu.nicosworld.move.Delta;
import eu.nicosworld.move.FreePathMovementValidator;
import eu.nicosworld.move.RegularMoveGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Assault capture rule implementation.
 * <p>
 * This rule scans in 8 directions (orthogonal and diagonal) from the actor's position.
 * A capture is possible if an enemy piece is found with at least one empty square in between,
 * provided the arithmetic condition (multiplication or division) is met.
 * </p>
 */
public class AssaultRule extends AbstractCaptureRule {

    public AssaultRule(RegularMoveGenerator generator,
                       FreePathMovementValidator pathValidator) {
        super(generator, pathValidator);
    }

    @Override
    public List<CaptureAction> findCaptures(CaptureContext ctx) {
        List<CaptureAction> captures = new ArrayList<>();
        PieceAtPosition actor = ctx.actor();

        // Directions: 4 Orthogonal + 4 Diagonal
        List<Delta> directions = List.of(
                new Delta(1, 0), new Delta(-1, 0), new Delta(0, 1), new Delta(0, -1),
                new Delta(1, 1), new Delta(1, -1), new Delta(-1, 1), new Delta(-1, -1)
        );

        for (Delta delta : directions) {
            captures.addAll(scanDirection(ctx, actor, delta));
        }

        return captures;
    }

    /**
     * Projects a ray in a specific direction to find the first piece encountered.
     */
    private List<CaptureAction> scanDirection(CaptureContext ctx, PieceAtPosition actor, Delta delta) {
        Set<CaptureAction> results = new HashSet<>();
        Position current = actor.position();
        int emptySpaces = 0;

        while (true) {
            // Move to the next tile in the direction
            current = new Position(current.getX() + delta.dx(), current.getY() + delta.dy());

            // Stop if we leave the board boundaries
            if (!ctx.board().isInside(current)) {
                break;
            }

            Piece target = ctx.board().getPieceAt(current);

            // If the square is empty, increment the space counter and continue
            if (target == null) {
                emptySpaces++;
                continue;
            }

            // If we hit a piece, check if it is an enemy
            if (isEnemy(actor.piece(), target)) {
                // Rule requirement: at least one empty square between them
                if (emptySpaces > 0) {
                    validateArithmetic(actor, target, current, emptySpaces, results);
                }
            }

            // A piece (ally or enemy) always blocks the "line of sight" for an assault
            break;
        }

        return new ArrayList<>(results);
    }

    /**
     * Checks all arithmetic combinations (handling Pyramids via extractTargets).
     */
    private void validateArithmetic(PieceAtPosition actor, Piece target, Position targetPos, int emptySpaces, Set<CaptureAction> results) {
        List<CaptureTarget> attackerOptions = extractTargets(actor.piece());
        List<CaptureTarget> targetOptions = extractTargets(target);

        for (CaptureTarget a : attackerOptions) {
            for (CaptureTarget t : targetOptions) {
                if (matchesAssault(a.value(), t.value(), emptySpaces)) {
                    results.add(new CaptureAction(
                            actor.piece(),
                            actor.position(),
                            target,
                            targetPos,
                            t.piece(),
                            t.isWholePiece(),
                            CaptureType.ASSAULT
                    ));
                }
            }
        }
    }

    /**
     * Core math: Attacker * Spaces = Target OR Attacker / Spaces = Target.
     */
    private boolean matchesAssault(int attackerVal, int targetVal, int emptySpaces) {
        boolean multiplicationMatch = (attackerVal * emptySpaces == targetVal);
        boolean divisionMatch = (attackerVal % emptySpaces == 0 && attackerVal / emptySpaces == targetVal);
        return multiplicationMatch || divisionMatch;
    }
}