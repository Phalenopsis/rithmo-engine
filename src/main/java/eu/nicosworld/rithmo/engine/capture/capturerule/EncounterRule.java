package eu.nicosworld.rithmo.engine.capture.capturerule;

import eu.nicosworld.rithmo.engine.capture.justification.EncounterJustification;
import eu.nicosworld.rithmo.engine.capture.model.*;
import eu.nicosworld.rithmo.engine.threat.model.SoloThreat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implements the Encounter (Rencontre) capture rule.
 *
 * <p>An encounter occurs when an attacking piece moves to a square occupied by an enemy piece (via
 * its regular movement) and their values match. Unlike Assault, an encounter requires the attacker
 * to physically reach the target's square.
 */
public final class EncounterRule implements CaptureRule {

  /**
   * Finds all potential encounter captures by checking all reachable positions.
   *
   * @param context The current game state and the actor piece.
   * @return A list of {@link CaptureAction} representing valid encounters.
   */
  @Override
  public List<CaptureAction> findCaptures(CaptureContext context) {
    List<CaptureAction> captures = new ArrayList<>();
    for (SoloThreat soloThreat : context.regularSoloThreat()) {
      resolveEncounter(
              soloThreat.actor().specificComponent().getValue(),
              soloThreat.target().specificComponent().getValue())
          .ifPresent(
              justification ->
                  captures.add(
                      CaptureAction.encounter(
                          soloThreat.actor(), soloThreat.target(), justification)));
    }
    return captures;
  }

  private Optional<EncounterJustification> resolveEncounter(int actorValue, int targetValue) {
    if (actorValue == targetValue) {
      return Optional.of(new EncounterJustification(actorValue));
    }

    return Optional.empty();
  }
}
