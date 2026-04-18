package eu.nicosworld.rithmoEngine.setup;

import eu.nicosworld.rithmoEngine.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Fluent builder used to create board configurations for tests or scenarios.
 *
 * <p>This builder provides a DSL-like API to simplify board setup:
 * <ul>
 *     <li>Create pieces using high-level methods (piece type, value, color)</li>
 *     <li>Attach components to composite pieces (e.g., pyramids)</li>
 *     <li>Place pieces on the board</li>
 * </ul>
 *
 * <p>It is designed for testing and setup purposes only, not runtime gameplay.</p>
 */
public class BoardBuilder {

    private Board board = new Board();

    private final Player white = new Player(PlayerColor.WHITE);
    private final Player black = new Player(PlayerColor.BLACK);

    private Piece currentPiece;
    private List<SimplePiece> currentComponents = new ArrayList<>();

    /**
     * Creates a new piece using high-level game rules.
     *
     * <p>If the piece is a pyramid, a composite structure is initialized
     * and components can be added using {@link #withComponent(PieceType, int)}.</p>
     *
     * @param type  the type of piece to create
     * @param value arithmetic value of the piece
     * @param color owner color
     * @return this builder instance
     */
    public BoardBuilder piece(PieceType type, int value, PlayerColor color) {

        Player owner = color.equals(PlayerColor.BLACK) ? black : white;

        // Special case: composite piece (Pyramid)
        if (type == PieceType.PYRAMID) {
            currentPiece = new Pyramid(owner, new ArrayList<>());
            currentComponents = new ArrayList<>();
        } else {
            currentPiece = new SimplePiece(type, owner, value);
        }

        return this;
    }

    public BoardBuilder(int width, int height) {
        this.board = new Board(width, height);
    }

    public BoardBuilder() {
        this.board = new Board();
    }

    /**
     * Directly injects a fully constructed piece into the builder.
     *
     * <p>This method bypasses internal creation logic and should only be used
     * for advanced test setups or special cases.</p>
     *
     * @param piece the pre-built piece
     * @return this builder instance
     */
    public BoardBuilder withPiece(Piece piece) {
        this.currentPiece = Objects.requireNonNull(piece);
        this.currentComponents = new ArrayList<>();
        return this;
    }

    /**
     * Adds a component to a composite piece (only valid for pyramids).
     *
     * @param type  type of the component piece
     * @param value arithmetic value of the component
     * @return this builder instance
     * @throws IllegalStateException if current piece is not a pyramid
     */
    public BoardBuilder withComponent(PieceType type, int value) {

        if (!(currentPiece instanceof Pyramid)) {
            throw new IllegalStateException("Cannot add components to a non-pyramid piece");
        }

        currentComponents.add(
                new SimplePiece(type, currentPiece.getPlayer(), value)
        );

        return this;
    }

    // =========================
    // CONVENIENCE METHODS
    // =========================

    /** Creates a black circle piece. */
    public BoardBuilder blackCircle(int value) {
        return piece(PieceType.CIRCLE, value, PlayerColor.BLACK);
    }

    /** Creates a white circle piece. */
    public BoardBuilder whiteCircle(int value) {
        return piece(PieceType.CIRCLE, value, PlayerColor.WHITE);
    }

    /** Creates a black triangle piece. */
    public BoardBuilder blackTriangle(int value) {
        return piece(PieceType.TRIANGLE, value, PlayerColor.BLACK);
    }

    /** Creates a white triangle piece. */
    public BoardBuilder whiteTriangle(int value) {
        return piece(PieceType.TRIANGLE, value, PlayerColor.WHITE);
    }

    /** Creates a black square piece. */
    public BoardBuilder blackSquare(int value) {
        return piece(PieceType.SQUARE, value, PlayerColor.BLACK);
    }

    /** Creates a white square piece. */
    public BoardBuilder whiteSquare(int value) {
        return piece(PieceType.SQUARE, value, PlayerColor.WHITE);
    }

    // =========================
    // PLACEMENT
    // =========================

    /**
     * Places the currently built piece at a given position.
     *
     * <p>If the piece is a pyramid, its components are finalized before placement.</p>
     *
     * @param x column index
     * @param y row index
     * @return this builder instance
     */
    public BoardBuilder at(int x, int y) {

        // Finalize composite structure if needed
        if (currentPiece instanceof Pyramid) {
            currentPiece = new Pyramid(
                    currentPiece.getPlayer(),
                    new ArrayList<>(currentComponents)
            );
        }

        board = board.addPiece(currentPiece, new Position(x, y));
        return this;
    }

    // =========================
    // BUILD
    // =========================

    /**
     * Builds the final immutable board instance.
     *
     * @return a copy of the constructed board
     */
    public Board build() {
        return board.copy();
    }
}