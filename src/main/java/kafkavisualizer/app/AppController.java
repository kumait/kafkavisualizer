package kafkavisualizer.app;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.util.SystemInfo;
import kafkavisualizer.Config;
import kafkavisualizer.Utils;
import kafkavisualizer.app.actions.AboutAction;
import kafkavisualizer.app.actions.ExitAction;
import kafkavisualizer.app.actions.SwitchThemeAction;
import kafkavisualizer.details.DetailsController;
import kafkavisualizer.events.Event;
import kafkavisualizer.events.EventBus;
import kafkavisualizer.navigator.NavigatorController;
import kafkavisualizer.status.StatusController;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.AboutEvent;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class AppController {
    private final AppFrame appFrame;
    private final NavigatorController navigatorController;
    private final DetailsController detailsController;
    private final StatusController statusController;

    public AppController() {
        navigatorController = new NavigatorController();
        detailsController = new DetailsController();
        statusController = new StatusController();
        appFrame = new AppFrame(navigatorController.getNavigatorPane(), detailsController.getDetailsPane(), statusController.getStatusPane());
        appFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AppFrame appFrame = (AppFrame) e.getSource();
                Component focusOwner = appFrame.getFocusOwner();
                if (focusOwner != null) {
                    appFrame.dispatchEvent(new FocusEvent(appFrame.getFocusOwner(), FocusEvent.FOCUS_LOST));
                }
                EventBus.broadcast(Event.APP_CLOSING);
            }
        });

        if (SystemInfo.isMacOS) {
            appFrame.getAppMenuBar().remove(appFrame.getFileMenu());
            appFrame.getAppMenuBar().remove(appFrame.getHelpMenu());

            var desktop = Desktop.getDesktop();
            desktop.setQuitHandler((e, response) -> {
                detailsController.stopConsumers(null);
                response.performQuit();
            });

            desktop.setAboutHandler(AppController::handleAbout);
        }

        appFrame.getExitMenuItem().setAction(new ExitAction(this));
        appFrame.getLightThemeMenuItem().setAction(new SwitchThemeAction(this, new SwitchThemeAction.ThemeInfo("light", "Light", FlatLightLaf.class.getName())));
        appFrame.getDarkThemeMenuItem().setAction(new SwitchThemeAction(this, new SwitchThemeAction.ThemeInfo("dark", "Dark", FlatDarkLaf.class.getName())));
        appFrame.getAboutMenuItem().setAction(new AboutAction());
    }

    private static void handleAbout(AboutEvent e) {
        new AboutAction().actionPerformed(null);
    }

    public void start() {
        appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        appFrame.setTitle("Kafka Visualizer");
        appFrame.pack();
        appFrame.setLocationRelativeTo(null);
        appFrame.setVisible(true);
        try {
            loadConfiguration();
        } catch (IOException e) {
            Utils.showError(appFrame, e.getMessage());
        }
        EventBus.broadcast(Event.APP_STARTED);
    }

    private void loadConfiguration() throws IOException {
        var theme = Config.props().getProperty(Config.KEY_THEME);
        appFrame.getLightThemeMenuItem().setSelected("light".equals(theme));
        appFrame.getDarkThemeMenuItem().setSelected("dark".equals(theme));
    }

    public NavigatorController getNavigatorController() {
        return navigatorController;
    }

    public DetailsController getDetailsController() {
        return detailsController;
    }

    public StatusController getStatusController() {
        return statusController;
    }

    public AppFrame getAppFrame() {
        return appFrame;
    }
}
