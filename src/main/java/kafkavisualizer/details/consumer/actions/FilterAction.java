package kafkavisualizer.details.consumer.actions;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FilterAction extends AbstractAction {
    public FilterAction() {
        super("Start", new FlatSVGIcon("icons/filter.svg", 16, 16));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
