package kafkavisualizer.events;

public interface EventObserver {
    void consume(Event event, Object payload);
}
