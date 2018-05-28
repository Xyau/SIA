package main;

import experiment.Experiment;
import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import utils.CSVWriter;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) {
        Appender  appender = new ConsoleAppender(new SimpleLayout());
        appender.setName("root");
        logger.addAppender(appender);

        Experiment experiment = null;
        Map<String,List<Double>> timeseries = new HashMap<>();
        for(String path: args){
            try {
                experiment = Configuration.getExperiment(path);
            } catch (IOException e) {
                e.printStackTrace();
                exit(1);
            }
            timeseries.putAll(experiment.run());
        }

        String out = CSVWriter.getTimeSeriesString(timeseries);
        CSVWriter.writeOutput("out.csv",out);
        System.out.println(out);
    }

}
