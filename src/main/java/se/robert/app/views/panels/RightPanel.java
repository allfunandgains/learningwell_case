package se.robert.app.views.panels;

import se.robert.app.records.YearData;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class RightPanel extends JPanel {

    DiagramPanel diagramPanel;
    public RightPanel() {
        setPreferredSize(new Dimension(700, 500));
        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());

        diagramPanel = new DiagramPanel();

        JScrollPane scrollPane = new JScrollPane(diagramPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        scrollPane.getViewport().setBackground(Color.DARK_GRAY);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void setData(java.util.List<YearData> data) {
        diagramPanel.setData(data);
    }
}
