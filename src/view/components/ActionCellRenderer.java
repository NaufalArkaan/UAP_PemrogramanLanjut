package view.components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ActionCellRenderer extends JPanel implements TableCellRenderer {

    public ActionCellRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 6, 0));
        setOpaque(true);

        add(createButton("👁", new Color(59,130,246))); // View
        add(createButton("✏", new Color(234,179,8)));  // Edit
        add(createButton("🗑", new Color(220,38,38)));  // Delete
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(36, 26));
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setBorder(BorderFactory.createEmptyBorder());
        return btn;
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
        return this;
    }
}
