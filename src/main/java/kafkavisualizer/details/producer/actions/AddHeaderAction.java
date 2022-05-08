package kafkavisualizer.details.producer.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.details.producer.ProducerDetailsController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AddHeaderAction extends AbstractAction {
    private final ProducerDetailsController controller;

    public AddHeaderAction(ProducerDetailsController controller) {
        super("Add Header", new FlatSVGIcon("icons/add.svg", 16, 16));
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.addEmptyHeader();
    }
}
