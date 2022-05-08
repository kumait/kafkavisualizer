package kafkavisualizer.details.topic.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.Utils;
import kafkavisualizer.details.topic.TopicsDetailsController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class DeleteTopicAction extends AbstractAction {
    private final TopicsDetailsController controller;

    public DeleteTopicAction(TopicsDetailsController controller) {
        super("Delete Topic", new FlatSVGIcon("icons/delete.svg", 16, 16));
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Utils.showConfirmation(controller.getTopicsPane().getTopLevelAncestor(), "Are you sure you want to delete the selected topics?")) {
            controller.deleteSelectedTopics();
        }
    }
}
