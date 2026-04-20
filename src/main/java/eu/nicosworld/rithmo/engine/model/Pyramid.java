package eu.nicosworld.rithmo.engine.model;

import java.util.ArrayList;
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

    public Pyramid(Player player, List<SimplePiece> components, PieceState state, String id) {
        super(PieceType.PYRAMID, player, state, id);
        this.components = components;
    }

    public int getValue() {
        return components.stream()
                .mapToInt(SimplePiece::getValue)
                .sum();
    }

    @Override
    protected Piece copyWithState(PieceState state) {
        return new Pyramid(getPlayer(), components, state, getId());
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

    public Pyramid removeComponent(SimplePiece capturedComponent) {
        ArrayList<SimplePiece> newComponents = new ArrayList<>(components);
        newComponents.remove(capturedComponent);
        return new Pyramid(getPlayer(), newComponents, getState(), getId());
    }

    public Pyramid removeComponent(Piece capturedComponent) {
        return removeComponent((SimplePiece) capturedComponent);
    }
}
