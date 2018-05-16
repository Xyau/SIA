package utils;

import java.util.Iterator;
import java.util.stream.Stream;

public abstract class Utils {
    public static Iterator<Integer> iteratorFrom(Integer ... phenotypeValue){
        return Stream.of(phenotypeValue).iterator();
    }
}
