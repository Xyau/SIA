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

    public static List<ItemPhenotype> parseFile(String pathfile, ItemType type){
        List<ItemPhenotype> weapons = new LinkedList<>();
        File file = new File(pathfile);
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                int id = sc.nextInt();
                Map<BonusType, Double> bonusMap = new HashMap<>();
                bonusMap.put(BonusType.STRENGHT, sc.nextDouble());
                bonusMap.put(BonusType.AGILITY, sc.nextDouble());
                bonusMap.put(BonusType.WISDOM, sc.nextDouble());
                bonusMap.put(BonusType.RESISTANCE, sc.nextDouble());
                bonusMap.put(BonusType.HEALTH, sc.nextDouble());
                weapons.add(new ItemPhenotype(id + " item", type, bonusMap));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            exit(0);
        }
        return weapons;
    }

}
