package eu.nicosworld.rithmoEngine.model;

import java.util.List;

public class Pyramid extends Piece{
    private final List<SimplePiece> components;

    public Pyramid(PieceType type, Player player, List<SimplePiece> components) {
        super(type, player);
        this.components = components;
    }

    public Pyramid(Player player, List<SimplePiece> components) {
        super(PieceType.PYRAMID, player);
        this.components = components;
    }

    public int getValue() {
        return components.stream()
                .mapToInt(SimplePiece::getValue)
                .sum();
    }

    public boolean hasComponent(PieceType type) {
        return components.stream()
                .anyMatch(p -> p.getType() == type);
    }

    public boolean hasCircle() {
        return hasComponent(PieceType.CIRCLE);
    }

    public boolean hasTriangle() {
        return hasComponent(PieceType.TRIANGLE);
    }

    public boolean hasSquare() {
        return hasComponent(PieceType.SQUARE);
    }

    public List<SimplePiece> getComponents() {
        return components;
    }
}
