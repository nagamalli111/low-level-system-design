package tic_toc_toe;

import java.util.Random;

public class AIMoveStrategy implements MoveStrategy {
    private final Random random = new Random();
    @Override
    public int[] getMove(Board board) {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!board.isCellEmpty(row, col));

        return new int[]{row, col};
    }
}
