package kafkavisualizer.navigator;

import javax.swing.*;

public interface TreeNodeIconSupplier {
    Icon getTreeNodeIcon(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus);
}
