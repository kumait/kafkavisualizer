package kafkavisualizer.details.producers;

import kafkavisualizer.navigator.actions.NewProducerAction;

public class ProducersDetailsController {
    private final ProducersDetailsPane pane;

    public ProducersDetailsController() {
        pane = new ProducersDetailsPane();
        pane.getNewProducerButton().setAction(new NewProducerAction());
    }

    public ProducersDetailsPane getPane() {
        return pane;
    }
}
