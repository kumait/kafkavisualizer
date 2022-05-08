package kafkavisualizer.details.consumers;

import kafkavisualizer.navigator.actions.NewConsumerAction;

public class ConsumersDetailsController {
    private final ConsumersDetailsPane pane;

    public ConsumersDetailsController() {
        pane = new ConsumersDetailsPane();
        pane.getNewConsumerButton().setAction(new NewConsumerAction());
    }

    public ConsumersDetailsPane getPane() {
        return pane;
    }
}
