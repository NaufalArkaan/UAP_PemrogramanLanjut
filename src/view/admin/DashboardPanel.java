package view.admin;

import util.UIStyle;
import view.components.StatCard;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.BACKGROUND);

        // ===== TITLE =====
        JLabel title = new JLabel("Dashboard Monitoring");
        title.setFont(UIStyle.TITLE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // ===== CARD WRAPPER =====
        JPanel cardWrapper = new JPanel(new GridLayout(1, 4, 20, 20));
        cardWrapper.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        cardWrapper.setBackground(UIStyle.BACKGROUND);

        cardWrapper.add(new StatCard("Total Peralatan", "156", Color.BLUE));
        cardWrapper.add(new StatCard("Rusak", "12", Color.RED));
        cardWrapper.add(new StatCard("Perbaikan", "5", Color.ORANGE));
        cardWrapper.add(new StatCard("Total Laporan", "23", Color.GREEN));

        add(title, BorderLayout.NORTH);
        add(cardWrapper, BorderLayout.CENTER);
    }
}
