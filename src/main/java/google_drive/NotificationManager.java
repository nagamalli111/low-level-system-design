package google_drive;

import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private List<Observer> observers = new ArrayList<>();

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unSubscribe(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.send(message);
        }
    }
}
