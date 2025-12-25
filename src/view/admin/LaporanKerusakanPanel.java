package view.admin;

import controller.UserLaporanController;
import model.LaporanBarang;
import view.components.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LaporanKerusakanPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    public LaporanKerusakanPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        JLabel title = new JLabel("Data Laporan Kerusakan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        String[] cols = {
                "ID Laporan","Nama Peralatan","Jenis",
                "Lokasi","Jenis Kerusakan","Status","Tanggal","Aksi"
        };

        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return c == 7;
            }
        };

        table = new JTable(model);
        table.setRowHeight(36);

        table.getColumnModel().getColumn(5)
                .setCellRenderer(new StatusCellRenderer());

        table.getColumnModel().getColumn(7)
                .setCellRenderer(new ActionCellRenderer());

        table.getColumnModel().getColumn(7)
                .setCellEditor(new ActionCellEditor(table));

        loadData();

        add(new JScrollPane(table), BorderLayout.CENTER);
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
                    l.getTanggal(),
                    ""
            });
        }
    }
}
