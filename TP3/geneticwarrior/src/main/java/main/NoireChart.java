package main;

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

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoireChart extends ApplicationFrame {

    private XYSeries maxFitness = new XYSeries( "Mejor Fitness" );
    private XYSeries minFitness = new XYSeries( "Peor Fitness" );
    private XYSeries avgFitness = new XYSeries( "Avg Fitness" );

    private Map<String,XYSeries> seriesList = new HashMap<>();

    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;

    private XYSeriesCollection dataset = new XYSeriesCollection( );

    private JFreeChart xylineChart;
    private XYPlot plot;
    public NoireChart( String applicationTitle, String chartTitle ) {
        super(applicationTitle);
        dataset.addSeries( maxFitness );
        dataset.addSeries( minFitness );
        dataset.addSeries( avgFitness );
         xylineChart = ChartFactory.createXYLineChart(
                chartTitle ,
                "Generación" ,
                "Fitness" ,
                dataset,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 860 , 567 ) );

        plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesStroke( 0 , new BasicStroke( 1f ) );
        renderer.setSeriesShape(0, new Rectangle(0,0));
        renderer.setSeriesPaint( 0 , Color.RED );

        renderer.setSeriesStroke( 1 , new BasicStroke( 1f ) );
        renderer.setSeriesShape(1, new Rectangle(0,0));
        renderer.setSeriesPaint( 1 , Color.YELLOW );

        renderer.setSeriesStroke( 2 , new BasicStroke( 1f ) );
        renderer.setSeriesShape(2, new Rectangle(0,0));
        renderer.setSeriesPaint( 2 , Color.green );

        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    public void updateChart(int gen, String series, Double value) {
        seriesList.get(series).add(gen,value);
        if (value> this.max ){
            this.max = value;
        } if (value < this.min ){
            this.min = min;
        }
    }

    public void updateChart(int gen, double max, double min, double avg){
        maxFitness.add(gen,max);
        minFitness.add(gen,min);
        avgFitness.add(gen,avg);
        if (max > this.max ){
            this.max = max;
        } if (min < this.min ){
            this.min = min;
        }
        xylineChart.getXYPlot().getRangeAxis().setRange(this.min*.95, this.max*1.05);
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
