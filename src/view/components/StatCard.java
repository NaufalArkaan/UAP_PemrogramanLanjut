package view.components;

import javax.swing.*;
import java.awt.*;

public class StatCard extends JPanel {

    public StatCard(String title, String value, Icon icon, Color accent) {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200,200,200), 1),
                BorderFactory.createEmptyBorder(14,16,14,16)
        ));
        setPreferredSize(new Dimension(260, 120));

        // ===== ACCENT BAR =====
        JPanel accentBar = new JPanel();
        accentBar.setBackground(accent);
        accentBar.setPreferredSize(new Dimension(6, 0));
        add(accentBar, BorderLayout.WEST);

        // ===== MAIN CONTENT =====
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        add(content, BorderLayout.CENTER);

        // ===== TOP ROW (ICON + VALUE) =====
        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        topRow.setOpaque(false);

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setPreferredSize(new Dimension(32, 32));
        topRow.add(lblIcon);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28)); // 🔥 LEBIH TEBAL
        lblValue.setForeground(accent);
        topRow.add(lblValue);

        // ===== TITLE =====
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblTitle.setForeground(Color.DARK_GRAY);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(6, 2, 0, 0));

        content.add(topRow);
        content.add(lblTitle);
    }
}
