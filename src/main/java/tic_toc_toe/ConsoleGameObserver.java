package tic_toc_toe;

public class ConsoleGameObserver implements GameObserver {
    @Override
    public void update(String message) {
        System.out.println(message);
    }
}
