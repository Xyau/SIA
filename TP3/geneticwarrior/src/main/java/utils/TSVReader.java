package utils;

import genes.ItemPhenotype;
import interfaces.Phenotype;
import javafx.util.Pair;
import main.BonusType;
import main.ItemType;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.System.exit;

public class TSVReader {
    public static String pathTest = "./src/main/resources/testdata/";
    public static String pathFull = "./src/main/resources/fulldata/";
    public static Boolean fullData = false;
    public static Logger logger = Logger.getRootLogger();
    static {
        logger.addAppender(new ConsoleAppender());
    }

    public static String getPath(ItemType type){
        if(fullData && !new File(pathFull).exists()){
            fullData = false;
            logger.warn("No full data exists");
        }
        String path;
        switch (type){
            case ARMOR:
                path = (fullData?pathFull:pathTest) + "pecheras.tsv";
                break;
            case BOOTS:
                path = (fullData?pathFull:pathTest) + "botas.tsv";
                break;
            case WEAPON:
                path = (fullData?pathFull:pathTest) + "armas.tsv";
                break;
            case GLOVES:
                path = (fullData?pathFull:pathTest) + "guantes.tsv";
                break;
            case HELMET:
                path = (fullData?pathFull:pathTest) + "cascos.tsv";
                break;
            default:
                throw new IllegalArgumentException("Inexistant ItemType");
        }
        return path;
    }

    public static List<ItemPhenotype> parseFile(String pathfile, ItemType type){
        logger.info("Loading " + type + " into memory");
        List<ItemPhenotype> weapons = new LinkedList<>();
        File file = new File(pathfile);
        try {
            Scanner sc = new Scanner(file);
            sc.nextLine();
            while (sc.hasNextInt()){
                int id = sc.nextInt();
                Map<BonusType, Float> bonusMap = new HashMap<>();
                bonusMap.put(BonusType.STRENGHT, sc.nextFloat());
                bonusMap.put(BonusType.AGILITY, sc.nextFloat());
                bonusMap.put(BonusType.WISDOM, sc.nextFloat());
                bonusMap.put(BonusType.RESISTANCE, sc.nextFloat());
                bonusMap.put(BonusType.HEALTH, sc.nextFloat());
                weapons.add(new ItemPhenotype(type.toString(), type, bonusMap));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            exit(0);
        }
        return weapons;
    }

    public static Double amount=10000d;
    public static void parseFileAndRefillQueue(ItemType type, ConcurrentLinkedQueue<ItemPhenotype> queue, Random random){
        logger.info("Loading " + type + " into memory");
        List<ItemPhenotype> items = new LinkedList<>();
        File file = new File(getPath(type));
        Double ratio = amount/1000000.0;

        try {
            Scanner sc = new Scanner(file);
            sc.nextLine();
            String s="das";
            while (sc.hasNextInt()){
                if(random.nextFloat()<ratio){
                    int id = sc.nextInt();
                    Map<BonusType, Float> bonusMap = new HashMap<>();
                    bonusMap.put(BonusType.STRENGHT, sc.nextFloat());
                    bonusMap.put(BonusType.AGILITY, sc.nextFloat());
                    bonusMap.put(BonusType.WISDOM, sc.nextFloat());
                    bonusMap.put(BonusType.RESISTANCE, sc.nextFloat());
                    bonusMap.put(BonusType.HEALTH, sc.nextFloat());
                    queue.add(new ItemPhenotype(type.toString(), type, bonusMap));
                } else {
                    s=sc.nextLine();
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            exit(0);
        }

    }

}
