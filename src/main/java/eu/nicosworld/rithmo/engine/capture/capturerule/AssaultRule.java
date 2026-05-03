package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureRule;
import eu.nicosworld.rithmo.engine.capture.model.*;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.move.Delta;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implements the Assault (Assaut) capture rule.
 * <p>
 * An assault occurs when an attacking piece targets an enemy piece from a distance.
 * The rule requires at least one empty square between the attacker and the target.
 * The arithmetic condition is satisfied if the attacker's value multiplied or
 * divided by the number of empty squares equals the target's value.
 * </p>
 */
public class AssaultRule extends AbstractCaptureRule {

    public AssaultRule(RegularMoveGenerator generator,
                       FreePathMovementValidator pathValidator) {
        super(generator, pathValidator);
    }

    /**
     * Scans all 8 directions from the actor's position to find potential assault captures.
     *
     * @param context The current game state and the actor piece.
     * @return A list of {@link CaptureAction} representing valid assaults.
     */
    @Override
    public List<CaptureAction> findCaptures(CaptureContext context) {
        List<CaptureAction> captures = new ArrayList<>();
        PieceAtPosition actor = context.actor();

        // Standard Rithmomachie directions: 4 Orthogonal + 4 Diagonal
        List<Delta> directions = List.of(
                new Delta(1, 0), new Delta(-1, 0), new Delta(0, 1), new Delta(0, -1),
                new Delta(1, 1), new Delta(1, -1), new Delta(-1, 1), new Delta(-1, -1)
        );

        for (Delta direction : directions) {
            captures.addAll(scanDirection(context, actor, direction));
        }

        return captures;
    }

    /**
     * Projects a ray in a specific direction and counts empty spaces until a piece is hit.
     */
    private List<CaptureAction> scanDirection(CaptureContext context, PieceAtPosition actor, Delta direction) {
        Set<CaptureAction> results = new HashSet<>();
        Position currentPosition = actor.position();
        int emptySpacesCount = 0;

        while (true) {
            currentPosition = new Position(
                    currentPosition.getX() + direction.dx(),
                    currentPosition.getY() + direction.dy()
            );

            if (!context.board().isInside(currentPosition)) {
                break;
            }

            Piece potentialTarget = context.board().getPieceAt(currentPosition);

            if (potentialTarget == null) {
                emptySpacesCount++;
                continue;
            }

            if (isEnemy(actor.piece(), potentialTarget)) {
                if (emptySpacesCount > 0) {
                    validateAssaultArithmetic(actor, potentialTarget, currentPosition, emptySpacesCount, results);
                }
            }

            // Any piece blocks the line of sight for an assault
            break;
        }

        return new ArrayList<>(results);
    }

    /**
     * Evaluates all possible component combinations between the attacker and the target
     * and uses the factory method to create valid CaptureActions.
     */
    private void validateAssaultArithmetic(PieceAtPosition actor,
                                           Piece targetPiece,
                                           Position targetPosition,
                                           int emptySpaces,
                                           Set<CaptureAction> results) {

        List<CaptureTarget> attackerOptions = extractTargets(actor.piece());
        List<CaptureTarget> targetOptions = extractTargets(targetPiece);

        for (CaptureTarget attackerOption : attackerOptions) {
            for (CaptureTarget targetOption : targetOptions) {

                if (isAssaultConditionMet(attackerOption.value(), targetOption.value(), emptySpaces)) {

                    InvolvedPiece involvedActor = new InvolvedPiece(
                            actor.piece(),
                            actor.position(),
                            attackerOption.piece()
                    );

                    InvolvedPiece involvedTarget = new InvolvedPiece(
                            targetPiece,
                            targetPosition,
                            targetOption.piece()
                    );

                    // Using the new factory method for cleaner code
                    results.add(CaptureAction.assault(involvedActor, involvedTarget));
                }
            }
        }
    }

    private boolean isAssaultConditionMet(int attackerValue, int targetValue, int distance) {
        boolean multiplicationMatch = (attackerValue * distance == targetValue);
        boolean divisionMatch = (attackerValue % distance == 0 && attackerValue / distance == targetValue);

        return multiplicationMatch || divisionMatch;
    }
}