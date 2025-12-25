package view.user.components;

import util.UIStyleUser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActionCard extends JPanel {

    private final Color accent;
    private final LineBorder normalBorder;
    private final LineBorder hoverBorder;

    public ActionCard(String title, String subtitle, Color accent) {

        this.accent = accent;

        this.normalBorder = new LineBorder(
                new Color(220, 225, 235), 1, true
        );
        this.hoverBorder = new LineBorder(accent, 2, true);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 🔥 WAJIB: card harus solid
        setOpaque(true);
        setBackground(Color.WHITE);

        setBorder(BorderFactory.createCompoundBorder(
                normalBorder,
                new EmptyBorder(18, 18, 18, 18)
        ));

        setPreferredSize(new Dimension(460, 200));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setAlignmentX(CENTER_ALIGNMENT);

        /* ================= ICON ================= */

        JPanel iconBox = new JPanel(new GridBagLayout());
        iconBox.setPreferredSize(new Dimension(64, 64));
        iconBox.setMaximumSize(new Dimension(64, 64));
        iconBox.setOpaque(true);
        iconBox.setBackground(new Color(
                accent.getRed(),
                accent.getGreen(),
                accent.getBlue(),
                40
        ));
        iconBox.setBorder(new LineBorder(accent, 2, true));
        iconBox.setAlignmentX(CENTER_ALIGNMENT);

        JLabel icon = new JLabel(accent.equals(UIStyleUser.DANGER) ? "!" : "📄");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 22));
        icon.setForeground(accent);
        iconBox.add(icon);

        /* ================= TEXT ================= */

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(UIStyleUser.H2);
        titleLabel.setForeground(new Color(35, 40, 60));
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(UIStyleUser.BODY);
        subtitleLabel.setForeground(UIStyleUser.TEXT_MUTED);
        subtitleLabel.setAlignmentX(CENTER_ALIGNMENT);

        /* ================= LAYOUT ================= */

        add(Box.createVerticalGlue());
        add(iconBox);
        add(Box.createVerticalStrut(14));
        add(titleLabel);
        add(Box.createVerticalStrut(6));
        add(subtitleLabel);
        add(Box.createVerticalGlue());

        /* ================= HOVER EFFECT ================= */

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(248, 251, 255)); // solid, bukan alpha
                setBorder(BorderFactory.createCompoundBorder(
                        hoverBorder,
                        new EmptyBorder(18, 18, 18, 18)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
                setBorder(BorderFactory.createCompoundBorder(
                        normalBorder,
                        new EmptyBorder(18, 18, 18, 18)
                ));
            }
        });
    }
}
