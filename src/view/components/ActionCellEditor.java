package view.components;

import controller.UserLaporanController;
import view.admin.AdminFrame;
import view.admin.DataPeralatanPanel;
import view.admin.EditDataPeralatanDialog;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

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

        /* ================= VIEW ================= */
        btnView.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String id = table.getValueAt(row, 0).toString();
                Window w = SwingUtilities.getWindowAncestor(table);
                if (w instanceof AdminFrame frame) {
                    frame.openDetailLaporan(id);
                }
            }
            fireEditingStopped();
        });

        /* ================= EDIT ================= */
        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                fireEditingStopped();

                // buka dialog (MODAL)
                EditDataPeralatanDialog dialog =
                        new EditDataPeralatanDialog(
                                SwingUtilities.getWindowAncestor(table),
                                table,
                                row
                        );
                dialog.setVisible(true);

                // 🔥 SETELAH DIALOG DITUTUP → REFRESH DATA
                Component c = SwingUtilities.getAncestorOfClass(
                        DataPeralatanPanel.class, table
                );
                if (c instanceof DataPeralatanPanel panel) {
                    panel.reloadData();
                }
            }
        });

        /* ================= DELETE ================= */
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) return;

            String id = table.getValueAt(row, 0).toString();

            int confirm = JOptionPane.showConfirmDialog(
                    table,
                    "Yakin ingin menghapus laporan ini?\nID: " + id,
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                UserLaporanController.hapusLaporan(id);

                // 🔥 refresh panel
                Component c = SwingUtilities.getAncestorOfClass(
                        DataPeralatanPanel.class, table
                );
                if (c instanceof DataPeralatanPanel panel) {
                    panel.reloadData();
                }
            }

            fireEditingStopped();
        });

        panel.add(btnView);
        panel.add(btnEdit);
        panel.add(btnDelete);
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
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
        return "";
    }
}
