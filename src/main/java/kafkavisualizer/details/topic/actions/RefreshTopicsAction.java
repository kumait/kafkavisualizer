package kafkavisualizer.details.topic.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.details.topic.TopicsDetailsController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RefreshTopicsAction extends AbstractAction {
    private final TopicsDetailsController controller;

    public RefreshTopicsAction(TopicsDetailsController controller) {
        super("Refresh Topics", new FlatSVGIcon("icons/refresh.svg", 16, 16));
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.loadTopics();
    }
}
