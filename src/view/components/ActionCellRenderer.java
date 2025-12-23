package view.components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ActionCellRenderer extends JPanel implements TableCellRenderer {

    public ActionCellRenderer() {
        setLayout(new GridLayout(1, 3, 6, 0));
        setOpaque(true);

        add(createButton("View", new Color(59, 130, 246)));
        add(createButton("Edit", new Color(234, 179, 8)));
        add(createButton("Hapus", new Color(220, 38, 38)));
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.BLACK);   // 🔥 TEXT HITAM
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setEnabled(false);             // renderer only
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
