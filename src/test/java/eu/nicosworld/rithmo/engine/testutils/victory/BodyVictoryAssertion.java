package eu.nicosworld.rithmo.engine.testutils.victory;

import eu.nicosworld.rithmo.engine.model.victory.BodyVictory;

import static org.assertj.core.api.Assertions.assertThat;

public class BodyVictoryAssertion extends AbstractVictoryAssertion<BodyVictory, BodyVictoryAssertion> {

    public BodyVictoryAssertion(BodyVictory victory) {
        super(victory);
    }

    public BodyVictoryAssertion hasCapturedCount(int expectedCount) {
        assertThat(victory.capturedCount()).isEqualTo(expectedCount);
        return this;
    }

    public BodyVictoryAssertion hasRequiredCount(int expectedTarget) {
        assertThat(victory.requiredCount()).isEqualTo(expectedTarget);
        return this;
    }

    @Override
    protected BodyVictoryAssertion self() {
        return this;
    }
}
