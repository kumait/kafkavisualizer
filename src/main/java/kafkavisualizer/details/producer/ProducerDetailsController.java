package kafkavisualizer.details.producer;

import kafkavisualizer.App;
import kafkavisualizer.Utils;
import kafkavisualizer.details.producer.actions.AddHeaderAction;
import kafkavisualizer.details.producer.actions.RemoveHeaderAction;
import kafkavisualizer.details.producer.actions.SendAction;
import kafkavisualizer.models.HeaderRow;
import kafkavisualizer.models.Producer;
import kafkavisualizer.navigator.actions.EditProducerAction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ProducerDetailsController implements ListSelectionListener {
    private Producer producer;
    private final ProducerDetailsPane producerDetailsPane;
    private final SendAction sendAction;
    private final EditProducerAction editProducerAction;
    private final AddHeaderAction addHeaderAction;
    private final RemoveHeaderAction removeHeaderAction;
    private final HeadersTableModel headersTableModel;

    public ProducerDetailsController() {
        producerDetailsPane = new ProducerDetailsPane();
        sendAction = new SendAction(this);
        producerDetailsPane.getSendButton().setAction(sendAction);

        editProducerAction = new EditProducerAction();
        producerDetailsPane.getEditButton().setAction(editProducerAction);

        var table = producerDetailsPane.getProducerEventPane().getHeadersTable();
        headersTableModel = new HeadersTableModel();
        table.setModel(headersTableModel);
        table.getSelectionModel().addListSelectionListener(this);

        addHeaderAction = new AddHeaderAction(this);
        producerDetailsPane.getProducerEventPane().getAddHeaderButton().setAction(addHeaderAction);
        removeHeaderAction = new RemoveHeaderAction(this);
        removeHeaderAction.setEnabled(false);
        producerDetailsPane.getProducerEventPane().getRemoveHeaderButton().setAction(removeHeaderAction);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        var selection = producerDetailsPane.getProducerEventPane().getHeadersTable().getSelectedRows();
        removeHeaderAction.setEnabled(selection.length > 0);
    }

    public static class HeadersTableModel extends AbstractTableModel {
        private static final String[] COL_NAMES = {"Key", "Value"};
        private final List<HeaderRow> headerRows = new ArrayList<>();

        @Override
        public String getColumnName(int column) {
            return COL_NAMES[column];
        }

        @Override
        public int getRowCount() {
            return headerRows.size();
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            var row = headerRows.get(rowIndex);
            if (columnIndex == 0) {
                return row.getKey();
            } else if (columnIndex == 1) {
                return row.getValue();
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return true;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            var row = headerRows.get(rowIndex);
            if (columnIndex == 0) {
                row.setKey(aValue.toString());
            } else if (columnIndex == 1) {
                row.setValue(aValue.toString());
            }
        }

        public List<HeaderRow> getHeaders() {
            return headerRows;
        }
    }

    public ProducerDetailsPane getProducerDetailsPane() {
        return producerDetailsPane;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public void send() {
        sendAction.setEnabled(false);
        App.getAppController().getStatusController().setWestStatus("Sending...");
        new Thread(() -> {
            Properties props = new Properties();
            props.put("bootstrap.servers", App.getAppController().getNavigatorController().getSelectedCluster().getServers());
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("acks", "all");
            props.put("request.timeout.ms", 5000);
            props.put("delivery.timeout.ms", 10000);
            props.put("linger.ms", 0);
            props.put("client.id", "Kafka Visualizer");

            try (var kafkaProducer = new KafkaProducer<String, String>(props)) {
                String key = null;
                String value = null;

                var keyText = producerDetailsPane.getProducerEventPane().getKeyTextArea().getText();
                if (keyText != null && keyText.length() > 0) {
                    key = keyText;
                }

                var valueText = producerDetailsPane.getProducerEventPane().getValueTextArea().getText();
                if (keyText != null && valueText.length() > 0) {
                    value = valueText;
                }

                var headers = headersTableModel.getHeaders()
                        .stream()
                        .map(h -> (Header) new RecordHeader(h.getKey(), h.getValue().getBytes(StandardCharsets.UTF_8))).collect(Collectors.toList());

                var record = new ProducerRecord<>(producer.getTopic(), null, key, value, headers);
                try {
                    var recordMetadata = kafkaProducer.send(record).get();
                    var message = String.format("Sent [partition = %d]", recordMetadata.partition());
                    if (recordMetadata.hasOffset()) {
                        message += String.format(" [offset = %d]", recordMetadata.offset());
                    }
                    App.getAppController().getStatusController().setWestStatus(message);
                } catch (Exception e) {
                    App.getAppController().getStatusController().setWestStatus("Send Error");
                    Utils.showError(App.contentPane(), e.getMessage());
                } finally {
                    sendAction.setEnabled(true);
                }
            }
        }).start();
    }

    public void addEmptyHeader() {
        var header = new HeaderRow("", "");
        headersTableModel.getHeaders().add(header);
        headersTableModel.fireTableDataChanged();
    }

    public void removeSelectedHeaders() {
        var table = producerDetailsPane.getProducerEventPane().getHeadersTable();
        var selection = table.getSelectedRows();
        headersTableModel.getHeaders().removeAll(Arrays.stream(selection).mapToObj(i -> headersTableModel.getHeaders().get(i)).collect(Collectors.toList()));
        headersTableModel.fireTableDataChanged();
    }
}
