package eu.nicosworld.rithmo.engine.testutils.victory;

import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.victory.Victory;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractVictoryAssertion<
    T extends Victory,
    SELF extends AbstractVictoryAssertion<T, SELF>
    > {
    protected final T victory;

    protected AbstractVictoryAssertion(T victory) {
        this.victory = victory;
    }

    protected abstract SELF self();

    public SELF hasWinner(Player expected) {
        assertThat(victory.winner())
            .isEqualTo(expected);

        return self();
    }
}
