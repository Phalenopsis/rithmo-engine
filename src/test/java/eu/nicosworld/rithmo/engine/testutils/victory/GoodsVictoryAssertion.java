package eu.nicosworld.rithmo.engine.testutils.victory;

import static org.assertj.core.api.Assertions.assertThat;

import eu.nicosworld.rithmo.engine.model.Player;
import eu.nicosworld.rithmo.engine.model.victory.GoodsVictory;

public class GoodsVictoryAssertion
    extends AbstractVictoryAssertion<GoodsVictory, GoodsVictoryAssertion> {

  public GoodsVictoryAssertion(GoodsVictory goodsVictory) {
    super(goodsVictory);
  }

  @Override
  protected GoodsVictoryAssertion self() {
    return this;
  }

  public GoodsVictoryAssertion hasCapturedValue(int expected) {
    assertThat(victory.capturedValue()).isEqualTo(expected);

    return this;
  }

  public GoodsVictoryAssertion hasRequiredValue(int expected) {
    assertThat(victory.requiredValue()).isEqualTo(expected);

    return this;
  }

  public GoodsVictoryAssertion hasVictorious(Player player) {
    assertThat(victory.winner()).isEqualTo(player);

    return this;
  }
}
