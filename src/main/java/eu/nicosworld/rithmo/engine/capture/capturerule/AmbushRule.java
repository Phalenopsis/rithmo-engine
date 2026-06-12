package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.justification.AmbushJustification;
import eu.nicosworld.rithmo.engine.capture.justification.AmbushOperator;
import eu.nicosworld.rithmo.engine.capture.model.*;
import eu.nicosworld.rithmo.engine.threat.model.AssistedThreat;
import java.util.*;

/**
 * Implements the Ambush (Embûche) capture rule.
 *
 * <p>An ambush occurs when an attacking piece and an allied piece coordinate to capture an enemy
 * piece. The arithmetic combination (sum, difference, product, or division) of the attacker's value
 * and the ally's value must equal the target's value.
 */
public final class AmbushRule implements CaptureRule {

  /**
   * Scans the board for all possible ambush captures for the active piece.
   *
   * @param context The current game state and the actor piece.
   * @return A list of {@link CaptureAction} detailing every valid ambush found.
   */
  @Override
  public List<CaptureAction> findCaptures(CaptureContext context) {
    Set<CaptureAction> captures = new HashSet<>();
    for (AssistedThreat assistedThreat : context.regularAssistedThreat()) {
      resolveAmbush(
              assistedThreat.getActorValue(),
              assistedThreat.getAllyValue(),
              assistedThreat.getTargetValue())
          .ifPresent(
              justification -> {
                captures.add(
                    CaptureAction.ambush(
                        assistedThreat.actor(),
                        assistedThreat.target(),
                        assistedThreat.ally(),
                        justification));
              });
    }
    return new ArrayList<>(captures);
  }

  /**
   * Validates if the combination of two values matches a target value using basic Rithmomachie
   * arithmetic operations.
   */
  private Optional<AmbushJustification> resolveAmbush(
      int attackerValue, int supporterValue, int targetValue) {

    if (attackerValue + supporterValue == targetValue) {
      return Optional.of(
          new AmbushJustification(attackerValue, AmbushOperator.ADD, supporterValue, targetValue));
    }

    if (attackerValue - supporterValue == targetValue) {
      return Optional.of(
          new AmbushJustification(
              attackerValue, AmbushOperator.SUBTRACT, supporterValue, targetValue));
    }

    if (supporterValue - attackerValue == targetValue) {
      return Optional.of(
          new AmbushJustification(
              attackerValue, AmbushOperator.SUBTRACT_INV, supporterValue, targetValue));
    }

    if (attackerValue * supporterValue == targetValue) {
      return Optional.of(
          new AmbushJustification(
              attackerValue, AmbushOperator.MULTIPLY, supporterValue, targetValue));
    }

    if (supporterValue != 0
        && attackerValue % supporterValue == 0
        && attackerValue / supporterValue == targetValue) {
      return Optional.of(
          new AmbushJustification(
              attackerValue, AmbushOperator.DIVIDE, supporterValue, targetValue));
    }

    if (attackerValue != 0
        && supporterValue % attackerValue == 0
        && supporterValue / attackerValue == targetValue) {
      return Optional.of(
          new AmbushJustification(
              attackerValue, AmbushOperator.DIVIDE_INV, supporterValue, targetValue));
    }

    return Optional.empty();
  }
}
