package utils;

import genes.ItemPhenotype;
import interfaces.Phenotype;
import main.BonusType;
import main.ItemType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static java.lang.System.exit;

public class TSVReader {

    public static List<ItemPhenotype> parseFile(ItemType type){
        switch (type){
            case ARMOR:
                return parseFile("./src/main/resources/testdata/pecheras.tsv",type);
            case BOOTS:
                return parseFile("./src/main/resources/testdata/botas.tsv",type);
            case WEAPON:
                return parseFile("./src/main/resources/testdata/armas.tsv",type);
            case GLOVES:
                return parseFile("./src/main/resources/testdata/guantes.tsv",type);
            case HELMET:
                return parseFile("./src/main/resources/testdata/cascos.tsv",type);
        }
        throw new IllegalArgumentException("Inexistant ItemType");
    }

    public static List<ItemPhenotype> parseFile(String pathfile, ItemType type){
        List<ItemPhenotype> weapons = new LinkedList<>();
        File file = new File(pathfile);
        try {
            Scanner sc = new Scanner(file);
            sc.nextLine();
            while (sc.hasNextInt()){
                int id = sc.nextInt();
                Map<BonusType, Double> bonusMap = new HashMap<>();
                bonusMap.put(BonusType.STRENGHT, sc.nextDouble());
                bonusMap.put(BonusType.AGILITY, sc.nextDouble());
                bonusMap.put(BonusType.WISDOM, sc.nextDouble());
                bonusMap.put(BonusType.RESISTANCE, sc.nextDouble());
                bonusMap.put(BonusType.HEALTH, sc.nextDouble());
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
