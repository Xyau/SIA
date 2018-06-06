package main;

import experiment.Experiment;
import org.apache.log4j.*;
import utils.CSVWriter;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static java.lang.System.exit;

public class Main {
    static Logger logger = Logger.getRootLogger();

    public static void main(String[] args) {
        Appender  appender = new ConsoleAppender(new SimpleLayout());
        appender.setName("root");
        logger.addAppender(appender);
        logger.setLevel(Level.INFO);

        Experiment experiment = null;
        ConcurrentMap<String,List<Double>> timeseries = new ConcurrentSkipListMap<>();
        List<Experiment> experiments = new ArrayList<>();
        for(String path: args){
            try {
                experiment = Configuration.getExperiment(path);
            } catch (IOException e) {
                e.printStackTrace();
                exit(1);
            }
            experiments.add(experiment);
//            timeseries.putAll(experiment.run());
        }

        experiments.parallelStream().map(exp->exp.run()).forEach(timeseries::putAll);

        String out = CSVWriter.getTimeSeriesString(timeseries);
        CSVWriter.writeOutput("out.csv",out);
//        System.out.println(out);
    }

}
