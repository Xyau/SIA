package main;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;

public class NoireChart extends ApplicationFrame {

    private XYSeries maxFitness = new XYSeries( "Mejor Fitness" );
    private XYSeries minFitness = new XYSeries( "Peor Fitness" );
    private XYSeries avgFitness = new XYSeries( "Avg Fitness" );

    private XYSeriesCollection dataset = new XYSeriesCollection( );

    private XYPlot plot;
    public NoireChart( String applicationTitle, String chartTitle ) {
        super(applicationTitle);
        dataset.addSeries( maxFitness );
        dataset.addSeries( minFitness );
        dataset.addSeries( avgFitness );
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                "Generación" ,
                "Fitness" ,
                dataset,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        plot = xylineChart.getXYPlot( );

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesStroke( 0 , new BasicStroke( 0.001f ) );
        renderer.setSeriesShape(0, new Rectangle(0,0));
        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    public void updateChart(int gen,double max,double min,double avg){
        maxFitness.add(gen,max);
        minFitness.add(gen,min);
        avgFitness.add(gen,avg);
    }


    public static void main(String[] args){

        NoireChart chart = new NoireChart("Gráfico fitness por generación",
                "");
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
        int i = 1;
        while(i > 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            chart.updateChart(i++,2f*i,0.5f*i,1.5f*i);
        }
    }
}
