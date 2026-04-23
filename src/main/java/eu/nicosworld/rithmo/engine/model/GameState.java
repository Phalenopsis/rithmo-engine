package eu.nicosworld.rithmo.engine.model;

import java.util.EnumMap;
import java.util.Map;

/**
 * Represents the complete state of a Rithmomachie game at a given point in time.
 *
 * <p>This object is immutable and acts as a snapshot of the game.
 * Any modification (board update, asset change, player switch, etc.)
 * results in the creation of a new {@code GameState} instance.</p>
 *
 * <p>A game state contains:
 * <ul>
 *     <li>The current {@link Board}</li>
 *     <li>The {@link Player} whose turn it is</li>
 *     <li>The off-board assets of each player ({@link PlayerAssets})</li>
 * </ul>
 * </p>
 *
 * <p>The {@code assets} map is keyed by {@link PlayerColor} to avoid
 * unnecessary coupling with {@link Player} instances and to ensure
 * stable, efficient lookups.</p>
 *
 * <p>This class provides helper methods to facilitate immutable updates
 * (e.g., {@code withBoard}, {@code withAssets}, {@code switchPlayer}).</p>
 */
public record GameState(
        Board board,
        Player currentPlayer,
        Map<PlayerColor, PlayerAssets> assets
) {

    /**
     * Canonical constructor.
     *
     * <p>Ensures the internal assets map is immutable.</p>
     *
     * @param board          the current board state
     * @param currentPlayer  the player whose turn it is
     * @param assets         the assets of each player
     */
    public GameState {
        assets = Map.copyOf(assets);
    }

    // =========================
    // FACTORY
    // =========================

    /**
     * Creates an initial game state with empty assets for all players.
     *
     * <p>Each player starts with no captured pieces and no reserve.</p>
     *
     * @param board          the initial board
     * @param currentPlayer  the starting player
     * @return a fully initialized {@code GameState}
     */
    public static GameState initial(Board board, Player currentPlayer) {

        Map<PlayerColor, PlayerAssets> assets =
                new EnumMap<>(PlayerColor.class);

        for (PlayerColor color : PlayerColor.values()) {
            assets.put(color, PlayerAssets.empty());
        }

        return new GameState(board, currentPlayer, assets);
    }

    // =========================
    // ACCESS HELPERS
    // =========================

    /**
     * Returns the assets associated with the given player.
     *
     * @param player the player
     * @return the player's assets
     */
    public PlayerAssets assetsOf(Player player) {
        return assets.get(player.getColor());
    }

    /**
     * Returns the assets associated with the given player color.
     *
     * @param color the player color
     * @return the corresponding assets
     */
    public PlayerAssets assetsOf(PlayerColor color) {
        return assets.get(color);
    }

    /**
     * Returns the assets associated with the active player.
     *
     * @return the corresponding assets
     */
    public PlayerAssets assetsOfCurrentPlayer() {
        return assetsOf(currentPlayer);
    }

    // =========================
    // IMMUTABLE TRANSFORMATIONS
    // =========================

    /**
     * Returns a new {@code GameState} with updated assets for a given player.
     *
     * @param color      the player color
     * @param newAssets  the new assets
     * @return a new game state with updated assets
     */
    public GameState withAssets(PlayerColor color, PlayerAssets newAssets) {

        Map<PlayerColor, PlayerAssets> newMap =
                new EnumMap<>(assets);

        newMap.put(color, newAssets);

        return new GameState(board, currentPlayer, newMap);
    }

    /**
     * Returns a new {@code GameState} with an updated board.
     *
     * @param newBoard the new board
     * @return a new game state with the updated board
     */
    public GameState withBoard(Board newBoard) {
        return new GameState(newBoard, currentPlayer, assets);
    }

    /**
     * Returns a new {@code GameState} with the current player switched
     * to their opponent.
     *
     * @return a new game state with the next player
     */
    public GameState switchPlayer() {
        return new GameState(board, currentPlayer.opponent(), assets);
    }
}