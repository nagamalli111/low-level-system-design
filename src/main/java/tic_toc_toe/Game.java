package tic_toc_toe;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Game {
    @Getter
    private final Board board;
    private final Player player1;
    private final Player player2;
    @Getter
    private Player currentPlayer;
    private final List<GameObserver> observers = new ArrayList<>();
    @Setter
    private GameState state;


    public Game(Player player1, Player player2) {
        board = new Board();
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        state = new PlayingState();
    }

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (GameObserver observer : observers) {
            observer.update(message);
        }
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public void start() {
        while (!(state instanceof WinState || state instanceof DrawState))
            state.handle(this);

        state.handle(this);
    }
}
