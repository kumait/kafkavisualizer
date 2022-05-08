package kafkavisualizer.app.actions;

import kafkavisualizer.app.AppController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class ExitAction extends AbstractAction {
    private final AppController controller;

    public ExitAction(AppController controller) {
        super("Exit");
        putValue(MNEMONIC_KEY, KeyEvent.VK_Y);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));

        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.controller.getAppFrame().dispatchEvent(new WindowEvent(this.controller.getAppFrame(), WindowEvent.WINDOW_CLOSING));
    }
}
