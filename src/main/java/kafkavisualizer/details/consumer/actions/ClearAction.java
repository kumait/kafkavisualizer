package kafkavisualizer.details.consumer.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.details.consumer.ConsumerDetailsController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ClearAction extends AbstractAction {
    private final ConsumerDetailsController controller;

    public ClearAction(ConsumerDetailsController controller) {
        super("Clear", new FlatSVGIcon("icons/delete.svg", 16, 16));
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.clear();
    }
}
