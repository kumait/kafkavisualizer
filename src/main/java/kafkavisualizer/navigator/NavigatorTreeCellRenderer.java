package kafkavisualizer.navigator;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class NavigatorTreeCellRenderer extends DefaultTreeCellRenderer {
    private final TreeNodeIconSupplier treeNodeIconSupplier;

    public NavigatorTreeCellRenderer(TreeNodeIconSupplier treeNodeIconSupplier) {
        if (treeNodeIconSupplier == null) {
            throw new NullPointerException("iconTreeNodeSupplier cannot be null");
        }
        this.treeNodeIconSupplier = treeNodeIconSupplier;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        var label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        var icon = treeNodeIconSupplier.getTreeNodeIcon(tree, value, sel, expanded, leaf, row, hasFocus);
        if (icon != null) {
            label.setIcon(icon);
        }
        return label;
    }
}
