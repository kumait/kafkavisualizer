package kafkavisualizer.navigator.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.App;
import kafkavisualizer.KafkaClient;
import kafkavisualizer.Utils;
import kafkavisualizer.dialog.DialogController;
import kafkavisualizer.navigator.ProducerPane;
import kafkavisualizer.navigator.nodes.ProducerNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class EditProducerAction extends AbstractAction {
    public EditProducerAction() {
        super("Edit Producer", new FlatSVGIcon("icons/edit.svg"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var controller = App.getAppController().getNavigatorController();
        var producerPane = new ProducerPane();
        var dialogController = new DialogController(App.contentPane(), producerPane, "Edit Producer");

        var producerNode = (ProducerNode)controller.getSelectedNode();
        var producer = producerNode.getProducer();

        KafkaClient.getTopics(controller.getSelectedCluster().getServers(), (topics, e1) -> {
            if (e1 != null) {
                Utils.showError(controller.getNavigatorPane().getTopLevelAncestor(), e1.getMessage());
            } else {
                topics.forEach(t -> producerPane.getTopicComboBox().addItem(t.getName()));
                producerPane.getTopicComboBox().setSelectedItem(producer.getTopic());
            }
        });

        producerPane.getNameTextField().setText(producer.getName());

        dialogController.addOKAction(e1 -> {
            var name = producerPane.getNameTextField().getText();
            var topic = producerPane.getTopicComboBox().getSelectedItem();
            if (topic == null) {
                Toolkit.getDefaultToolkit().beep();
                return;
            }
            producer.setName(name);
            producer.setTopic(topic.toString());
            dialogController.closeDialog();
            controller.getTreeModel().nodeChanged(producerNode);

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
