package tic_toc_toe;

public class AIPlayer extends Player {
    public AIPlayer(String name, Symbol symbol) {
        super(name, symbol, new AIMoveStrategy());
    }
}
