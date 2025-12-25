package view.admin;

import controller.UserLaporanController;
import model.LaporanBarang;
import view.components.StatusCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportsPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public ReportsPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        String[] cols = {
                "ID", "Nama", "Jenis", "Lokasi",
                "Kerusakan", "Status", "Tanggal"
        };

        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(32);

        table.getColumnModel()
                .getColumn(5)
                .setCellRenderer(new StatusCellRenderer());

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData(); // load awal
    }

    private void loadData() {
        model.setRowCount(0);

        for (LaporanBarang l : UserLaporanController.getAllLaporan()) {
            model.addRow(new Object[]{
                    l.getIdLaporan(),
                    l.getNamaBarang(),
                    l.getJenis(),
                    l.getLokasi(),
                    l.getKerusakan(),
                    l.getStatus(),
                    l.getTanggal()
            });
        }
    }

    // 🔥 INI YANG BENAR
    public void reloadData() {
        loadData();
    }
}
