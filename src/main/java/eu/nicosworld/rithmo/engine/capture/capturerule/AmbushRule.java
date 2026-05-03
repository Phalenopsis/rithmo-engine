package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureRule;
import eu.nicosworld.rithmo.engine.capture.model.*;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.Move;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;

import java.util.*;

/**
 * Implements the Ambush (Embûche) capture rule.
 * <p>
 * An ambush occurs when an attacking piece and an allied piece coordinate
 * to capture an enemy piece. The arithmetic combination (sum, difference,
 * product, or division) of the attacker's value and the ally's value
 * must equal the target's value.
 * </p>
 */
public class AmbushRule extends AbstractCaptureRule {

    public AmbushRule(RegularMoveGenerator generator,
                      FreePathMovementValidator pathValidator) {
        super(generator, pathValidator);
    }

    /**
     * Scans the board for all possible ambush captures for the active piece.
     *
     * @param context The current game state and the actor piece.
     * @return A list of {@link CaptureAction} detailing every valid ambush found.
     */
    @Override
    public List<CaptureAction> findCaptures(CaptureContext context) {
        Set<CaptureAction> captures = new HashSet<>();

        PieceAtPosition actor = context.actor();
        Piece attackerPiece = actor.piece();
        Position attackerPosition = actor.position();

        // 1. Generate all potential destination squares for the attacker
        List<Move> potentialMoves = regularMoveGenerator.generate(context.state(), actor);

        for (Move move : potentialMoves) {
            Position targetPosition = move.to();
            Piece targetPiece = context.board().getPieceAt(targetPosition);

            // 2. Filter: Target must be an enemy piece
            if (targetPiece == null || !isEnemy(attackerPiece, targetPiece)) {
                continue;
            }

            // 3. Filter: Movement path to the target must be clear
            if (pathValidator.isBlocked(context.state(), attackerPosition, targetPosition)) {
                continue;
            }

            // 4. Extract arithmetic options (handling pyramids components vs total value)
            List<CaptureTarget> attackerOptions = extractTargets(attackerPiece);
            List<CaptureTarget> targetOptions = extractTargets(targetPiece);

            // 5. Look for allies around the target position to assist in the ambush
            Map<Position, Piece> alliesAroundTarget = regularMoveGenerator.getAllPieceAround(
                    context.state(),
                    targetPosition,
                    isEnemyOf(targetPiece).and(isNot(attackerPiece))
            );

            for (Map.Entry<Position, Piece> entry : alliesAroundTarget.entrySet()) {
                Position allyPosition = entry.getKey();
                Piece allyPiece = entry.getValue();

                // Path between ally and target must also be clear
                if (pathValidator.isBlocked(context.state(), allyPosition, targetPosition)) {
                    continue;
                }

                List<CaptureTarget> allyOptions = extractTargets(allyPiece);

                // 6. Evaluate all arithmetic combinations between Attacker, Ally, and Target
                for (CaptureTarget attackerOption : attackerOptions) {
                    for (CaptureTarget targetOption : targetOptions) {
                        for (CaptureTarget allyOption : allyOptions) {

                            if (matchesAmbush(attackerOption.value(), allyOption.value(), targetOption.value())) {

                                // Build context for the actor, the target, and the specific supporter
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

                                InvolvedPiece involvedAlly = new InvolvedPiece(
                                        allyPiece,
                                        allyPosition,
                                        allyOption.piece()
                                );

                                // Use the factory method to encapsulate capture creation
                                captures.add(CaptureAction.ambush(
                                        involvedActor,
                                        involvedTarget,
                                        involvedAlly
                                ));
                            }
                        }
                    }
                }
            }
        }

        return new ArrayList<>(captures);
    }

    /**
     * Validates if the combination of two values matches a target value
     * using basic Rithmomachie arithmetic operations.
     */
    private boolean matchesAmbush(int attackerValue, int allyValue, int targetValue) {
        return attackerValue + allyValue == targetValue
                || Math.abs(attackerValue - allyValue) == targetValue
                || attackerValue * allyValue == targetValue
                || (allyValue != 0 && attackerValue % allyValue == 0 && attackerValue / allyValue == targetValue)
                || (attackerValue != 0 && allyValue % attackerValue == 0 && allyValue / attackerValue == targetValue);
    }
}