package kafkavisualizer.navigator.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.App;
import kafkavisualizer.KafkaClient;
import kafkavisualizer.Utils;
import kafkavisualizer.dialog.DialogController;
import kafkavisualizer.navigator.ProducerPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewProducerAction extends AbstractAction {

    public NewProducerAction() {
        super("New Producer", new FlatSVGIcon("icons/right.svg"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var controller = App.getAppController().getNavigatorController();
        var producerPane = new ProducerPane();
        var dialogController = new DialogController(App.contentPane(), producerPane, "New Producer");

        new Thread(() -> {
            try {
                var topics = KafkaClient.getTopics(controller.getSelectedCluster().getServers());
                topics.forEach(t -> producerPane.getTopicComboBox().addItem(t.getName()));
            } catch (Exception ex) {
                Utils.showError(controller.getNavigatorPane().getTopLevelAncestor(), ex.getMessage());
            }
        }).start();

        dialogController.addOKAction(e1 -> {
            var name = producerPane.getNameTextField().getText();
            var topic = producerPane.getTopicComboBox().getSelectedItem();

            if (topic == null) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            controller.addProducer(name, topic.toString());
            dialogController.closeDialog();
        });

        dialogController.addCancelAction(e1 -> dialogController.closeDialog());
        dialogController.showDialog();
    }
}
