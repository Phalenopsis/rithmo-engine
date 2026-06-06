package eu.nicosworld.rithmo.engine.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Base class representing a game piece in Rithmomachie. *
 *
 * <p>Each piece is uniquely identified by an ID generated at instantiation, ensuring that pieces
 * with identical types and values are treated as distinct entities.
 */
public abstract class Piece {
  private final PieceType type;
  private final Player owner;
  private final PieceState state;
  private final String id;

  /**
   * Constructs a piece with a default state of {@link PieceState#IN_GAME}.
   *
   * @param type the geometric type of the piece
   * @param player the owner of the piece
   */
  protected Piece(PieceType type, Player player) {
    this(type, player, PieceState.IN_GAME);
  }

  /**
   * Constructs a piece with a specific state and generates a unique identifier.
   *
   * @param type the geometric type of the piece
   * @param player the owner of the piece
   * @param state the current status of the piece
   */
  protected Piece(PieceType type, Player player, PieceState state) {
    this(type, player, state, UUID.randomUUID().toString());
  }

  protected Piece(PieceType type, Player player, PieceState state, String id) {
    this.type = type;
    this.owner = player;
    this.state = state;
    this.id = id;
  }

  /**
   * @return the arithmetic value of the piece
   */
  public abstract int getValue();

  public Player getPlayer() {
    return owner;
  }

  public PieceType getType() {
    return type;
  }

  public PieceState getState() {
    return state;
  }

  public Piece withState(PieceState state) {
    return copyWithState(state);
  }

  protected abstract Piece copyWithState(PieceState state);

  public String getId() {
    return id;
  }

  public boolean isPyramid() {
    return this instanceof Pyramid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Piece piece)) return false;
    return Objects.equals(id, piece.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return String.format("%s %s (%d)", type, owner.getColor(), getValue());
  }
}
