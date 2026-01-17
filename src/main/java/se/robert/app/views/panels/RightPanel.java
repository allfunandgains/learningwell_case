package se.robert.app.views.panels;

import se.robert.app.records.YearData;
import se.robert.app.utilities.AppConfig;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

/**
 * A JPanel responsible for organizing GUI components related to showing chart data.
 *
 * @author Robert Kullman
 */
public class RightPanel extends JPanel {

    /** JPanel responsible for showing the stack chart. */
    private final DiagramPanel diagramPanel;

    /**
     * Constructs the panel and initializes GUI components and child panels.
     */
    public RightPanel() {
        setPreferredSize(new Dimension(AppConfig.RIGHT_PANEL_PREFERRED_WIDTH, AppConfig.RIGHT_PANEL_PREFERRED_HEIGHT));
        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());

        ScaleBarPanel scaleBar = new ScaleBarPanel();
        diagramPanel = new DiagramPanel();

        JScrollPane scrollPane = new JScrollPane(diagramPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane, BorderLayout.CENTER);
        scrollPane.setRowHeaderView(scaleBar);
        scrollPane.getRowHeader().setBackground(Color.DARK_GRAY);
        scaleBar.setBackground(Color.DARK_GRAY);
        scaleBar.setOpaque(true);
    }


    /**
     * Updates the diagram with new data and repaints the panel.
     * @param data List of {@link YearData} objects.
     */
    public void setData(List<YearData> data) {
        diagramPanel.setData(data);
        revalidate();
        repaint();
    }
}
