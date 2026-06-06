package eu.nicosworld.rithmo.engine.model;

public class SimplePiece extends Piece {
  private final int value;

  public SimplePiece(PieceType type, Player player, int value) {
    super(type, player);
    this.value = value;
  }

  private SimplePiece(PieceType type, Player player, PieceState state, String id, int value) {
    super(type, player, state, id);
    this.value = value;
  }

  @Override
  public int getValue() {
    return value;
  }

  @Override
  protected Piece copyWithState(PieceState state) {
    return new SimplePiece(getType(), getPlayer(), state, getId(), value);
  }
}
