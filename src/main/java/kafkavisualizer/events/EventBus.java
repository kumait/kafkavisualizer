package kafkavisualizer.events;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class EventBus {
    private static final Set<EventObserver> observers = new HashSet<>();

    public static void register(EventObserver observer) {
        observers.add(observer);
    }

    public static void removeObserver(EventObserver observer) {
        observers.remove(observer);
    }

    public static void broadcast(Event event, Object payload) {
        for (var observer : observers) {
            try {
                observer.consume(event, payload);
            } catch (Exception ex) {
                Logger.getGlobal().warning(ex.getMessage());
            }
        }
    }

    public static void broadcast(Event event) {
        broadcast(event, null);
    }
}
