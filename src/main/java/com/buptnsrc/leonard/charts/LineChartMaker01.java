package com.buptnsrc.leonard.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RefineryUtilities;

/**
 * Created by leonard on 16-4-13.
 */
public class LineChartMaker01 extends ApplicationFrame{
    public LineChartMaker01(String paramString)
    {
        super(paramString);
        JPanel localJPanel = createDemoPanel();
        localJPanel.setPreferredSize(new Dimension(1000, 400)); //size of the chart interface
        setContentPane(localJPanel);
    }

    private static CategoryDataset createDataset()
    {
        DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
        localDefaultCategoryDataset.addValue(273.0D, "Classes", "JDK 1.0");
        localDefaultCategoryDataset.addValue(597.0D, "Classes", "JDK 1.1");
        localDefaultCategoryDataset.addValue(779.0D, "Classes", "JDK 1.2");
        localDefaultCategoryDataset.addValue(1020.0D, "Classes", "JDK 1.3");
        localDefaultCategoryDataset.addValue(1500.0D, "Classes", "JDK 1.4");
        localDefaultCategoryDataset.addValue(2500.0D, "Classes", "JDK 1.5");
        return localDefaultCategoryDataset;
    }

    private static JFreeChart createChart(CategoryDataset paramCategoryDataset)
    {
        //second parameter: horizontal name, third parameter: vertical name
        JFreeChart localJFreeChart = ChartFactory.createLineChart("Java Standard Class Library", null, "Class Count", paramCategoryDataset, PlotOrientation.VERTICAL, false, true, false);
        localJFreeChart.addSubtitle(new TextTitle("Number of Classes By Release"));
        TextTitle localTextTitle = new TextTitle("Source: Java In A Nutshell (5th Edition) by David Flanagan (O'Reilly)");
        localTextTitle.setFont(new Font("SansSerif", 0, 10));
        localTextTitle.setPosition(RectangleEdge.BOTTOM);
        localTextTitle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        localJFreeChart.addSubtitle(localTextTitle);
        CategoryPlot localCategoryPlot = (CategoryPlot)localJFreeChart.getPlot();
        localCategoryPlot.setRangePannable(true);
        localCategoryPlot.setRangeGridlinesVisible(false);
        URL localURL = LineChartMaker01.class.getClassLoader().getResource("OnBridge11small.png");
        if (localURL != null)
        {
            ImageIcon localObject = new ImageIcon(localURL);
            localJFreeChart.setBackgroundImage(((ImageIcon)localObject).getImage());
            localCategoryPlot.setBackgroundPaint(null);
        }
        Object localObject = (NumberAxis)localCategoryPlot.getRangeAxis();
        ((NumberAxis)localObject).setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ChartUtilities.applyCurrentTheme(localJFreeChart);
        LineAndShapeRenderer localLineAndShapeRenderer = (LineAndShapeRenderer)localCategoryPlot.getRenderer();
        localLineAndShapeRenderer.setBaseShapesVisible(true);
        localLineAndShapeRenderer.setDrawOutlines(true);
        localLineAndShapeRenderer.setUseFillPaint(true);
        localLineAndShapeRenderer.setBaseFillPaint(Color.white);
        localLineAndShapeRenderer.setSeriesStroke(0, new BasicStroke(3.0F));
        localLineAndShapeRenderer.setSeriesOutlineStroke(0, new BasicStroke(2.0F));
        localLineAndShapeRenderer.setSeriesShape(0, new Ellipse2D.Double(-5.0D, -5.0D, 10.0D, 10.0D));
        return (JFreeChart)localJFreeChart;
    }

    public static JPanel createDemoPanel()
    {
        JFreeChart localJFreeChart = createChart(createDataset());
        ChartPanel localChartPanel = new ChartPanel(localJFreeChart);
        localChartPanel.setMouseWheelEnabled(true);
        return localChartPanel;
    }
}
