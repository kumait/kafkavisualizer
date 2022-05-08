package kafkavisualizer.app.actions;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import kafkavisualizer.Config;
import kafkavisualizer.Utils;
import kafkavisualizer.app.AppController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class SwitchThemeAction extends AbstractAction {
    public static class ThemeInfo {
        private final String id;
        private final String name;
        private final String className;

        public ThemeInfo(String id, String name, String className) {
            this.id = Objects.requireNonNull(id);
            this.name = Objects.requireNonNull(name);
            this.className = Objects.requireNonNull(className);
        }
    }

    private final AppController controller;
    private final ThemeInfo themeInfo;

    public SwitchThemeAction(AppController controller, ThemeInfo themeInfo) {
        super(themeInfo.name);
        this.controller = controller;
        this.themeInfo = themeInfo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (UIManager.getLookAndFeel().getClass().getName().equals(themeInfo.className)) {
            return;
        }
        EventQueue.invokeLater(() -> {
            FlatAnimatedLafChange.showSnapshot();
            try {
                UIManager.setLookAndFeel(themeInfo.className);
                FlatLaf.updateUI();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
                Config.props().put(Config.KEY_THEME, themeInfo.id);
                Config.save();
            } catch( Exception ex ) {
                Utils.showError(controller.getAppFrame(), ex.getMessage());
            }
        });
    }
}
