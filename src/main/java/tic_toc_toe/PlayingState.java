package tic_toc_toe;

public class PlayingState implements GameState {
    @Override
    public void handle(Game game) {
        Board board = game.getBoard();
        board.display();

        Player currentPlayer = game.getCurrentPlayer();
        int[] move = currentPlayer.makeMove(board);
        if (board.makeMove(move[0], move[1], currentPlayer.getSymbol())) {
            if (board.checkWin(currentPlayer.getSymbol()))
                game.setState(new WinState(currentPlayer));
            else if (board.isFull())
                game.setState(new DrawState());
            else
                game.switchTurn();
        } else {
            game.notifyObservers("Invalid move try again");
        }
    }
}
