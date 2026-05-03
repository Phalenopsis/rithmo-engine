package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureRule;
import eu.nicosworld.rithmo.engine.capture.model.*;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.Move;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Power capture rule.
 * <p>
 * A power capture occurs when the value of the attacking piece (or one of its components)
 * is the square or cube of the target's value, or vice versa.
 * Like the Encounter rule, the attacker must physically reach the target's square.
 * </p>
 */
public class PowerRule extends AbstractCaptureRule {

    public PowerRule(RegularMoveGenerator generator,
                     FreePathMovementValidator pathValidator) {
        super(generator, pathValidator);
    }

    /**
     * Finds all potential power captures by checking all reachable positions.
     *
     * @param context The current game state and the actor piece.
     * @return A list of {@link CaptureAction} representing valid power captures.
     */
    @Override
    public List<CaptureAction> findCaptures(CaptureContext context) {
        List<CaptureAction> captures = new ArrayList<>();

        PieceAtPosition actor = context.actor();
        Piece attackerPiece = actor.piece();
        Position attackerPosition = actor.position();

        // 1. Generate all reachable moves for the current piece
        List<Move> potentialMoves = regularMoveGenerator.generate(context.state(), actor);

        for (Move move : potentialMoves) {
            Position targetPosition = move.to();
            Piece targetPiece = context.board().getPieceAt(targetPosition);

            // 2. Filter: Target must be an enemy
            if (targetPiece == null || !isEnemy(attackerPiece, targetPiece)) {
                continue;
            }

            // 3. Filter: Check for path obstructions
            if (pathValidator.isBlocked(context.state(), attackerPosition, targetPosition)) {
                continue;
            }

            // 4. Extraction and Comparison
            List<CaptureTarget> attackerOptions = extractTargets(attackerPiece);
            List<CaptureTarget> targetOptions = extractTargets(targetPiece);

            for (CaptureTarget attackerOption : attackerOptions) {
                for (CaptureTarget targetOption : targetOptions) {

                    if (isPowerMatch(attackerOption.value(), targetOption.value())) {

                        InvolvedPiece involvedActor = new InvolvedPiece(
                                attackerPiece,
                                attackerPosition,
                                attackerOption.piece()
                        );

                        InvolvedPiece involvedTarget = new InvolvedPiece(
                                targetPiece,
                                targetPosition,
                                targetOption.piece()
                        );

                        // Using the static factory method for Power capture
                        captures.add(CaptureAction.power(involvedActor, involvedTarget));
                    }
                }
            }
        }

        return captures;
    }

    /**
     * Arithmetic logic for Power capture:
     * Checks if one value is the square or cube of the other.
     */
    private boolean isPowerMatch(int attackerValue, int targetValue) {
        return attackerValue == targetValue * targetValue
                || attackerValue == targetValue * targetValue * targetValue
                || attackerValue * attackerValue == targetValue
                || attackerValue * attackerValue * attackerValue == targetValue;
    }
}