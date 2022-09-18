package kafkavisualizer.app.actions;

import kafkavisualizer.App;
import kafkavisualizer.Utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AboutAction extends AbstractAction {
    public AboutAction() {
        super("About");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        var message = "Kafka Visualizer 1.1.0\n" +
                "https://github.com/kumait/kafkavisualizer\n\n" +
                "This software includes icons from IntelliJ IDEA Community Edition\n" +
                "Copyright (C) JetBrains s.r.o.\n" +
                "https://www.jetbrains.com/idea/\n\n" +
                "This software uses FlatLaf\n" +
                "https://www.formdev.com/flatlaf/";

        Utils.showInfo(App.contentPane(), message);
    }
}
