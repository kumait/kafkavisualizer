package kafkavisualizer.navigator.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TestAction extends AbstractAction {
    private Runnable runnable;

    public TestAction(String name, Runnable runnable) {
        super(name);
        this.runnable = runnable;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.runnable.run();
    }
}
