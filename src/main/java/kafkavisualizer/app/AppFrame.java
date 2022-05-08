package kafkavisualizer.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class AppFrame extends JFrame {
    private final JPanel leftPanel;
    private final JPanel detailsPanel;
    private final JPanel statusPanel;
    private final JMenuBar appMenuBar;
    private final JMenu fileMenu;
    private final JMenu viewMenu;
    private final JMenu helpMenu;
    private final JMenuItem exitMenuItem;
    private final JRadioButtonMenuItem lightThemeMenuItem;
    private final JRadioButtonMenuItem darkThemeMenuItem;
    private final JMenuItem aboutMenuItem;

    public AppFrame(JPanel leftPanel, JPanel detailsPanel, JPanel statusPanel) throws HeadlessException {
        this.leftPanel = leftPanel;
        this.detailsPanel = detailsPanel;
        this.statusPanel = statusPanel;
        setTitle("Kafka Visualizer");
        setMinimumSize(new Dimension(1024, 768));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        var splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, detailsPanel);
        splitPane.setDividerLocation(300);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(statusPanel, BorderLayout.SOUTH);

        appMenuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitMenuItem = new JMenuItem();
        fileMenu.add(exitMenuItem);

        viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        var themeMenu = new JMenu("Theme");
        themeMenu.setMnemonic(KeyEvent.VK_T);
        lightThemeMenuItem = new JRadioButtonMenuItem();
        darkThemeMenuItem = new JRadioButtonMenuItem();
        var themeButtonGroup = new ButtonGroup();
        themeButtonGroup.add(lightThemeMenuItem);
        themeButtonGroup.add(darkThemeMenuItem);
        themeMenu.add(lightThemeMenuItem);
        themeMenu.add(darkThemeMenuItem);
        viewMenu.add(themeMenu);

        helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        aboutMenuItem = new JMenuItem();
        helpMenu.add(aboutMenuItem);

        appMenuBar.add(fileMenu);
        appMenuBar.add(viewMenu);
        appMenuBar.add(helpMenu);

        setJMenuBar(appMenuBar);
    }

    public JPanel getLeftPanel() {
        return leftPanel;
    }

    public JPanel getDetailsPanel() {
        return detailsPanel;
    }

    public JPanel getStatusPanel() {
        return statusPanel;
    }

    public JMenuBar getAppMenuBar() {
        return appMenuBar;
    }

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    public JRadioButtonMenuItem getLightThemeMenuItem() {
        return lightThemeMenuItem;
    }

    public JRadioButtonMenuItem getDarkThemeMenuItem() {
        return darkThemeMenuItem;
    }

    public JMenu getViewMenu() {
        return viewMenu;
    }

    public JMenu getHelpMenu() {
        return helpMenu;
    }

    public JMenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }
}
