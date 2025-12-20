package view.admin;

import view.admin.SidebarPanel;
import javax.swing.*;
import java.awt.*;
import view.auth.LoginFrame;

public class AdminFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    public AdminFrame() {
        setTitle("Dashboard Admin - Sistem Monitoring Lab");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        SidebarPanel sidebar = new SidebarPanel(this);
        add(sidebar, BorderLayout.WEST);

        // ===== TOP BAR =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(14, 64, 160));
        topBar.setPreferredSize(new Dimension(0, 45));

        JLabel title = new JLabel("  Dashboard Admin - Sistem Monitoring Lab");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        topBar.add(title, BorderLayout.WEST);

        add(topBar, BorderLayout.NORTH);

        // ===== CONTENT (CARD LAYOUT) =====
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(245, 246, 250));

        contentPanel.add(new DashboardPanel(), "dashboard");
        contentPanel.add(new ReportsPanel(), "laporan");

        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ===== INI YANG DIBUTUHKAN SIDEBAR =====
    public void showPage(String page) {
        cardLayout.show(contentPanel, page);
    }

    public void logout() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dispose();               // tutup AdminFrame
            new LoginFrame();        // kembali ke login
        }
    }

}
