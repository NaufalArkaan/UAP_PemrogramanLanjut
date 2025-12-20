package view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StatusCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        label.setOpaque(true);

        String status = value.toString().toLowerCase();

        if (status.contains("rusak")) {
            label.setBackground(new Color(254, 226, 226));
            label.setForeground(new Color(185, 28, 28));
        } else if (status.contains("perbaikan")) {
            label.setBackground(new Color(254, 243, 199));
            label.setForeground(new Color(202, 138, 4));
        } else {
            label.setBackground(new Color(220, 252, 231));
            label.setForeground(new Color(22, 163, 74));
        }

        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
            label.setForeground(table.getSelectionForeground());
        }

        return label;
    }
}
