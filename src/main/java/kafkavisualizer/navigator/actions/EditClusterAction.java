package kafkavisualizer.navigator.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import kafkavisualizer.App;
import kafkavisualizer.Utils;
import kafkavisualizer.dialog.DialogController;
import kafkavisualizer.navigator.ClusterPane;
import kafkavisualizer.navigator.nodes.ClusterNode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class EditClusterAction extends AbstractAction {

    public EditClusterAction() {
        super("Edit Cluster", new FlatSVGIcon("icons/edit.svg"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var controller = App.getAppController().getNavigatorController();
        var selection = controller.getNavigatorPane().getTree().getSelectionPath();
        if (selection == null) {
            return;
        }
        var clusterNode = (ClusterNode) selection.getLastPathComponent();
        var cluster = clusterNode.getCluster();

        var clusterPane = new ClusterPane();
        var dialogController = new DialogController(App.contentPane(), clusterPane, "Edit Cluster");
        clusterPane.getNameTextField().setText(cluster.getName());
        clusterPane.getServersTextField().setText(cluster.getServers());

        dialogController.addOKAction(e1 -> {
            var name = clusterPane.getNameTextField().getText();
            var servers = clusterPane.getServersTextField().getText();

            if (name == null || name.trim().length() == 0 || servers == null || servers.trim().length() == 0) {
                return;
            }

            if (!servers.equals(cluster.getServers())) {
                var confirmed = Utils.showConfirmation(App.contentPane(), "This will stop and clear all consumers, continue?");
                if (!confirmed) {
                    return;
                }
                App.getAppController().getDetailsController().stopConsumers(cluster);
                App.getAppController().getDetailsController().clearConsumers(cluster);
            }

            cluster.setName(name);
            cluster.setServers(servers);

            try {
                controller.getNavigatorModel().save();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(controller.getNavigatorPane(), "Cannot save.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            //controller.updateTreeFromModel();
            dialogController.closeDialog();

        });
        dialogController.addCancelAction(e1 -> dialogController.closeDialog());
        dialogController.showDialog();
    }
}
