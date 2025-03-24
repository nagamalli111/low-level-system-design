package tic_toc_toe;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WinState implements GameState {
    private Player winner;
    @Override
    public void handle(Game game) {
        game.notifyObservers(winner.getName() + "Wins!");
        game.getBoard().display();
    }
}
