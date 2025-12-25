package view.user.components;

import javax.swing.*;
import java.awt.*;

public class ShadowPanel extends JPanel {

    // Shadow dibuat lebih kecil & lebih transparan
    private int shadowSize = 6; // SEBELUM: 12
    private Color shadowColor = new Color(0, 0, 0, 28); // SEBELUM: 60

    public ShadowPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Gradasi shadow dari luar ke dalam (lebih natural)
        for (int i = shadowSize; i > 0; i--) {
            int alpha = (shadowColor.getAlpha() * i) / shadowSize;
            g2.setColor(new Color(
                    shadowColor.getRed(),
                    shadowColor.getGreen(),
                    shadowColor.getBlue(),
                    alpha
            ));

            g2.fillRoundRect(
                    i,
                    i,
                    width - (i * 2),
                    height - (i * 2),
                    18,
                    18
            );
        }

        g2.dispose();
    }
}