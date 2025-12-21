package view.components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableActionRenderer extends JPanel implements TableCellRenderer {

    public TableActionRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 6, 4));
        setOpaque(true);

        add(createButton("👁"));
        add(createButton("✏"));
        add(createButton("🗑"));
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setMargin(new Insets(2,6,2,6));
        return btn;
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        setBackground(isSelected
                ? table.getSelectionBackground()
                : Color.WHITE);

        return this;
    }
}
