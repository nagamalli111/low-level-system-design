package tic_toc_toe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public abstract class Player {
    @Getter
    protected String name;
    @Getter
    protected Symbol symbol;
    @Setter
    protected MoveStrategy moveStrategy;

    public int[] makeMove(Board board) {
        return moveStrategy.getMove(board);
    }
}
