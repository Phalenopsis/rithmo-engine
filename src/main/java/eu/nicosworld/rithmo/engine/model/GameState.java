package eu.nicosworld.rithmo.engine.model;

import java.util.EnumMap;
import java.util.Map;

public record GameState(
        Board board,
        Player currentPlayer,
        Map<PlayerColor, PlayerAssets> assets
) {

    public GameState {
        assets = Map.copyOf(assets);
    }

    // =========================
    // FACTORY
    // =========================

    public static GameState initial(Board board, Player currentPlayer) {

        Map<PlayerColor, PlayerAssets> assets =
                new EnumMap<>(PlayerColor.class);

        for (PlayerColor color : PlayerColor.values()) {
            assets.put(color, PlayerAssets.empty());
        }

        return new GameState(board, currentPlayer, assets);
    }

    // =========================
    // HELPERS
    // =========================

    public PlayerAssets assetsOf(Player player) {
        return assets.get(player.getColor());
    }

    public PlayerAssets assetsOf(PlayerColor color) {
        return assets.get(color);
    }

    public GameState withAssets(PlayerColor color, PlayerAssets newAssets) {

        Map<PlayerColor, PlayerAssets> newMap =
                new EnumMap<>(assets);

        newMap.put(color, newAssets);

        return new GameState(board, currentPlayer, newMap);
    }

    public GameState withBoard(Board newBoard) {
        return new GameState(newBoard, currentPlayer, assets);
    }

    public GameState switchPlayer() {
        return new GameState(board, currentPlayer.opponent(), assets);
    }
}