package se.robert.app.views.panels;

import se.robert.app.utilities.AppConfig;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A custom JPanel that renders a percentage scale bar drawn with a {@link Graphics2D}
 * instance.
 *
 * @author Robert Kullman
 */
public class ScaleBarPanel extends JPanel {


    /**
     * Constructs a {@code ScaleBarPanel} with a transparent background
     * and a fixed preferred width.
     * The height is determined by the parent layout, allowing the
     * scale bar to align dynamically with adjacent diagram components.
     */
    public ScaleBarPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(AppConfig.SCALE_BAR_WIDTH, 0));
    }

    /**
     * Paints the scale bar, with major and minor ticks every 20% and 10%, respectively.
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        try {
            int w = getWidth();
            int h = getHeight();

            int plotH = h - AppConfig.YEAR_LABEL_HEIGHT;
            if (plotH <= 0) {
                return;
            }

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

