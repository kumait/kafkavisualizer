package kafkavisualizer.details.producer.listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import kafkavisualizer.App;
import kafkavisualizer.details.producer.ProducerDetailsController;
import kafkavisualizer.models.Header;
import kafkavisualizer.models.Producer;

public class ProducerFocusListener implements FocusListener {

    @Override
    public void focusGained(FocusEvent e) {
    }

    @Override
    public void focusLost(FocusEvent e) {
        
        JComponent jComponent = (JComponent) e.getSource();
        var detailsController = App.getAppController().getDetailsController();
        Producer producer = detailsController.getProducerByEventComponent(jComponent);
        boolean needSave = false;
        
        if ("valueTextArea".equals(jComponent.getName())) {
            String actualText = ((JTextArea) jComponent).getText();
            if (!actualText.equals(producer.getValue())) {
                producer.setValue(actualText);
                needSave = true;
            }
        } else if ("keyTextArea".equals(jComponent.getName())) {
            String actualText = ((JTextArea) jComponent).getText();
            if (!actualText.equals(producer.getKey())) {
                producer.setKey(actualText);
                needSave = true;
            }
        } else if ("headersTable".equals(jComponent.getName())) {
            ProducerDetailsController pdc = detailsController.getProducerDetailsControllerByProducer(producer);
            List<Header> actualHeaders = pdc.getHeadersRows().stream()
                    .map(headerRow -> new Header(headerRow.getKey(), headerRow.getValue()))
                    .collect(Collectors.toList());
            
            if (!headersListsEquals(producer.getHeaders(), actualHeaders)) {
                producer.getHeaders().clear();
                producer.getHeaders().addAll(actualHeaders);
                needSave = true;
            }
        }
        
        if (needSave) {
            var navigatorController = App.getAppController().getNavigatorController();
            try {
                navigatorController.getNavigatorModel().save();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(navigatorController.getNavigatorPane(), "Cannot save.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean headersListsEquals(List<Header> list1, List<Header> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }
        for(int i = 0; i < list1.size(); i++) {
            if (!Objects.equals(list1.get(i), list2.get(i))) {
                return false;
            }
        }
        return true;
    }

}
