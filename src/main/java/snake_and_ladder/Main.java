package snake_and_ladder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

class Board {
    private final List<Snake> snakes;
    private final List<Ladder> ladders;
    private static final int SIZE = 100;
    private static volatile Board INSTANCE;

    private Board() {
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        initializeBoard();
    }

    private void initializeBoard() {
        snakes.add(new Snake(16, 6));
        snakes.add(new Snake(47, 26));
        snakes.add(new Snake(49, 11));
        ladders.add(new Ladder(3, 22));
        ladders.add(new Ladder(5, 8));
        ladders.add(new Ladder(15, 97));
    }

    public static Board getInstance() {
        if (INSTANCE == null) {
            synchronized (Board.class) {
                if (INSTANCE == null)
                    INSTANCE = new Board();
            }
        }
        return INSTANCE;
    }

    public int getNewPositon(int currentPosition) {
        for (Snake snake : snakes) {
            if (currentPosition == snake.getHead())
                return snake.getTail();
        }

        for (Ladder ladder : ladders) {
            if (currentPosition == ladder.getBottom())
                return ladder.getTop();
        }

        return currentPosition;
    }

}

interface DiceStrategy {
    int roll();
}

class StandardDice implements DiceStrategy {
    private final Random random = new Random();
    @Override
    public int roll() {
        return random.nextInt(1, 7);
    }
}

class BiasedDice implements DiceStrategy {
    private final Random random = new Random();
    @Override
    public int roll() {
        return new int[]{4,5,6}[random.nextInt(3)];
    }
}

class Player {
    @Getter
    @Setter
    private String name;

    @Setter
    private DiceStrategy diceStrategy;

    @Getter
    @Setter
    private int position;

    public Player(String name, DiceStrategy strategy) {
        this.position = 0;
        this.name = name;
        this.diceStrategy = strategy;
    }

    public int rollDice() {
        return diceStrategy.roll();
    }

    public void move(int newPosition) {
        this.position = newPosition;
        System.out.println("Player " + this.getName() + " moved to " + newPosition);
    }
}

interface Observer {
    void notifyUserTurn(Player player);
    void notifyAllPlayers(List<Player> players);
    void notifyAudience();
}

@AllArgsConstructor
class ConsoleObserver implements  Observer {
    private List<Player> players;

    @Override
    public void notifyUserTurn(Player player) {
        System.out.println(player.getName() + " Your turn!");
    }

    @Override
    public void notifyAllPlayers(List<Player> players) {
    }

    @Override
    public void notifyAudience() {

    }
}

interface Command {
    void execute();
    void undo();
}

class MoveCommand implements Command {
    private Player player;
    private Board board;
    private int diceRoll;
    private int previousPosition;

    public MoveCommand(Player player, Board board, int diceRoll) {
        this.player = player;
        this.board = board;
        this.diceRoll = diceRoll;
    }

    @Override
    public void execute() {
        previousPosition = player.getPosition();
        int newPosition = board.getNewPositon(previousPosition + diceRoll);
        player.move(newPosition);
    }

    @Override
    public void undo() {
        player.setPosition(previousPosition);
    }
}



class Game {
    private final Board board;
    private final List<Player> players;
    private final List<Observer> observers;
    private final Stack<Command> commandHistory;

    public Game(Board board, List<Player> players) {
        this.players = players;
        this.board = board;
        this.observers = new ArrayList<>();
        commandHistory = new Stack<>();
    }

    public void startGame() {
        boolean gameOver = false;

        while (!gameOver) {
            for (Player player : players) {
                int diceRoll = player.rollDice();
                MoveCommand moveCommand = new MoveCommand(player, board, diceRoll);
                moveCommand.execute();
                commandHistory.push(moveCommand);

                if (player.getPosition() >= 100) {
                    System.out.println(player.getName() + " Wins!");
                    gameOver = true;
                    break;
                }
            }
        }
    }

    public void undoLastMove() {
        if (commandHistory.isEmpty()) {
            System.out.println("No moves to undo!");
        } else {
            Command lastCommand = commandHistory.pop();
            lastCommand.undo();
            System.out.println("Undo done");
        }
    }


}


@RequiredArgsConstructor
@Getter
@Setter
class Snake {
    private final int head;
    private final int tail;
}

@RequiredArgsConstructor
@Getter
@Setter
class Ladder {
    private final int bottom;
    private final int top;
}

public class Main {
    public static void main(String[] args) {
        Board board = Board.getInstance();

        Player alice = new Player("Alice", new StandardDice());
        Player bob = new Player("Bob", new BiasedDice());

        Game game = new Game(board, List.of(alice, bob));
        game.startGame();
    }
}
