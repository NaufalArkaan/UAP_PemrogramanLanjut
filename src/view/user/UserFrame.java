package view.user;

import model.User;
import util.UIStyleUser;
import view.user.form.FormLaporanPanel;
import view.user.riwayat.RiwayatLaporanPanel;

import javax.swing.*;
import java.awt.*;

/**
 * USER FRAME
 *
 * ===============================
 * Mengatur navigasi antar halaman
 * Panel dibuat SATU KALI
 * Data di-refresh setiap berpindah halaman
 */
public class UserFrame extends JFrame {

    private final User user;

    private CardLayout cardLayout;
    private JPanel contentPanel;

    // ===== SIMPAN PANEL SEBAGAI FIELD =====
    private UserDashboardPanel dashboardPanel;
    private FormLaporanPanel formLaporanPanel;
    private RiwayatLaporanPanel riwayatLaporanPanel;

    /* ===== ROUTES ===== */
    public static final String DASHBOARD = "DASHBOARD";
    public static final String FORM_LAPORAN = "FORM_LAPORAN";
    public static final String RIWAYAT_LAPORAN = "RIWAYAT_LAPORAN";

    public UserFrame(User user) {
        this.user = user;
        initUI();
    }

    /* ================= INIT UI ================= */

    private void initUI() {
        setTitle("User - Sistem Monitoring Laboratorium");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 32));
        centerWrapper.setBackground(UIStyleUser.BG);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setPreferredSize(new Dimension(1100, 720));
        contentPanel.setOpaque(false);

        dashboardPanel = new UserDashboardPanel(this, user);
        formLaporanPanel = new FormLaporanPanel(this);
        riwayatLaporanPanel = new RiwayatLaporanPanel(this);

        contentPanel.add(dashboardPanel, DASHBOARD);
        contentPanel.add(formLaporanPanel, FORM_LAPORAN);
        contentPanel.add(riwayatLaporanPanel, RIWAYAT_LAPORAN);

        centerWrapper.add(contentPanel);
        add(centerWrapper, BorderLayout.CENTER);

        showDashboard();
        setVisible(true);
    }

    /* ================= NAVIGATION ================= */

    public void showDashboard() {
        dashboardPanel.refreshSummary();
        cardLayout.show(contentPanel, DASHBOARD);
    }

    public void showFormLaporan() {
        cardLayout.show(contentPanel, FORM_LAPORAN);
    }

    public void showRiwayatLaporan() {
        riwayatLaporanPanel.reload();
        cardLayout.show(contentPanel, RIWAYAT_LAPORAN);
    }
}
