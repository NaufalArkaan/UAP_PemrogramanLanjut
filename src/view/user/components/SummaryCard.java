package view.user.components;

import util.UIStyleUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * SUMMARY CARD
 *
 * Digunakan di Dashboard
 * Value bisa di-update (Dummy sekarang, MySQL nanti)
 */
public class SummaryCard extends JPanel {

    // 🔥 SIMPAN LABEL SEBAGAI FIELD
    private final JLabel valueLabel;

    public SummaryCard(String title, String value) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
        setBorder(new EmptyBorder(14, 14, 14, 14));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(255, 255, 255, 210)); // semi transparent
        card.setBorder(BorderFactory.createLineBorder(
                new Color(220, 225, 235), 1, true
        ));

        // ===== VALUE =====
        valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(UIStyleUser.PRIMARY);
        valueLabel.setAlignmentX(CENTER_ALIGNMENT);

        // ===== TITLE =====
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIStyleUser.SMALL);
        titleLabel.setForeground(UIStyleUser.TEXT_MUTED);
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(titleLabel);
        card.add(Box.createVerticalGlue());

        add(card);
    }

    /**
     * UPDATE VALUE
     *
     * Dummy → dari memory
     * MySQL → hasil COUNT(*)
     */
    public void setValue(String value) {
        valueLabel.setText(value);
    }
}
