package kafkavisualizer;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import kafkavisualizer.app.AppController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class App {
    private static AppController appController;

    public static AppController getAppController() {
        return appController;
    }

    public static Container contentPane() {
        return appController.getAppFrame().getContentPane();
    }

    private static void initTheme() throws IOException {
        var theme = Config.props().putIfAbsent(Config.KEY_THEME, "light");
        if (theme == null) {
            Config.save();
        }
    }

    private static void setupTheme() {
        var theme = Config.props().getProperty(Config.KEY_THEME);
        if ("light".equals(theme)) {
            FlatLightLaf.setup();
        } else if ("dark".equals(theme)) {
            FlatDarkLaf.setup();
        }
    }

    public static void main(String[] args) {
        try {
            if (SystemInfo.isMacOS) {
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                System.setProperty("apple.awt.application.name", "Kafka Visualizer");
                System.setProperty("apple.awt.application.appearance", "system");
            }

            if (SystemInfo.isLinux) {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
            }

            Config.load();
            initTheme();
        } catch (IOException e) {
            Utils.showError(null, e.getMessage());
        }
        setupTheme();
        SwingUtilities.invokeLater(() -> {
            appController = new AppController();
            appController.start();
        });
    }

}
