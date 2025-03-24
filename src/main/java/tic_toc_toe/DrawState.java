package tic_toc_toe;

public class DrawState implements GameState {
    @Override
    public void handle(Game game) {
        game.notifyObservers("Game is draw!");
        game.getBoard().display();
    }
}
