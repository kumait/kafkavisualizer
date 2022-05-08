package kafkavisualizer.navigator;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class ClusterPane extends JPanel {
    private final JTextField nameTextField;
    private final JTextField serversTextField;

    public ClusterPane() {
        setLayout(new MigLayout());
        nameTextField = new JTextField(25);
        serversTextField = new JTextField(25);
        serversTextField.setToolTipText("eg: 10.81.85.20:9092,localhost:9093");
        add(new JLabel("Name"));
        add(nameTextField, "wrap");
        add(new JLabel("Servers"));
        add(serversTextField);
    }

    public JTextField getServersTextField() {
        return serversTextField;
    }

    public JTextField getNameTextField() {
        return nameTextField;
    }
}
