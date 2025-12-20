package view.components;

import util.UIStyle;

import javax.swing.*;
import java.awt.*;

public class StatCard extends JPanel {

    public StatCard(String title, String value, Color color) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setForeground(color);

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(UIStyle.SMALL);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        add(lblValue, BorderLayout.CENTER);
        add(lblTitle, BorderLayout.SOUTH);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 120);
    }
}
