package view.admin;

import view.components.StatCard;
import view.components.ShadowPanel;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    public DashboardPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(245,246,250));
        setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        // Title area
        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        JLabel title = new JLabel("Dashboard Monitoring");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        top.add(title, BorderLayout.NORTH);

        JLabel subtitle = new JLabel("Sistem Monitoring Kerusakan Peralatan Laboratorium Informatika");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(Color.DARK_GRAY);
        top.add(subtitle, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);

        // cards area
        JPanel cardsWrapper = new JPanel(new GridLayout(1,4,16,16));
        cardsWrapper.setOpaque(false);
        cardsWrapper.setBorder(BorderFactory.createEmptyBorder(12,0,12,0));

        // create cards (use StatCard)
        cardsWrapper.add(new StatCard(
                "Total Peralatan",
                "156",
                UIManager.getIcon("OptionPane.informationIcon"),
                new Color(28,81,204)
        ));

        cardsWrapper.add(new StatCard(
                "Peralatan Rusak",
                "12",
                UIManager.getIcon("OptionPane.errorIcon"),
                new Color(200,40,40)
        ));

        cardsWrapper.add(new StatCard(
                "Dalam Perbaikan",
                "5",
                UIManager.getIcon("OptionPane.warningIcon"),
                new Color(230,160,20)
        ));

        cardsWrapper.add(new StatCard(
                "Total Laporan",
                "23",
                UIManager.getIcon("OptionPane.questionIcon"),
                new Color(0,160,80)
        ));

        // put cards inside a panel with padding + border to mimic sample
        JPanel cardsContainer = new JPanel(new BorderLayout());
        cardsContainer.setOpaque(false);
        JPanel inner = new JPanel(new BorderLayout());
        inner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(6,6,6,6),
                BorderFactory.createEmptyBorder(0,0,0,0)
        ));
        inner.setOpaque(false);
        inner.add(cardsWrapper, BorderLayout.CENTER);
        cardsContainer.add(inner, BorderLayout.CENTER);

        add(cardsContainer, BorderLayout.CENTER);

        // reports table below
        ReportsPanel reports = new ReportsPanel();
        add(reports, BorderLayout.SOUTH);
    }
}
