package utils;

import javafx.util.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CSVWriter {

    public static String getTimeSeriesString(Map<String,List<Double>> timeseries){
        StringBuilder builder = new StringBuilder();
        List<Map.Entry<String ,List<Double>>> list = new ArrayList<>();
        list.addAll(timeseries.entrySet());
        list.stream().map(Map.Entry::getKey).map(x->x+",").forEachOrdered(builder::append);
        builder.append("\n");
        Integer i =0;
        Boolean aListStillHasElements = true;
        while (aListStillHasElements){
            aListStillHasElements = false;
            for (List<Double> curr:list.stream().map(Map.Entry::getValue).collect(Collectors.toList())){
                if(i<curr.size()){
                    builder.append(curr.get(i));
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
