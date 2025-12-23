package view.components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import view.admin.AdminFrame;
import view.admin.EditDataPeralatanDialog;

public class ActionCellEditor extends AbstractCellEditor implements TableCellEditor {

    private final JPanel panel;
    private final JTable table;

    public ActionCellEditor(JTable table) {
        this.table = table;

        panel = new JPanel(new GridLayout(1, 3, 6, 0));
        panel.setOpaque(true);

        JButton btnView   = createButton("View",  new Color(59,130,246));
        JButton btnEdit   = createButton("Edit",  new Color(234,179,8));
        JButton btnDelete = createButton("Hapus", new Color(220,38,38));

        btnView.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String id = table.getValueAt(row, 0).toString();

                Window w = SwingUtilities.getWindowAncestor(table);
                if (w instanceof AdminFrame frame) {
                    frame.openDetailLaporan(id);
                }
                fireEditingStopped();
            }
        });


// ===== EDIT =====
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                fireEditingStopped();
                new EditDataPeralatanDialog(
                        SwingUtilities.getWindowAncestor(table),
                        table,
                        row
                ).setVisible(true);
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            fireEditingStopped();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(
                        table,
                        "Yakin hapus data ini?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    ((DefaultTableModel) table.getModel()).removeRow(row);
                }
            }
        });

        panel.add(btnView);
        panel.add(btnEdit);
        panel.add(btnDelete);
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.BLACK);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int col) {

        panel.setBackground(table.getSelectionBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
