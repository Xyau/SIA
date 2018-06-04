package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CSVWriter {

    static String separator = ",";

    public static String getTimeSeriesString(Map<String,List<Double>> timeseries){
        StringBuilder builder = new StringBuilder();
        List<Map.Entry<String ,List<Double>>> list = new ArrayList<>();
        list.addAll(timeseries.entrySet());
        list.sort(Comparator.comparing(x->x.getKey().split(" ")[1]));
        builder.append("Generation"+separator);
        list.stream().map(Map.Entry::getKey).map(x->x+separator).forEachOrdered(builder::append);
        builder.append("\n");
        Integer i =0;
        Boolean aListStillHasElements = true;
        String s;
        while (aListStillHasElements){
            aListStillHasElements = false;
            builder.append(i).append(separator);
            for (List<Double> curr:list.stream().map(Map.Entry::getValue).collect(Collectors.toList())){
                if(i<curr.size()){
                    s = curr.get(i).toString();
                    builder.append(s, 0, Math.min(6,s.length()));
                    if(i+1<curr.size()){
                        aListStillHasElements = true;
                    }
                }
                builder.append(separator);
            }
            builder.setLength(builder.length()-separator.length());
            builder.append("\n");
            i++;
        }
        return builder.toString();
    }

    public static void writeOutput(String path, String content){
        try {
            BufferedWriter writer= new BufferedWriter(new FileWriter(path));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
