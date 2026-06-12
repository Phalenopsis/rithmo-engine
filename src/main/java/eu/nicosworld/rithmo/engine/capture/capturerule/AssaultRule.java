package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.justification.AssaultJustification;
import eu.nicosworld.rithmo.engine.capture.justification.AssaultOperator;
import eu.nicosworld.rithmo.engine.capture.model.*;
import eu.nicosworld.rithmo.engine.model.PieceAtPosition;
import eu.nicosworld.rithmo.engine.model.Position;
import eu.nicosworld.rithmo.engine.move.Delta;
import eu.nicosworld.rithmo.engine.threat.AttackSupport;
import java.util.*;

/**
 * Implements the Assault (Assaut) capture rule.
 *
 * <p>An assault occurs when an attacking piece targets an enemy piece from a distance. The rule
 * requires at least one empty square between the attacker and the target. The arithmetic condition
 * is satisfied if the attacker's value multiplied or divided by the number of empty squares equals
 * the target's value.
 */
public final class AssaultRule implements CaptureRule {

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
    List<Delta> directions =
        List.of(
            new Delta(1, 0),
            new Delta(-1, 0),
            new Delta(0, 1),
            new Delta(0, -1),
            new Delta(1, 1),
            new Delta(1, -1),
            new Delta(-1, 1),
            new Delta(-1, -1));

    for (Delta direction : directions) {
      captures.addAll(scanDirection(context, actor, direction));
    }

    return captures;
  }

  /** Projects a ray in a specific direction and counts empty spaces until a piece is hit. */
  private List<CaptureAction> scanDirection(
      CaptureContext context, PieceAtPosition actor, Delta direction) {
    Set<CaptureAction> results = new HashSet<>();
    Position currentPosition = actor.position();
    int emptySpacesCount = 0;

    while (true) {
      currentPosition =
          new Position(
              currentPosition.getX() + direction.dx(), currentPosition.getY() + direction.dy());

      if (!context.board().isInside(currentPosition)) {
        break;
      }

      PieceAtPosition potentialTarget = context.board().getPieceAtPosition(currentPosition);

      if (potentialTarget.piece() == null) {
        emptySpacesCount++;
        continue;
      }

      if (AttackSupport.areEnemies(actor.piece(), potentialTarget.piece())) {
        if (emptySpacesCount > 0) {
          validateAssaultArithmetic(actor, potentialTarget, emptySpacesCount, results);
        }
      }

      // Any piece blocks the line of sight for an assault
      break;
    }

    return new ArrayList<>(results);
  }

  /**
   * Evaluates all possible component combinations between the attacker and the target and uses the
   * factory method to create valid CaptureActions.
   */
  private void validateAssaultArithmetic(
      PieceAtPosition actor, PieceAtPosition target, int emptySpaces, Set<CaptureAction> results) {

    List<InvolvedPiece> attackerOptions = AttackSupport.getPieceVariants(actor);
    List<InvolvedPiece> targetOptions = AttackSupport.getPieceVariants(target);

    for (InvolvedPiece attackerOption : attackerOptions) {
      for (InvolvedPiece targetOption : targetOptions) {

        resolveAssault(
                attackerOption.specificComponent().getValue(),
                targetOption.specificComponent().getValue(),
                emptySpaces)
            .ifPresent(
                justification ->
                    results.add(
                        CaptureAction.assault(attackerOption, targetOption, justification)));
      }
    }
  }

  private Optional<AssaultJustification> resolveAssault(
      int attackerValue, int targetValue, int distance) {
    if (attackerValue * distance == targetValue) {
      return Optional.of(
          new AssaultJustification(distance, AssaultOperator.MULTIPLY, attackerValue, targetValue));
    }

    if (attackerValue % distance == 0 && attackerValue / distance == targetValue) {
      return Optional.of(
          new AssaultJustification(distance, AssaultOperator.DIVIDE, attackerValue, targetValue));
    }

    return Optional.empty();
  }
}
