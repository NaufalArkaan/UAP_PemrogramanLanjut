package view.components;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import view.admin.ReportFormDialog;
import java.awt.*;

public class ActionCellEditor extends AbstractCellEditor implements TableCellEditor {

    private JPanel panel;

    public ActionCellEditor(JTable table) {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));

        JButton btnView = new JButton("👁");
        JButton btnEdit = new JButton("✏");
        JButton btnDelete = new JButton("🗑");

        btnView.addActionListener(e -> {
            int row = table.getSelectedRow();
            new view.admin.ReportViewDialog(table, row);
            fireEditingStopped();
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            new view.admin.ReportFormDialog(table, row);
            fireEditingStopped();
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            int confirm = JOptionPane.showConfirmDialog(
                    table,
                    "Hapus data ini?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                ((javax.swing.table.DefaultTableModel) table.getModel())
                        .removeRow(row);
            }
            fireEditingStopped();
        });

        panel.add(btnView);
        panel.add(btnEdit);
        panel.add(btnDelete);
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int col) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }
}
