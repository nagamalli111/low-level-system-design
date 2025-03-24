package tic_toc_toe;

public class HumanPlayer extends Player {
    public HumanPlayer(String name, Symbol symbol) {
        super(name, symbol, new HumanMoveStrategy());
    }
}
