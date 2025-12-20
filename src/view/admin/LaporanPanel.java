package view.admin;

import util.UIStyle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LaporanPanel extends JPanel {

    public LaporanPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BACKGROUND);

        JLabel title = new JLabel("Data Laporan Kerusakan");
        title.setFont(UIStyle.TITLE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // ===== SEARCH BAR =====
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        JTextField searchField = new JTextField();
        JButton btnCari = new JButton("Cari");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(btnCari, BorderLayout.EAST);

        // ===== TABLE =====
        String[] kolom = {"ID", "Peralatan", "Lokasi", "Status", "Tanggal"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);
        JTable table = new JTable(model);
        table.setRowHeight(28);

        model.addRow(new Object[]{"LPR-001", "PC-15", "Lab 1", "Rusak", "14/12/2025"});
        model.addRow(new Object[]{"LPR-002", "Monitor-22", "Lab 2", "Perbaikan", "13/12/2025"});

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(UIStyle.BACKGROUND);
        topWrapper.add(title, BorderLayout.NORTH);
        topWrapper.add(searchPanel, BorderLayout.SOUTH);

        add(topWrapper, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
