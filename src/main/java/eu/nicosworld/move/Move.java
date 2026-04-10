package eu.nicosworld.move;

import eu.nicosworld.model.Position;

import java.util.Objects;

public class Move {
    private Position from;
    private Position to;

    private MoveNature nature;

    public Move(Position from, Position to, MoveNature nature) {
        this.from = from;
        this.to = to;
        this.nature = nature;
    }

    public Position getTo() {
        return to;
    }

    public Position getFrom() {
        return from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move move)) return false;
        return Objects.equals(from, move.from) && Objects.equals(to, move.to) && nature == move.nature;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, nature);
    }
}
