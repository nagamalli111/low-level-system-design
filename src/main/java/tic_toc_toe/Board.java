package tic_toc_toe;

public class Board {
    private final Symbol[][] grid = new Symbol[3][3];

    public boolean makeMove(int row, int col, Symbol symbol) {
        if (!isCellEmpty(row, col))
            return false;

        grid[row][col] = symbol;
        return true;
    }

    public boolean isCellEmpty(int row, int col) {
        return grid[row][col] == null;
    }

    public boolean checkWin(Symbol symbol) {
        for (int i = 0; i < 3; i++) {
            if ((grid[i][0] == symbol && grid[i][1] == symbol && grid[i][2] == symbol) ||
                    (grid[0][i] == symbol && grid[1][i] == symbol && grid[2][i] == symbol)) {
                return true;
            }
        }
        return (grid[0][0] == symbol && grid[1][1] == symbol && grid[2][2] == symbol) ||
                (grid[0][2] == symbol && grid[1][1] == symbol && grid[2][0] == symbol);
    }

    public boolean isFull() {
        for (Symbol[] row: grid) {
            for (Symbol cell : row)
                if (cell == null)
                    return false;
        }

        return true;
    }

    public  void display() {
        for (Symbol[] row : grid) {
            for (Symbol cell : row) {
                System.out.print(cell == null ? "-" : cell);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
