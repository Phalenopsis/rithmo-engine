package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureRule;
import eu.nicosworld.rithmo.engine.capture.justification.PowerJustification;
import eu.nicosworld.rithmo.engine.capture.justification.PowerRelation;
import eu.nicosworld.rithmo.engine.capture.model.*;
import eu.nicosworld.rithmo.engine.model.Piece;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.move.FreePathMovementValidator;
import eu.nicosworld.rithmo.engine.move.Move;
import eu.nicosworld.rithmo.engine.move.RegularMoveGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.pow;

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

                    resolvePower(
                            attackerOption.value(),
                            targetOption.value()
                    ).ifPresent(justification ->
                            captures.add(
                                    CaptureAction.power(
                                            new InvolvedPiece(
                                                    attackerPiece,
                                                    attackerPosition,
                                                    attackerOption.piece()
                                            ),
                                            new InvolvedPiece(
                                                    targetPiece,
                                                    targetPosition,
                                                    targetOption.piece()
                                            ),
                                            justification
                                    )
                            )
                    );
                }
            }
        }

        return captures;
    }

    private Optional<PowerJustification> resolvePower(
            int attackerValue,
            int targetValue
    ) {
        for (int exponent = 2; exponent <= 9; exponent++) {

            if (pow(attackerValue, exponent) == targetValue) {
                return Optional.of(
                        new PowerJustification(
                                attackerValue,
                                PowerRelation.POWER,
                                exponent,
                                targetValue
                        )
                );
            }

            if (pow(targetValue, exponent) == attackerValue) {
                return Optional.of(
                        new PowerJustification(
                                attackerValue,
                                PowerRelation.ROOT,
                                exponent,
                                targetValue
                        )
                );
            }
        }

        return Optional.empty();
    }
}