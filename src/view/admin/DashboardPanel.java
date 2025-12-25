package view.admin;

import controller.UserLaporanController;
import model.LaporanBarang;
import view.components.StatCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardPanel extends JPanel {

    // ===== STAT CARD SEBAGAI FIELD (AGAR BISA UPDATE) =====
    private StatCard totalPeralatanCard;
    private StatCard peralatanRusakCard;
    private StatCard dalamPerbaikanCard;
    private StatCard totalLaporanCard;

    // ===== TABEL LAPORAN (SATU INSTANCE) =====
    private ReportsPanel reportsPanel;

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 246, 250));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildCards(), BorderLayout.CENTER);
        add(buildTable(), BorderLayout.SOUTH);

        // 🔥 LOAD DATA AWAL
        refreshData();
    }

    /* ================= HEADER ================= */

    private JComponent buildHeader() {
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel title = new JLabel("Dashboard Monitoring");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitle = new JLabel(
                "Sistem Monitoring Kerusakan Peralatan Laboratorium Informatika"
        );
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(Color.DARK_GRAY);

        top.add(title, BorderLayout.NORTH);
        top.add(subtitle, BorderLayout.SOUTH);
        return top;
    }

    /* ================= STAT CARDS ================= */

    private JComponent buildCards() {

        JPanel wrapper = new JPanel(new GridLayout(1, 4, 16, 16));
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));

        totalPeralatanCard = new StatCard(
                "Total Peralatan", "0",
                UIManager.getIcon("OptionPane.informationIcon"),
                new Color(28, 81, 204)
        );

        peralatanRusakCard = new StatCard(
                "Peralatan Rusak", "0",
                UIManager.getIcon("OptionPane.errorIcon"),
                new Color(200, 40, 40)
        );

        dalamPerbaikanCard = new StatCard(
                "Dalam Perbaikan", "0",
                UIManager.getIcon("OptionPane.warningIcon"),
                new Color(230, 160, 20)
        );

        totalLaporanCard = new StatCard(
                "Total Laporan", "0",
                UIManager.getIcon("OptionPane.questionIcon"),
                new Color(0, 160, 80)
        );

        wrapper.add(totalPeralatanCard);
        wrapper.add(peralatanRusakCard);
        wrapper.add(dalamPerbaikanCard);
        wrapper.add(totalLaporanCard);

        return wrapper;
    }

    /* ================= TABLE ================= */

    private JComponent buildTable() {
        reportsPanel = new ReportsPanel();
        return reportsPanel;
    }

    /* ================= REFRESH DATA (KUNCI UTAMA) ================= */

    public void refreshData() {

        List<LaporanBarang> list = UserLaporanController.getAllLaporan();

        int total = list.size();
        int rusak = 0;
        int perbaikan = 0;

        for (LaporanBarang l : list) {
            String status = l.getStatus();
            if ("Rusak".equalsIgnoreCase(status)) {
                rusak++;
            } else if ("Perbaikan".equalsIgnoreCase(status)
                    || "Dalam Perbaikan".equalsIgnoreCase(status)) {
                perbaikan++;
            }
        }

        // 🔥 UPDATE CARD TANPA REBUILD UI
        totalPeralatanCard.setValue(String.valueOf(total));
        peralatanRusakCard.setValue(String.valueOf(rusak));
        dalamPerbaikanCard.setValue(String.valueOf(perbaikan));
        totalLaporanCard.setValue(String.valueOf(total));

        // 🔥 UPDATE TABLE
        reportsPanel.reloadData();
    }
}
