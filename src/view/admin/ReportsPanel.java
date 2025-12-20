package view.admin;

import view.components.StatusCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;

public class ReportsPanel extends JPanel {

    private JTable table;

    public ReportsPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel title = new JLabel("Riwayat Laporan");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton btnExportCsv = new JButton("Export CSV");
        btnExportCsv.setFocusPainted(false);
        btnExportCsv.addActionListener(e -> exportToCSV());

        header.add(title, BorderLayout.WEST);
        header.add(btnExportCsv, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ===== TABLE MODEL =====
        String[] cols = {"ID", "Peralatan", "Lokasi", "Status", "Tanggal"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        model.addRow(new Object[]{"#001", "Komputer PC-15", "Lab 1", "Rusak", "14/12/2025"});
        model.addRow(new Object[]{"#002", "Monitor LCD-22", "Lab 2", "Perbaikan", "13/12/2025"});
        model.addRow(new Object[]{"#003", "Proyektor-08", "Lab Multimedia", "Normal", "12/12/2025"});

        // ===== TABLE =====
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // ===== HEADER STYLE =====
        JTableHeader th = table.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 13));
        th.setBackground(new Color(243, 244, 246));
        th.setForeground(Color.DARK_GRAY);
        th.setReorderingAllowed(false);

        // ===== STATUS COLOR =====
        table.getColumnModel()
                .getColumn(3)
                .setCellRenderer(new StatusCellRenderer());

        // ===== SCROLLPANE =====
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        sp.getViewport().setBackground(Color.WHITE);

        add(sp, BorderLayout.CENTER);
    }

    // ===== EXPORT CSV =====
    private void exportToCSV() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Simpan Laporan CSV");

        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            try (FileWriter fw = new FileWriter(file + ".csv")) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                // header
                for (int i = 0; i < model.getColumnCount(); i++) {
                    fw.write(model.getColumnName(i) + ",");
                }
                fw.write("\n");

                // data
                for (int r = 0; r < model.getRowCount(); r++) {
                    for (int c = 0; c < model.getColumnCount(); c++) {
                        fw.write(model.getValueAt(r, c).toString() + ",");
                    }
                    fw.write("\n");
                }

                JOptionPane.showMessageDialog(this, "Berhasil export CSV!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal export CSV!");
            }
        }
    }
}
