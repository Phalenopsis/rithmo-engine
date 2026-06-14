package eu.nicosworld.rithmo.engine.setup;

import eu.nicosworld.rithmo.engine.model.*;
import java.util.ArrayList;
import java.util.List;

public class PyramidBuilder {
  protected final Player player;
  protected final List<SimplePiece> components = new ArrayList<>();

  protected PyramidBuilder(Player player) {
    this.player = player;
  }

  public static PyramidBuilder forPlayer(Player player) {
    return new PyramidBuilder(player);
  }

  public static PyramidBuilder fullBlack() {

    return forPlayer(Player.BLACK)
        .withSquare(36)
        .withSquare(25)
        .withTriangle(16)
        .withTriangle(9)
        .withCircle(4)
        .withCircle(1);
  }

  public static PyramidBuilder fullBoissiereBlack() {

    return forPlayer(Player.BLACK)
        .withSquare(36)
        .withSquare(25)
        .withTriangle(16)
        .withTriangle(9)
        .withCircle(4)
        .withCircle(1);
  }

  public static PyramidBuilder fullWhite() {

    return forPlayer(Player.WHITE)
        .withSquare(64)
        .withSquare(49)
        .withTriangle(36)
        .withTriangle(25)
        .withCircle(16);
  }

  public static PyramidBuilder fullBoissiereWhite() {

    return forPlayer(Player.WHITE)
        .withSquare(64)
        .withSquare(49)
        .withTriangle(36)
        .withTriangle(25)
        .withCircle(16);
  }

  public PyramidBuilder withSquare(int value) {
    return with(PieceType.SQUARE, value);
  }

  public PyramidBuilder withTriangle(int value) {
    return with(PieceType.TRIANGLE, value);
  }

  public PyramidBuilder withCircle(int value) {
    return with(PieceType.CIRCLE, value);
  }

  public PyramidBuilder with(PieceType type, int value) {
    components.add(new SimplePiece(type, player, value));
    return this;
  }

  public Pyramid build() {
    return new Pyramid(player, List.copyOf(components));
  }
}
