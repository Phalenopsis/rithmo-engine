package eu.nicosworld.rithmo.engine.testutils;

import eu.nicosworld.rithmo.engine.model.*;

import static org.assertj.core.api.Assertions.assertThat;

public class GameStateAssertion {
    private final GameState gameState;
    private Player player;
    private Piece piece = null;

    private GameStateAssertion(GameState gameState) {
        this.gameState = gameState;
    }

    public static GameStateAssertion assertThis(GameState gameState) {
        return new GameStateAssertion(gameState);
    }

    public GameStateAssertion player(Player player) {
        this.piece = null;
        this.player = player;
        return this;
    }

    public GameStateAssertion hasCapturedEquivalentInReserve(Piece capturedPiece) {
        Player expectedOwner = capturedPiece.getPlayer().opponent();

        boolean found = this.gameState.assetsOf(this.player)
                .reserve()
                .stream()
                .anyMatch(piece ->
                        piece.getType() == capturedPiece.getType()
                                && piece.getValue() == capturedPiece.getValue()
                                && piece.getPlayer() == expectedOwner
                );

        assertThat(found)
                .as(
                        "Reserve of %s should contain converted equivalent of %s",
                        this.player,
                        capturedPiece
                )
                .isTrue();

        return this;
    }

    public GameStateAssertion doesNotHaveCapturedEquivalentInReserve(Piece capturedPiece) {

        Player expectedOwner = capturedPiece.getPlayer().opponent();

        boolean found = this.gameState.assetsOf(this.player)
                .reserve()
                .stream()
                .anyMatch(piece ->
                        piece.getType() == capturedPiece.getType()
                                && piece.getValue() == capturedPiece.getValue()
                                && piece.getPlayer() == expectedOwner
                );

        assertThat(found)
                .as(
                        "Reserve of %s should not contain converted equivalent of %s",
                        this.player,
                        capturedPiece
                )
                .isFalse();

        return this;
    }

    public GameStateAssertion hasNotInReserve(Piece piece) {
        assertThat(gameState.assetsOf(player).reserve())
                .as("Réserve de %s", player)
                .doesNotContain(piece);
        return this;
    }

    public GameStateAssertion hasEmptyReserve() {
        assertThat(gameState.assetsOf(player).reserve()).isEmpty();
        return this;
    }

    public GameStateAssertion hasOnBoard(Piece piece) {
        this.piece = piece;
        // Optionnel : vérifier que la pièce appartient bien au joueur
        assertThat(piece.getPlayer()).isEqualTo(player);
        return this;
    }

    public GameStateAssertion at(Position position) {
        if (piece == null) {
            throw new IllegalStateException("Appelez hasOnBoard() avant at()");
        }

        assertThat(gameState.board().getPieceAt(position))
                .as("Position %s sur le plateau", position)
                .isNotNull()
                .isEqualTo(piece);
        return this;
    }

    public GameStateAssertion isNotAt(Position position) {
        assertThat(gameState.board().getPieceAt(position))
                .as("La position %s devrait être vide", position)
                .isNotEqualTo(piece);
        return this;
    }

    public GameStateAssertion isEmpty(Position position) {
        assertThat(gameState.board().getPieceAt(position))
                .isNull();
        return this;
    }
}
