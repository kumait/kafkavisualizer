package kafkavisualizer.navigator.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.App;
import kafkavisualizer.KafkaClient;
import kafkavisualizer.Utils;
import kafkavisualizer.dialog.DialogController;
import kafkavisualizer.models.Consumer;
import kafkavisualizer.models.Format;
import kafkavisualizer.navigator.ConsumerPane;
import kafkavisualizer.navigator.nodes.ConsumerNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class EditConsumerAction extends AbstractAction {

    public EditConsumerAction() {
        super("Edit Consumer", new FlatSVGIcon("icons/edit.svg"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var controller = App.getAppController().getNavigatorController();
        var consumerPane = new ConsumerPane();
        var dialogController = new DialogController(App.contentPane(), consumerPane, "Edit Consumer");

        for (var item: Consumer.StartFrom.values()) {
            consumerPane.getStartFromComboBox().addItem(item);
        }

        for (var item: Format.values()) {
            consumerPane.getValueFormatComboBox().addItem(item);
            consumerPane.getKeyFormatComboBox().addItem(item);
        }

        var consumerNode = (ConsumerNode)controller.getSelectedNode();
        var consumer = consumerNode.getConsumer();

        consumerPane.getNameTextField().setText(consumer.getName());
        consumerPane.getTopicComboBox().setSelectedItem(consumer.getTopics().get(0));
        consumerPane.getStartFromComboBox().setSelectedItem(consumer.getStartFrom());

        // Backward compatibility
        if (consumer.getValueFormat() == null) {
            consumer.setValueFormat(Format.PLAIN_TEXT);
        }
        if (consumer.getKeyFormat() == null) {
            consumer.setKeyFormat(Format.PLAIN_TEXT);
        }

        consumerPane.getValueFormatComboBox().setSelectedItem(consumer.getValueFormat());
        consumerPane.getKeyFormatComboBox().setSelectedItem(consumer.getKeyFormat());

        KafkaClient.getTopics(controller.getSelectedCluster().getServers(), (topics, e1) -> {
            if (e1 != null) {
                Utils.showError(controller.getNavigatorPane().getTopLevelAncestor(), e1.getMessage());
            } else {
                topics.forEach(t -> consumerPane.getTopicComboBox().addItem(t.getName()));
                consumerPane.getTopicComboBox().setSelectedItem(consumer.getTopics().get(0));
            }
        });

        consumerPane.getNameTextField().setText(consumer.getName());

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

            consumer.setName(name);
            consumer.setTopics(List.of(topic.toString()));
            consumer.setStartFrom((Consumer.StartFrom) startFrom);
            consumer.setValueFormat((Format)consumerPane.getValueFormatComboBox().getSelectedItem());
            consumer.setKeyFormat((Format)consumerPane.getKeyFormatComboBox().getSelectedItem());
            dialogController.closeDialog();
            controller.getTreeModel().nodeChanged(consumerNode);

            try {
                controller.getNavigatorModel().save();
            } catch (IOException ex) {
                Utils.showError(controller.getNavigatorPane().getTopLevelAncestor(), ex.getMessage());
            }

        });



        dialogController.addCancelAction(e1 -> dialogController.closeDialog());
        dialogController.showDialog();
    }
}
