package view.admin;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {

    private boolean collapsed = false;
    private final int EXPANDED_WIDTH = 220;
    private final int COLLAPSED_WIDTH = 64;

    private JButton btnDashboard, btnLaporan, btnPeralatan, btnLogout;
    private JLabel lblTitle;

    public SidebarPanel(AdminFrame frame) {

        setLayout(new BorderLayout());
        setBackground(new Color(15, 23, 42));
        setPreferredSize(new Dimension(EXPANDED_WIDTH, 0));

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(15, 23, 42));
        header.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JButton btnHamburger = new JButton("☰");
        btnHamburger.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnHamburger.setForeground(Color.WHITE);
        btnHamburger.setBackground(new Color(15, 23, 42));
        btnHamburger.setFocusPainted(false);
        btnHamburger.setBorderPainted(false);
        btnHamburger.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblTitle = new JLabel("Admin Panel");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        header.add(btnHamburger, BorderLayout.WEST);
        header.add(lblTitle, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        // ===== MENU =====
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(15, 23, 42));
        menu.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        btnDashboard = createMenuButton("Dashboard", "🏠");
        btnPeralatan = createMenuButton("Data Peralatan", "🖥");
        btnLaporan = createMenuButton("Riwayat Laporan", "📄");

        menu.add(btnDashboard);
        menu.add(Box.createVerticalStrut(6));
        menu.add(btnPeralatan);
        menu.add(Box.createVerticalStrut(6));
        menu.add(btnLaporan);

        add(menu, BorderLayout.CENTER);

        // ===== LOGOUT =====
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(new Color(15, 23, 42));
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 8, 12, 8));

        btnLogout = new JButton("Logout");
        btnLogout.setBackground(new Color(220, 38, 38));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        bottom.add(btnLogout, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // ===== ACTIONS =====
        btnDashboard.addActionListener(e -> frame.showPage("dashboard"));
        btnPeralatan.addActionListener(e -> frame.showPage("peralatan"));
        btnLaporan.addActionListener(e -> frame.showPage("laporan"));
        btnLogout.addActionListener(e -> frame.logout());
        btnHamburger.addActionListener(e -> toggleSidebar());
    }

    private JButton createMenuButton(String text, String icon) {
        JButton btn = new JButton(icon + "  " + text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(999, 44));
        btn.setBackground(new Color(15, 23, 42));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(30, 41, 59));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(15, 23, 42));
            }
        });

        return btn;
    }

    // ===== COLLAPSE / EXPAND =====
    private void toggleSidebar() {
        collapsed = !collapsed;

        if (collapsed) {
            setPreferredSize(new Dimension(COLLAPSED_WIDTH, 0));
            lblTitle.setVisible(false);
            btnDashboard.setText("🏠");
            btnPeralatan.setText("🖥");
            btnLaporan.setText("📄");
            btnLogout.setText("⏻");
        } else {
            setPreferredSize(new Dimension(EXPANDED_WIDTH, 0));
            lblTitle.setVisible(true);
            btnDashboard.setText("🏠  Dashboard");
            btnPeralatan.setText("🖥  Data Peralatan");
            btnLaporan.setText("📄  Riwayat Laporan");
            btnLogout.setText("Logout");
        }

        revalidate();
        repaint();
    }
}
