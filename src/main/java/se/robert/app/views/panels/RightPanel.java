package se.robert.app.views.panels;

import se.robert.app.records.YearData;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

public class RightPanel extends JPanel {

    DiagramPanel diagramPanel;
    public RightPanel() {
        setPreferredSize(new Dimension(700, 500));
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

    public void setData(List<YearData> data) {
        diagramPanel.setData(data);
        revalidate();
        repaint();
    }
}
