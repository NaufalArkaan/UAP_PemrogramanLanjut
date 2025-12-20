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
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                SwingUtilities.getWindowAncestor(this).dispose();
                new view.auth.LoginFrame();
            }
        });

        add(new JLabel());
        add(btnDashboard);
        add(btnLaporan);
        add(new JLabel());
        add(btnLogout);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(UIStyle.NORMAL);
        btn.setForeground(Color.WHITE);
        btn.setBackground(UIStyle.PRIMARY);
        btn.setFocusPainted(false);
        return btn;
    }
}

