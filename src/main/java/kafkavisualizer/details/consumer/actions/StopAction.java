package kafkavisualizer.details.consumer.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.details.consumer.ConsumerDetailsController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class StopAction extends AbstractAction {
    private final ConsumerDetailsController controller;

    public StopAction(ConsumerDetailsController controller) {
        super("Start", new FlatSVGIcon("icons/suspend.svg", 16, 16));
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.stop();
    }
}
