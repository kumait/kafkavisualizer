package kafkavisualizer.details.topic.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.App;
import kafkavisualizer.Utils;
import kafkavisualizer.details.topic.TopicPane;
import kafkavisualizer.dialog.DialogAction;
import kafkavisualizer.dialog.DialogController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class NewTopicAction extends AbstractAction {
    public NewTopicAction() {
        super("New Topic", new FlatSVGIcon("icons/add.svg", 16, 16));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var controller = App.getAppController().getDetailsController().getTopicsDetailsController();

        var topicPane = new TopicPane();
        topicPane.getPartitionsTextField().setText("1");
        topicPane.getReplicationFactorTextField().setText("1");
        var dialogController = new DialogController(App.contentPane(), topicPane, "New Topic");

        var okAction = new DialogAction("OK", null);
        var cancelAction = new DialogAction("Cancel", null, (e1) -> dialogController.closeDialog());
        dialogController.addDefaultAction(okAction);
        dialogController.addAction(cancelAction);

        okAction.setListener((e1) -> {
            okAction.setEnabled(false);
            var name = topicPane.getNameTextField().getText().trim();
            var partitions = Integer.parseInt(topicPane.getPartitionsTextField().getText());
            var replicationFactor = Short.parseShort(topicPane.getReplicationFactorTextField().getText());

            new Thread(() -> {
                try {
                    controller.getModel().createTopic(name, partitions, replicationFactor);
                    SwingUtilities.invokeLater(dialogController::closeDialog);
                    controller.loadTopics();
                } catch (Exception ex) {
                    Utils.showError(controller.getTopicsPane().getTopLevelAncestor(), ex.getMessage());
                } finally {
                    SwingUtilities.invokeLater(() -> okAction.setEnabled(true));
                }
            }).start();
        });

        dialogController.showDialog();
    }
}
