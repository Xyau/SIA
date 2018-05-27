package main;

import java.util.BitSet;

public class Fenotype extends BitSet {
    public Integer getOriginalGeneSize() {
        return originalGeneSize;
    }

    Integer originalGeneSize;

    public Fenotype(String binary) {
        originalGeneSize = binary.length();
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                this.set(i);
            }
        }
    }

    public Fenotype(Integer size){
        super(size);
        this.originalGeneSize = size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.originalGeneSize; i++) {
            builder.append(this.get(i)?"1":"0");
        }
        return builder.toString();
    }
}
