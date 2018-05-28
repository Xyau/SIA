package utils;

import genes.ItemPhenotype;
import interfaces.Phenotype;
import main.BonusType;
import main.ItemType;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.exit;

public class TSVReader {
    public static String pathTest = "./src/main/resources/testdata/";
    public static String pathFull = "./src/main/resources/fulldata/";
    public static Boolean fullData = false;
    public static Logger logger = Logger.getRootLogger();
    static {
        logger.addAppender(new ConsoleAppender());
    }

    public static List<ItemPhenotype> parseFile(ItemType type){
        if(fullData && !new File(pathFull).exists()){
            fullData = false;
            logger.warn("No full data exists");
        }
        switch (type){
            case ARMOR:
                return parseFile((fullData?pathFull:pathTest) + "pecheras.tsv",type);
            case BOOTS:
                return parseFile((fullData?pathFull:pathTest) + "botas.tsv",type);
            case WEAPON:
                return parseFile((fullData?pathFull:pathTest)+ "armas.tsv",type);
            case GLOVES:
                return parseFile((fullData?pathFull:pathTest)+ "guantes.tsv",type);
            case HELMET:
                return parseFile((fullData?pathFull:pathTest) + "cascos.tsv",type);
        }
        throw new IllegalArgumentException("Inexistant ItemType");
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

}
