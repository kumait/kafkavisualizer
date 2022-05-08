package kafkavisualizer.details.producer.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.details.producer.ProducerDetailsController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RemoveHeaderAction extends AbstractAction {
    private final ProducerDetailsController controller;

    public RemoveHeaderAction(ProducerDetailsController controller) {
        super("Remove Header", new FlatSVGIcon("icons/remove.svg", 16, 16));
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.removeSelectedHeaders();
    }
}
