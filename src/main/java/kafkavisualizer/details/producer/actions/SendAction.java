package kafkavisualizer.details.producer.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.details.producer.ProducerDetailsController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SendAction extends AbstractAction {
    private final ProducerDetailsController controller;

    public SendAction(ProducerDetailsController controller) {
        super("Send", new FlatSVGIcon("icons/send.svg", 16, 16));
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.send();
    }
}
