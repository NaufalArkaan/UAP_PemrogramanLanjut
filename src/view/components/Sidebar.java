package view.components;

import javax.swing.*;
import java.awt.*;
import view.admin.AdminFrame;
import util.UIStyle;

public class Sidebar extends JPanel {

    public Sidebar(AdminFrame frame) {

        setBackground(UIStyle.SIDEBAR);
        setPreferredSize(new Dimension(220, 0));
        setLayout(new GridLayout(6, 1, 10, 10));

        JButton btnDashboard = createButton("Dashboard");
        JButton btnLaporan = createButton("Laporan Kerusakan");
        JButton btnLogout = createButton("Logout");

        btnDashboard.addActionListener(e -> frame.showPage("dashboard"));
        btnLaporan.addActionListener(e -> frame.showPage("laporan"));
        btnLogout.addActionListener(e -> {
            frame.dispose();
            new view.auth.LoginFrame();
        });

        add(btnDashboard);
        add(btnLaporan);
        add(new JLabel()); // spacer
        add(btnLogout);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(UIStyle.SIDEBAR);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(UIStyle.SIDEBAR.darker()));
        return btn;
    }
}
