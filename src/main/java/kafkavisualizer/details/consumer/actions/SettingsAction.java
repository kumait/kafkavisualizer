package kafkavisualizer.details.consumer.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SettingsAction extends AbstractAction {
    public SettingsAction() {
        super("Start", new FlatSVGIcon("icons/settings.svg", 16, 16));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
