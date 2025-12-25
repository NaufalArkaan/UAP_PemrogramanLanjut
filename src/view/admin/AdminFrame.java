package view.admin;

import view.auth.LoginFrame;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    // ===== SIMPAN PANEL (SATU INSTANCE) =====
    private DashboardPanel dashboardPanel;
    private ReportsPanel reportsPanel;
    private DataPeralatanPanel dataPeralatanPanel;

    public AdminFrame() {
        setTitle("Dashboard Admin - Sistem Monitoring Lab");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        /* ===== SIDEBAR (TIDAK DIUBAH) ===== */
        SidebarPanel sidebar = new SidebarPanel(this);
        add(sidebar, BorderLayout.WEST);

        /* ===== TOP BAR ===== */
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(14, 64, 160));
        topBar.setPreferredSize(new Dimension(0, 45));

        JLabel title = new JLabel("  Dashboard Admin - Sistem Monitoring Lab");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        topBar.add(title, BorderLayout.WEST);

        add(topBar, BorderLayout.NORTH);

        /* ===== CONTENT ===== */
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 246, 250));

        // 🔥 INIT PANEL SEKALI (WAJIB)
        dashboardPanel = new DashboardPanel();
        reportsPanel = new ReportsPanel();
        dataPeralatanPanel = new DataPeralatanPanel();

        contentPanel.add(dashboardPanel, "dashboard");
        contentPanel.add(reportsPanel, "laporan");
        contentPanel.add(dataPeralatanPanel, "peralatan");

        add(contentPanel, BorderLayout.CENTER);

        /* ===== AUTO SYNC SAAT WINDOW AKTIF ===== */
        addWindowFocusListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                dataPeralatanPanel.reloadData();
                reportsPanel.reloadData();
                refreshDashboard();
            }
        });

        showPage("dashboard");
        setVisible(true);
    }

    /* ===== DIPANGGIL SIDEBAR ===== */
    public void showPage(String page) {

        // 🔥 AUTO RELOAD SAAT PINDAH PAGE
        switch (page) {
            case "dashboard" -> refreshDashboard();
            case "laporan" -> reportsPanel.reloadData();
            case "peralatan" -> dataPeralatanPanel.reloadData();
        }

        cardLayout.show(contentPanel, page);
    }

    /* ===== REFRESH DASHBOARD ===== */
    public void refreshDashboard() {
        if (dashboardPanel != null) {
            dashboardPanel.refreshData();
        }
    }

    /* ===== LOGOUT ===== */
    public void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            new LoginFrame();
        }
    }

    /* ===== DETAIL LAPORAN ===== */
    public void openDetailLaporan(String id) {
        DetailLaporanPanel detail = new DetailLaporanPanel(this, id);
        contentPanel.add(detail, "detail");
        cardLayout.show(contentPanel, "detail");
    }

    /* ===== TAMBAH DATA ===== */
    public void openTambahData() {
        TambahDataPeralatanPanel tambah = new TambahDataPeralatanPanel(this);
        contentPanel.add(tambah, "tambah");
        cardLayout.show(contentPanel, "tambah");
    }
}
