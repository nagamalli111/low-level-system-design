package tic_toc_toe;

import java.util.Map;
import java.util.function.BiFunction;

public class PlayerFactory {
    private static  final Map<PlayerType, BiFunction<String, Symbol, Player>> PLAYER_CREATORS =
            Map.of(PlayerType.HUMAN, HumanPlayer::new ,
                    PlayerType.AI, AIPlayer::new);

    public static Player createPlayer(String name, PlayerType type, Symbol symbol) {
        return PLAYER_CREATORS.getOrDefault(type, (n, s) -> {
            throw new IllegalArgumentException("Invalid player type: " + type);
        }).apply(name, symbol);
    }
}
