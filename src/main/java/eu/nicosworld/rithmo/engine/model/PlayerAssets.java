package eu.nicosworld.rithmo.engine.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents all off-board assets owned by a player.
 *
 * <p>This includes:
 * <ul>
 *     <li>Captured pieces (history / scoring)</li>
 *     <li>Reserve pieces (reusable in game)</li>
 * </ul>
 *
 * <p>This object is immutable. All mutation operations return a new instance.</p>
 */
public record PlayerAssets(
        List<Piece> captured,
        List<Piece> reserve
) {

    public PlayerAssets {
        captured = List.copyOf(captured);
        reserve = List.copyOf(reserve);
    }

    // =========================
    // FACTORY
    // =========================

    public static PlayerAssets empty() {
        return new PlayerAssets(List.of(), List.of());
    }

    // =========================
    // GETTERS (SAFE)
    // =========================

    public List<Piece> captured() {
        return captured;
    }

    public List<Piece> reserve() {
        return reserve;
    }

    // =========================
    // CAPTURED LOGIC
    // =========================

    public PlayerAssets addCaptured(Piece piece) {
        Objects.requireNonNull(piece);

        List<Piece> newCaptured = new ArrayList<>(captured);
        newCaptured.add(piece);

        return new PlayerAssets(newCaptured, reserve);
    }

    public int capturedCount() {
        return captured.size();
    }

    public int capturedValue() {
        return captured.stream()
                .mapToInt(Piece::getValue)
                .sum();
    }

    // =========================
    // RESERVE LOGIC
    // =========================

    public PlayerAssets addToReserve(Piece piece) {
        Objects.requireNonNull(piece);
        if (piece instanceof Pyramid) {
            throw new IllegalArgumentException("A Pyramid cannot be added to the reserve.");
        }

        List<Piece> newReserve = new ArrayList<>(reserve);
        newReserve.add(new SimplePiece(piece.getType(), piece.getPlayer().opponent(), piece.getValue()));

        return new PlayerAssets(captured, newReserve);
    }

    public PlayerAssets removeFromReserve(Piece piece) {
        Objects.requireNonNull(piece);

        List<Piece> newReserve = new ArrayList<>(reserve);
        newReserve.remove(piece);

        return new PlayerAssets(captured, newReserve);
    }

    public boolean hasInReserve(Piece piece) {
        return reserve.contains(piece);
    }

    public int reserveCount() {
        return reserve.size();
    }

    // =========================
    // COMBINED OPERATIONS
    // =========================

    /**
     * Adds a captured piece and also puts it into reserve.
     * Useful if your rules allow immediate reuse.
     */
    public PlayerAssets captureAndStore(Piece piece) {
        return this.addCaptured(piece)
                .addToReserve(piece);
    }

    // =========================
    // DEBUG
    // =========================

    @Override
    public String toString() {
        return "PlayerAssets{" +
                "captured=" + captured +
                ", reserve=" + reserve +
                '}';
    }
}