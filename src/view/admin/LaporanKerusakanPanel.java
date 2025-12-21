package view.admin;

import view.components.StatusCellRenderer;
import view.components.ActionCellRenderer;
import view.components.ActionCellEditor;
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

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Data Laporan Kerusakan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));

        JButton btnTambah = new JButton("+ Tambah Data");
        btnTambah.setBackground(new Color(30,64,175));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);

        btnTambah.addActionListener(e -> {
            new LaporanFormDialog(null, model);
        });

        header.add(title, BorderLayout.WEST);
        header.add(btnTambah, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] cols = {
                "ID Laporan", "Nama Peralatan", "Jenis",
                "Lokasi", "Jenis Kerusakan", "Status", "Tanggal", "Aksi"
        };

        model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) {
                return col == 7;
            }
        };

        // dummy data
        model.addRow(new Object[]{"#LPR-001","Komputer PC-15","Komputer","Lab 1","Hardware","Rusak","14/12/2025",""});
        model.addRow(new Object[]{"#LPR-002","Monitor LCD-22","Monitor","Lab 2","Hardware","Perbaikan","13/12/2025",""});
        model.addRow(new Object[]{"#LPR-003","Proyektor-08","Proyektor","Lab Multimedia","Hardware","Normal","12/12/2025",""});

        table = new JTable(model);
        table.setRowHeight(36);
        table.getColumnModel().getColumn(5).setCellRenderer(new StatusCellRenderer());
        table.getColumnModel().getColumn(7).setCellRenderer(new ActionCellRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new ActionCellEditor(table));

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));

        add(sp, BorderLayout.CENTER);
    }
}
