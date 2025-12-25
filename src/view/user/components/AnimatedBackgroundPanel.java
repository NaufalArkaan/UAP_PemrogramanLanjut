package view.user.components;

import util.UIStyleUser;

import javax.swing.*;
import java.awt.*;

public class AnimatedBackgroundPanel extends JPanel {

    private int offset = 0;

    public AnimatedBackgroundPanel() {
        setOpaque(true);

        Timer timer = new Timer(40, e -> {
            offset += 1;
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Gradient background
        GradientPaint gp = new GradientPaint(
                0, 0,
                new Color(
                        UIStyleUser.PRIMARY.getRed(),
                        UIStyleUser.PRIMARY.getGreen(),
                        UIStyleUser.PRIMARY.getBlue(),
                        25
                ),
                0, h,
                Color.WHITE
        );
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);

        // Floating circles (very subtle)
        g2.setColor(new Color(120, 140, 220, 30));
        for (int i = 0; i < 8; i++) {
            int x = (i * 180 + offset) % w;
            int y = (i * 120) % h;
            g2.fillOval(x, y, 100, 100);
        }
    }
}
