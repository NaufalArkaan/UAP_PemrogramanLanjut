package view.components;

import javax.swing.*;
import java.awt.*;
import util.UIStyle;

public class StatusBadge extends JLabel {

    public StatusBadge(String status) {
        setText(status);
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setFont(UIStyle.SMALL);

        switch (status) {
            case "Rusak" -> setBackground(new Color(255, 99, 99));
            case "Perbaikan" -> setBackground(new Color(255, 193, 7));
            default -> setBackground(new Color(76, 175, 80));
        }

        setForeground(Color.WHITE);
    }
}
