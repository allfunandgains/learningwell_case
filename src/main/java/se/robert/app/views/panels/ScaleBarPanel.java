package se.robert.app.views.panels;

import se.robert.app.utilities.AppConfig;

import javax.swing.*;
import java.awt.*;

public class ScaleBarPanel extends JPanel {



    public ScaleBarPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(AppConfig.SCALE_BAR_WIDTH, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            int w = getWidth();
            int h = getHeight();

            int plotH = h - AppConfig.YEAR_LABEL_HEIGHT;
            if (plotH <= 0) return;

            int usableH = Math.max(1, plotH - AppConfig.PADDING_TOP - AppConfig.PADDING_BOTTOM);

            int axisX = w - 10;
            int baseY = AppConfig.PADDING_TOP + usableH;

            g2.setColor(AppConfig.SCALE_BAR_COLOR);
            g2.drawLine(axisX, AppConfig.PADDING_TOP, axisX, baseY);

            // Ticks: 0..100 (major every 20)
            for (int p = 0; p <= 100; p += 10) {
                double ratio = p / 100.0;
                int y = baseY - (int) Math.round(ratio * usableH);

                boolean major = (p % 20 == 0);
                int tickLen = major ? 10 : 6;

                g2.drawLine(axisX - tickLen, y, axisX, y);

                if (major) {
                    String label = p + "%";
                    FontMetrics fm = g2.getFontMetrics();
                    int textW = fm.stringWidth(label);
                    g2.drawString(label, axisX - tickLen - 6 - textW, y + fm.getAscent() / 2 - 2);
                }
            }

        } finally {
            g2.dispose();
        }
    }
}

