package eu.nicosworld.model;

import eu.nicosworld.move.Move;

import java.util.List;

public class GameState {
    private final Board board;
    private final Player currentPlayer;

    int turnNumber;
    List<Move> history;

    public GameState(Board board, Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    public Board getBoard() { return board; }
    public Player getCurrentPlayer() { return currentPlayer; }
}
