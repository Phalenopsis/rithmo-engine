package eu.nicosworld.rithmo.engine.capture.capturerule;

import static java.lang.Math.pow;

import eu.nicosworld.rithmo.engine.capture.justification.PowerJustification;
import eu.nicosworld.rithmo.engine.capture.justification.PowerRelation;
import eu.nicosworld.rithmo.engine.capture.model.*;
import eu.nicosworld.rithmo.engine.threat.model.SoloThreat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implements the Power capture rule.
 *
 * <p>A power capture occurs when the value of the attacking piece (or one of its components) is the
 * square or cube of the target's value, or vice versa. Like the Encounter rule, the attacker must
 * physically reach the target's square.
 */
public final class PowerRule implements CaptureRule {

  /**
   * Finds all potential power captures by checking all reachable positions.
   *
   * @param context The current game state and the actor piece.
   * @return A list of {@link CaptureAction} representing valid power captures.
   */
  @Override
  public List<CaptureAction> findCaptures(CaptureContext context) {
    List<CaptureAction> captures = new ArrayList<>();
    for (SoloThreat soloThreat : context.regularSoloThreat()) {
      resolvePower(soloThreat.getActorValue(), soloThreat.getTargetValue())
          .ifPresent(
              justification ->
                  captures.add(
                      CaptureAction.power(soloThreat.actor(), soloThreat.target(), justification)));
    }
    return captures;
  }

  private Optional<PowerJustification> resolvePower(int attackerValue, int targetValue) {
    for (int exponent = 2; exponent <= 9; exponent++) {

      if (pow(attackerValue, exponent) == targetValue) {
        return Optional.of(
            new PowerJustification(attackerValue, PowerRelation.POWER, exponent, targetValue));
      }

      if (pow(targetValue, exponent) == attackerValue) {
        return Optional.of(
            new PowerJustification(attackerValue, PowerRelation.ROOT, exponent, targetValue));
      }
    }

    return Optional.empty();
  }
}
