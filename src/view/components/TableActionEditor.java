package view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class TableActionEditor extends AbstractCellEditor implements TableCellEditor {

    private JPanel panel = new JPanel();
    private JButton btnView = new JButton("👁");
    private JButton btnEdit = new JButton("✏");
    private JButton btnDelete = new JButton("🗑");

    public TableActionEditor(JTable table, DefaultTableModel model) {

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 6, 4));
        panel.add(btnView);
        panel.add(btnEdit);
        panel.add(btnDelete);

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int confirm = JOptionPane.showConfirmDialog(
                        table,
                        "Yakin ingin menghapus data ini?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(row);
                }
            }
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected,
            int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
