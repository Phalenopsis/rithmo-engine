package eu.nicosworld.rithmo.engine.testutils.victory;

import eu.nicosworld.rithmo.engine.model.victory.BodyVictory;
import eu.nicosworld.rithmo.engine.model.victory.GoodsVictory;
import eu.nicosworld.rithmo.engine.model.victory.LawsuitVictory;
import eu.nicosworld.rithmo.engine.model.victory.Victory;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class VictoryAssertion {
    private final Optional<Victory> victory;

    private VictoryAssertion(Optional<Victory> victory) {
        this.victory = victory;
    }

    public static VictoryAssertion from(Optional<Victory> victory) {
        return new VictoryAssertion(victory);
    }

    public VictoryAssertion isVictory() {
        assertThat(victory.isPresent()).isTrue();

        return this;
    }

    public void isNotVictory() {
        assertThat(victory.isEmpty()).isTrue();
    }

    public BodyVictoryAssertion isByBody() {
        return new BodyVictoryAssertion(bodyVictory());
    }

    private BodyVictory bodyVictory() {
        assertThat(victory)
            .isPresent()
            .get()
            .isInstanceOf(BodyVictory.class);

        return (BodyVictory) victory.get();
    }

    public GoodsVictoryAssertion isByGoods() {
        return new GoodsVictoryAssertion(goodsVictory());
    }

    private GoodsVictory goodsVictory() {
        assertThat(victory)
            .isPresent()
            .get()
            .isInstanceOf(GoodsVictory.class);

        return (GoodsVictory) victory.get();
    }

    public LawsuitVictoryAssertion isByLawsuit() {
        return new LawsuitVictoryAssertion(lawsuitVictory());
    }

    private LawsuitVictory lawsuitVictory() {
        assertThat(victory)
            .isPresent()
            .get()
            .isInstanceOf(LawsuitVictory.class);

        return (LawsuitVictory) victory.get();
    }
}
