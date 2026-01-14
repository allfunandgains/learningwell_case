package se.robert.app.views.panels;

import se.robert.app.records.YearData;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DiagramPanel extends JPanel {

    private final JPanel columnsPanel;
    private final int columnWidth;
    private final int chartHeight;


    public DiagramPanel() {
        this(64, 220);
    }

    public DiagramPanel(int columnWidth, int chartHeight) {
        this.columnWidth = columnWidth;
        this.chartHeight = chartHeight;

        setLayout(new BorderLayout());

        columnsPanel = new JPanel();
        columnsPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        columnsPanel.setLayout(new BoxLayout(columnsPanel, BoxLayout.X_AXIS));

        add(columnsPanel, BorderLayout.CENTER);
    }

    /**
     * Updates the chart with new data.
     * Call this from the EDT (Swing thread).
     */
    public void setData(List<YearData> data) {
        columnsPanel.removeAll();

        if (data == null || data.isEmpty()) {
            columnsPanel.add(createEmptyStateLabel());
            revalidate();
            repaint();
            return;
        }

        List<YearData> sorted = new ArrayList<>(data);
        sorted.sort(Comparator.comparingInt(YearData::year));

        for (int i = 0; i < sorted.size(); i++) {
            YearData yd = sorted.get(i);

            YearBarPanel column = new YearBarPanel(
                    yd.year(),
                    yd.maleValue(),
                    yd.femaleValue(),
                    columnWidth,
                    chartHeight
            );

            columnsPanel.add(column);

            if (i < sorted.size() - 1) {
                columnsPanel.add(Box.createHorizontalStrut(12));
            }
        }

        revalidate();
        repaint();
    }

    private JComponent createEmptyStateLabel() {
        JLabel label = new JLabel("No data to display", SwingConstants.CENTER);
        label.setBorder(new EmptyBorder(24, 24, 24, 24));
        return label;
    }
}
