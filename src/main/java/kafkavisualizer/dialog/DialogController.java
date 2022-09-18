package kafkavisualizer.dialog;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class DialogController {
    private final JDialog dialog;
    private final Component parent;
    private final Window window;
    private final Container content;
    private final String title;
    private final List<Action> actions;
    private Action defaultAction;

    public DialogController(Component parent, Container content, String title) {
        this.parent = parent;
        this.window = parent != null ? SwingUtilities.windowForComponent(parent) : null;
        this.content = content;
        this.title = title;
        this.actions = new ArrayList<>();
        this.dialog = createDialog();
    }

    private JDialog createDialog() {
        final JDialog dialog;
        if (window instanceof Frame) {
            dialog = new JDialog((Frame) window, title, true);
        } else {
            dialog = new JDialog((Dialog) window, title, true);
        }
        return dialog;
    }

    private void initDialog(JDialog dialog) {
        var actionsPane = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        for (var action : actions) {
            var button = new JButton(action);
            actionsPane.add(button);
            if (action == defaultAction) {
                dialog.getRootPane().setDefaultButton(button);
            }
        }

        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(content, BorderLayout.CENTER);
        dialog.getContentPane().add(actionsPane, BorderLayout.SOUTH);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setResizable(false);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
    }

    public void addAction(DialogAction action) {
        actions.add(action);
    }

    public void addDefaultAction(DialogAction action) {
        addAction(action);
        defaultAction = action;
    }

    public void addOKAction(ActionListener listener) {
        var action = new DialogAction("OK", null, listener);
        addDefaultAction(action);
    }

    public void addCancelAction(ActionListener listener) {
        addAction(new DialogAction("Cancel", null, listener));
    }

    public Container getContent() {
        return content;
    }

    public void showDialog() {
        initDialog(dialog);
        dialog.pack();
        dialog.setVisible(true);
    }

    public void closeDialog() {
        dialog.dispose();
        dialog.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlatLightLaf.setup();

            var panel = new JPanel(new FlowLayout(FlowLayout.LEADING));
            panel.add(new JTextField(25));
            panel.add(new JButton("Check"));

            var dc = new DialogController(null, panel, "Test Dialog");

            var applyAction = new DialogAction("Apply", null, e -> {
                dc.closeDialog();
            });


            dc.addDefaultAction(new DialogAction("OK", null, e -> {
                applyAction.setEnabled(false);
            }));

            dc.addAction(new DialogAction("Cancel", null, e -> {
                dc.closeDialog();
            }));


            applyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
            dc.addAction(applyAction);
            dc.showDialog();

        });
    }
}
