package view.admin;

import view.components.Sidebar;
import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {

    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);

    public AdminFrame() {
        setTitle("Dashboard Admin - Sistem Monitoring Lab");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar(this);
        add(sidebar, BorderLayout.WEST);

        mainPanel.add(new DashboardPanel(), "dashboard");
        mainPanel.add(new LaporanPanel(), "laporan");

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void showPage(String page) {
        cardLayout.show(mainPanel, page);
    }
}

