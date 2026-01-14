package se.robert.app.views.panels;

import se.robert.app.utilities.AppConfig;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class YearBarPanel extends JPanel {

    private final double male;
    private final double female;

    private final JLabel yearLabel;

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

}
