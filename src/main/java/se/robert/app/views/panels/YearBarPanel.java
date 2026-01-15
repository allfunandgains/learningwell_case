package se.robert.app.views.panels;

import se.robert.app.utilities.AppConfig;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A Swing component that represents a single year in a bar chart. The panel displays
 * male and female values represented as percentages.
 *
 * @author Robert Kullman
 */
public class YearBarPanel extends JPanel {


    /** Percentage value for males (0–100). */
    private final double male;

    /** Percentage value for females (0–100). */
    private final double female;

    /** Label displaying the year at the bottom of the panel. */
    private final JLabel yearLabel;

    /**
     * Constructs a {@code YearBarPanel} for a specific year and its associated values.
     * @param year   the year to display as a label
     * @param male   male percentage value (0–100)
     * @param female female percentage value (0–100)
     * @param width  preferred width of the panel
     * @param height preferred height of the panel
     */
    public YearBarPanel(int year, double male, double female, int width, int height) {
        this.male = male;
        this.female = female;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);

        yearLabel = new JLabel(String.valueOf(year), SwingConstants.CENTER);
        yearLabel.setFont(AppConfig.YEAR_LABEL_FONT);
        add(yearLabel, BorderLayout.SOUTH);
    }

    /**
     * Paints the male and female bars for this year.
     * The available drawing area is calculated by subtracting the height of the
     * year label from the total component height. Bars are vertically scaled
     * based on their percentage values and drawn as filled rectangles.
     * @param g the {@link Graphics} context used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        try {
            int labelH = yearLabel.getHeight();
            int w = this.getWidth();
            int h = this.getHeight() - labelH;

            if (h <= 0) return;

            int paddingTop = 8;
            int paddingBottom = 8;
            int usableH = Math.max(1, h - paddingTop - paddingBottom);

            int gap = Math.max(4, w / 10);
            int barW = Math.max(6, (w - gap) / 2);

            double maleRatio = clamp01(male / 100.0);
            double femaleRatio = clamp01(female / 100.0);

            int maleH = (int) Math.round(maleRatio * usableH);
            int femaleH = (int) Math.round(femaleRatio * usableH);

            int xMale = 0;
            int xFemale = barW + gap;

            int baseY = paddingTop + usableH;
            int yMale = baseY - maleH;
            int yFemale = baseY - femaleH;

            // Male
            g2.setColor(AppConfig.MALE_BAR_COLOR);
            g2.fillRect(xMale, yMale, barW, maleH);

            // Female
            g2.setColor(AppConfig.FEMALE_BAR_COLOR);
            g2.fillRect(xFemale, yFemale, barW, femaleH);

        } finally {
            g2.dispose();
        }
    }

    /**
     * Clamps a value to the range 0.0, 1.0.
     * @param value the value to clamp
     * @return a value between 0.0 and 1.0
     */
    private static double clamp01(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }
}
