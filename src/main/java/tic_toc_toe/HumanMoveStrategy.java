package tic_toc_toe;

import java.util.Scanner;

public class HumanMoveStrategy implements MoveStrategy {
    Scanner scanner = new Scanner(System.in);
    @Override
    public int[] getMove(Board board) {
        System.out.println("Enter your move (row and col): ");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        return new int[] {row, col};
    }
}
