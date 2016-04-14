package com.buptnsrc.leonard.charts;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPosition;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.CategoryLabelWidthType;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.text.TextBlockAnchor;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.TextAnchor;

public class BarChartMaker extends ApplicationFrame
{
    public BarChartMaker(String paramString,int... datas)
    {
        super(paramString);
        JPanel localJPanel = createDemoPanel(datas);
        localJPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(localJPanel);
    }

    private static CategoryDataset createDataset(int... datas)
    {
        DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
//        localDefaultCategoryDataset.addValue(23.0D, "Series 1", "London");
//        localDefaultCategoryDataset.addValue(14.0D, "Series 1", "New York");
//        localDefaultCategoryDataset.addValue(14.0D, "Series 1", "Istanbul");
//        localDefaultCategoryDataset.addValue(14.0D, "Series 1", "Cairo");
//        localDefaultCategoryDataset.addValue(13.0D, "Series 2", "London");
//        localDefaultCategoryDataset.addValue(19.0D, "Series 2", "New York");
//        localDefaultCategoryDataset.addValue(19.0D, "Series 2", "Istanbul");
//        localDefaultCategoryDataset.addValue(19.0D, "Series 2", "Cairo");
//        localDefaultCategoryDataset.addValue(7.0D, "Series 3", "London");
//        localDefaultCategoryDataset.addValue(9.0D, "Series 3", "New York");
//        localDefaultCategoryDataset.addValue(9.0D, "Series 3", "Istanbul");
        localDefaultCategoryDataset.addValue(datas[0], "MaxMin", "");
        localDefaultCategoryDataset.addValue(datas[1], "MinMin", "");
        localDefaultCategoryDataset.addValue(datas[2], "ClassicGa", "");
        localDefaultCategoryDataset.addValue(datas[3], "GaWithGeo", "");
        return localDefaultCategoryDataset;
    }

    private static JFreeChart createChart(CategoryDataset paramCategoryDataset)
    {
        JFreeChart localJFreeChart = ChartFactory.createBarChart3D("Ga With Geo", "Algorithms", "Process Time", paramCategoryDataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot localCategoryPlot = (CategoryPlot)localJFreeChart.getPlot();
        localCategoryPlot.setForegroundAlpha(1.0F);
        localCategoryPlot.setRangePannable(true);
        CategoryAxis localCategoryAxis = localCategoryPlot.getDomainAxis();
        CategoryLabelPositions localCategoryLabelPositions = localCategoryAxis.getCategoryLabelPositions();
        CategoryLabelPosition localCategoryLabelPosition = new CategoryLabelPosition(RectangleAnchor.LEFT, TextBlockAnchor.CENTER_LEFT, TextAnchor.CENTER_LEFT, 0.0D, CategoryLabelWidthType.RANGE, 0.3F);
        localCategoryAxis.setCategoryLabelPositions(CategoryLabelPositions.replaceLeftPosition(localCategoryLabelPositions, localCategoryLabelPosition));
        return localJFreeChart;
    }

    public static JPanel createDemoPanel(int... datas)
    {
        JFreeChart localJFreeChart = createChart(createDataset(datas));
        ChartPanel localChartPanel = new ChartPanel(localJFreeChart);
        localChartPanel.setMouseWheelEnabled(true);
        return localChartPanel;
    }

    public static void main(String[] paramArrayOfString)
    {
        BarChartMaker localBarChart3DDemo2 = new BarChartMaker("JFreeChart: BarChart3DDemo2.java");
        localBarChart3DDemo2.pack();
        RefineryUtilities.centerFrameOnScreen(localBarChart3DDemo2);
        localBarChart3DDemo2.setVisible(true);
    }
}
