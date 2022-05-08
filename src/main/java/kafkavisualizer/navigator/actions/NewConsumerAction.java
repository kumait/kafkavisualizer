package kafkavisualizer.navigator.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.App;
import kafkavisualizer.KafkaClient;
import kafkavisualizer.Utils;
import kafkavisualizer.dialog.DialogController;
import kafkavisualizer.models.Consumer;
import kafkavisualizer.navigator.ConsumerPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class NewConsumerAction extends AbstractAction {
    public NewConsumerAction() {
        super("New Consumer", new FlatSVGIcon("icons/left.svg"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var controller = App.getAppController().getNavigatorController();
        var consumerPane = new ConsumerPane();
        var dialogController = new DialogController(App.contentPane(), consumerPane, "New Consumer");

        consumerPane.getStartFromComboBox().addItem(Consumer.StartFrom.NOW);
        consumerPane.getStartFromComboBox().addItem(Consumer.StartFrom.BEGINNING);

        KafkaClient.getTopics(controller.getSelectedCluster().getServers(), (topics, e1) -> {
            if (e1 != null) {
                Utils.showError(controller.getNavigatorPane().getTopLevelAncestor(), e1.getMessage());
            } else {
                topics.forEach(t -> consumerPane.getTopicComboBox().addItem(t.getName()));
            }
        });

        dialogController.addOKAction(e1 -> {
            var name = consumerPane.getNameTextField().getText();
            var topic = consumerPane.getTopicComboBox().getSelectedItem();
            var startFrom = consumerPane.getStartFromComboBox().getSelectedItem();
            if (topic == null) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            if (startFrom == null) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }

            controller.addConsumer(name, topic.toString(), (Consumer.StartFrom)startFrom);
            dialogController.closeDialog();
        });

        dialogController.addCancelAction(e1 -> dialogController.closeDialog());
        dialogController.showDialog();
    }
}
