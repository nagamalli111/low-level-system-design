package tic_toc_toe;

public class Demo {
    public static void main(String[] args) {
        Board board = new Board();
        Player p1 = new HumanPlayer("Matty", Symbol.X);
        Player ai = new AIPlayer("Open AI", Symbol.O);

        Game game = new Game(p1, ai);
        game.addObserver(new ConsoleGameObserver());
        game.start();
    }
}
