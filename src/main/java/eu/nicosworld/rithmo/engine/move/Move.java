package eu.nicosworld.rithmo.engine.move;

import eu.nicosworld.rithmo.engine.model.Position;

import java.util.Objects;

/**
 * Represents a movement action from one position to another on the board.
 *
 * <p>A move is defined by:
 * <ul>
 *     <li>an origin position ({@code from})</li>
 *     <li>a destination position ({@code to})</li>
 *     <li>a nature describing the movement type</li>
 * </ul>
 *
 * <p>The {@link MoveNature} is used to distinguish between different rule sets
 * (e.g., regular vs irregular movement).</p>
 *
 * <p>This class is immutable and safely usable as a key in sets or maps.
 * Equality and hash code are based on all fields (from, to, nature),
 * ensuring correct deduplication in move aggregation.</p>
 */
public record Move(Position from, Position to, MoveNature nature) {

    /**
     * Returns the nature of this move, indicating which rule set
     * produced it.
     */
    @Override
    public MoveNature nature() {
        return nature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move move)) return false;
        return Objects.equals(from, move.from)
                && Objects.equals(to, move.to)
                && nature == move.nature;
    }

}