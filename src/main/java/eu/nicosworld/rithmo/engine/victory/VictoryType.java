package eu.nicosworld.rithmo.engine.victory;

public enum VictoryType {
    BODY, LAWSUIT, GOODS;

    public static VictoryType from(VictoryRule rule) {
        return switch (rule) {
            case GoodsVictoryRule ignored -> GOODS;
            case LawsuitVictoryRule ignored -> LAWSUIT;
            case BodyVictoryRule ignored -> BODY;
        };
    }
}
