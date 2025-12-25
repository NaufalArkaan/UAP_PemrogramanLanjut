package view.admin;

import controller.UserLaporanController;
import model.LaporanBarang;
import util.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LaporanPanel extends JPanel {

    private DefaultTableModel model;

    public LaporanPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BACKGROUND);

        JLabel title = new JLabel("Data Laporan Kerusakan");
        title.setFont(UIStyle.TITLE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        String[] kolom = {"ID", "Peralatan", "Lokasi", "Status", "Tanggal"};
        model = new DefaultTableModel(kolom, 0);
        JTable table = new JTable(model);
        table.setRowHeight(28);

        loadData(); // 🔥 AMBIL DARI CSV

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadData() {
        model.setRowCount(0);

        for (LaporanBarang l : UserLaporanController.getAllLaporan()) {
            model.addRow(new Object[]{
                    l.getIdLaporan(),
                    l.getNamaBarang(),
                    l.getLokasi(),
                    l.getStatus(),
                    l.getTanggal()
            });
        }
    }
}
