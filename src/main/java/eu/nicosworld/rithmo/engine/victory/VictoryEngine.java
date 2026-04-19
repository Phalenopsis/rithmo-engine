package eu.nicosworld.rithmo.engine.victory;

import eu.nicosworld.rithmo.engine.model.PlayerColor;

import java.util.Map;
import java.util.Optional;

public class VictoryEngine {

    /**
     * Checks if a player has reached the body victory condition.
     *
     * @return winning player if any, otherwise empty
     */
    public Optional<PlayerColor> checkWinner(
            VictoryState state,
            BodyVictoryCondition condition
    ) {

        for (Map.Entry<PlayerColor, Integer> entry : state.captures().entrySet()) {

            if (entry.getValue() >= condition.targetCaptures()) {
                return Optional.of(entry.getKey());
            }
        }

        return Optional.empty();
    }
}