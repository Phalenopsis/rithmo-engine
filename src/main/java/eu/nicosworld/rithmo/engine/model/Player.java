package eu.nicosworld.rithmo.engine.model;

/**
 * Represents a player in the Rithmomachie game.
 *
 * <p>This class is implemented as a <b>singleton</b> with exactly two instances: {@link #BLACK} and
 * {@link #WHITE}. No other instances can be created.
 *
 * <p>A {@code Player} is identified solely by its {@link PlayerColor}. Since instances are unique,
 * identity comparison (using {@code ==}) is sufficient and preferred over {@code equals()}.
 *
 * <p>This design ensures:
 *
 * <ul>
 *   <li>Consistency across the engine (no duplicate player instances)
 *   <li>Efficient comparisons using reference equality
 *   <li>Clear separation between identity ({@code Player}) and value ({@code PlayerColor})
 * </ul>
 *
 * <p>Typical usage:
 *
 * <pre>{@code
 * Player current = Player.BLACK;
 * Player opponent = current.opponent();
 * }</pre>
 */
public final class Player {

  /** Singleton instance representing the black player. */
  public static final Player BLACK = new Player(PlayerColor.BLACK);

  /** Singleton instance representing the white player. */
  public static final Player WHITE = new Player(PlayerColor.WHITE);

  private final PlayerColor color;

  /**
   * Private constructor to enforce singleton instances.
   *
   * @param color the color associated with this player
   */
  private Player(PlayerColor color) {
    this.color = color;
  }

  /**
   * Returns the color of this player.
   *
   * @return the player's color
   */
  public PlayerColor getColor() {
    return color;
  }

  /**
   * Returns the opponent of this player.
   *
   * <p>This method relies on instance identity and always returns the other singleton instance.
   *
   * @return the opposing player
   */
  public Player opponent() {
    return this == BLACK ? WHITE : BLACK;
  }

  /**
   * Returns the singleton {@code Player} instance corresponding to the given color.
   *
   * @param color the player color
   * @return {@link #BLACK} if color is BLACK, otherwise {@link #WHITE}
   */
  public static Player of(PlayerColor color) {
    return color == PlayerColor.BLACK ? BLACK : WHITE;
  }

  @Override
  public String toString() {
    return "Player " + color;
  }
}
