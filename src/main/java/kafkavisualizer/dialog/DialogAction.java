package kafkavisualizer.dialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class DialogAction extends AbstractAction {
    private ActionListener listener;

    public DialogAction(String name, Icon icon, ActionListener listener) {
        super(name, icon);
        this.listener = listener;
    }

    public DialogAction(String name, Icon icon) {
        this(name, icon, null);
    }

    public ActionListener getListener() {
        return listener;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener != null) {
            listener.actionPerformed(e);
        }
    }
}
