package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.AbstractCaptureRule;
import eu.nicosworld.rithmo.engine.capture.justification.EncounterJustification;
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

/**
 * Implements the Encounter (Rencontre) capture rule.
 * <p>
 * An encounter occurs when an attacking piece moves to a square occupied by an enemy
 * piece (via its regular movement) and their values match.
 * Unlike Assault, an encounter requires the attacker to physically reach the target's square.
 * </p>
 */
public class EncounterRule extends AbstractCaptureRule {

    public EncounterRule(RegularMoveGenerator generator,
                         FreePathMovementValidator pathValidator) {
        super(generator, pathValidator);
    }

    /**
     * Finds all potential encounter captures by checking all reachable positions.
     *
     * @param context The current game state and the actor piece.
     * @return A list of {@link CaptureAction} representing valid encounters.
     */
    @Override
    public List<CaptureAction> findCaptures(CaptureContext context) {
        List<CaptureAction> captures = new ArrayList<>();

        PieceAtPosition actor = context.actor();
        Piece attackerPiece = actor.piece();
        Position attackerPosition = actor.position();

        // 1. Generate all squares reachable by the piece's move type (Circle, Triangle, Square)
        List<Move> potentialMoves = regularMoveGenerator.generate(context.state(), actor);

        for (Move move : potentialMoves) {
            Position targetPosition = move.to();
            Piece targetPiece = context.board().getPieceAt(targetPosition);

            // 2. Filter: Target must be an enemy piece
            if (targetPiece == null || !isEnemy(attackerPiece, targetPiece)) {
                continue;
            }

            // 3. Filter: Movement path must be clear (no jumping over pieces except for irregular moves)
            if (pathValidator.isBlocked(context.state(), attackerPosition, targetPosition)) {
                continue;
            }

            // 4. Extraction: Find which components (or whole piece) match the equality condition.
            // Note: findMatchingTargets uses isMatch (equality) internally.
            List<CaptureTarget> attackerOptions = extractTargets(attackerPiece);
            List<CaptureTarget> targetOptions = extractTargets(targetPiece);

            for (CaptureTarget attackerOption : attackerOptions) {
                for (CaptureTarget targetOption : targetOptions) {

                    // Default encounter check is value equality
                    resolveEncounter(attackerOption.value(), targetOption.value())
                            .ifPresent(justification ->
                                    captures.add(
                                            CaptureAction.encounter(
                                                    new InvolvedPiece(
                                                            actor.piece(),
                                                            actor.position(),
                                                            attackerOption.piece()
                                                    ),
                                                    new InvolvedPiece(
                                                            targetPiece,
                                                            targetPosition,
                                                            targetOption.piece()
                                                    ),
                                                    justification
                                            )
                                    ));
                }
            }
        }

        return captures;
    }

    private Optional<EncounterJustification> resolveEncounter(
            int actorValue,
            int targetValue
    ) {
        if (actorValue == targetValue) {
            return Optional.of(
                    new EncounterJustification(
                            actorValue
                    )
            );
        }

        return Optional.empty();
    }
}