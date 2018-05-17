package utils;

import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CSVWriter {

    public static String getTimeSeriesString(List<Pair<String,List<Double>>> timeseries){
        StringBuilder builder = new StringBuilder();
        timeseries.stream().map(Pair::getKey).map(x->x+",").forEachOrdered(builder::append);
        builder.append("\n");
        Integer i =0;
        Boolean aListStillHasElements = true;
        while (aListStillHasElements){
            aListStillHasElements = false;
            for (List<Double> list:timeseries.stream().map(Pair::getValue).collect(Collectors.toList())){
                if(i<list.size()){
                    builder.append(list.get(i));
                    aListStillHasElements = true;
                } else {
                    builder.append(" ");
                }
                builder.append(",");
            }
            builder.setLength(builder.length()-1);
            builder.append("\n");
            i++;
        }
        return builder.toString();
    }
}
