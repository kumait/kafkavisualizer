package kafkavisualizer.status;

import javax.swing.*;

public class StatusController {
    private final StatusPane statusPane;

    public StatusController() {
        statusPane = new StatusPane();
    }

    public StatusPane getStatusPane() {
        return statusPane;
    }

    public void setWestStatus(String status) {
        SwingUtilities.invokeLater(() -> statusPane.getWestLabel().setText(status));
    }

    public void setEastStatus(String status) {
        SwingUtilities.invokeLater(() -> statusPane.getEastLabel().setText(status));
    }
}
