package com.buptnsrc.leonard.charts;

import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
/**
 * Created by leonard on 16-4-13.
 */
public class LineChartMaker02 extends ApplicationFrame{

    public LineChartMaker02(String paramString,int[] data1,int[] data2)
    {
        super(paramString);
        JPanel localJPanel = createDemoPanel(data1,data2);
        localJPanel.setPreferredSize(new Dimension(1000, 540));
        setContentPane(localJPanel);
    }

    private static XYDataset createDataset(int[] data1, int[] data2)
    {
        XYSeries localXYSeries1 = new XYSeries("ClassicGa");
        for(int i=0;i<data1.length;i+=10){
            localXYSeries1.add(i+1,data1[i]);
        }
        XYSeries localXYSeries2 = new XYSeries("GaWithGeo");
        for(int i=0;i<data2.length;i+=10){
            localXYSeries2.add(i+1,data2[i]);
        }
        XYSeriesCollection localXYSeriesCollection = new XYSeriesCollection();
        localXYSeriesCollection.addSeries(localXYSeries1);
        localXYSeriesCollection.addSeries(localXYSeries2);
        return localXYSeriesCollection;
    }

    private static JFreeChart createChart(XYDataset paramXYDataset)
    {
        JFreeChart localJFreeChart = ChartFactory.createXYLineChart("Classic Ga", "X", "Y", paramXYDataset, PlotOrientation.VERTICAL, true, true, false);
        XYPlot localXYPlot = (XYPlot)localJFreeChart.getPlot();
        localXYPlot.setDomainPannable(true);
        localXYPlot.setRangePannable(true);
        XYLineAndShapeRenderer localXYLineAndShapeRenderer = (XYLineAndShapeRenderer)localXYPlot.getRenderer();
        localXYLineAndShapeRenderer.setBaseShapesVisible(true);
        localXYLineAndShapeRenderer.setBaseShapesFilled(true);
        NumberAxis localNumberAxis = (NumberAxis)localXYPlot.getRangeAxis();
        localNumberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return localJFreeChart;
    }

    public static JPanel createDemoPanel(int[] data1,int[] data2)
    {
        JFreeChart localJFreeChart = createChart(createDataset(data1,data2));
        ChartPanel localChartPanel = new ChartPanel(localJFreeChart);
        localChartPanel.setMouseWheelEnabled(true);
        return localChartPanel;
    }
}
