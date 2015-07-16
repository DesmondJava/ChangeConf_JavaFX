package model;

import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class PriceMetal {

    private final static double PROCENT_OT_OCENOCHNOY_STOIMOSTI_1_DAY = 0.73;
    private final static double PROCENT_OT_OCENOCHNOY_STOIMOSTI_14_DAY = 0.7249;

    public static boolean changeGoldPrice(ObservableList<ConfValue> data, double new_price){
        for(ConfValue gold : data){
            String title = gold.getTitle();
            if(title.startsWith("Au")){
                int proba = Integer.parseInt(title.substring(2));
                gold.setPrice(newPrice(proba, new_price, 585));
            }
        }
        return true;
    }

    public static boolean changeSilverPrice(ObservableList<ConfValue> data, double new_price){
        for(ConfValue gold : data){
            String title = gold.getTitle();
            if(title.startsWith("Ag")){
                int proba = Integer.parseInt(title.substring(2));
                gold.setPrice(newPrice(proba, new_price, 875));
            }
        }
        return true;
    }

    private static String newPrice(int proba, double new_price, int default_proba) {
        double pure_gold = (new_price * 1000) / default_proba;
        double new_amount = (pure_gold * proba) / 1000;
        double new_assessed_value = new_amount / PROCENT_OT_OCENOCHNOY_STOIMOSTI_1_DAY;
        double new_amount_with_koef = new_assessed_value * PROCENT_OT_OCENOCHNOY_STOIMOSTI_14_DAY;
        double result_round = new BigDecimal(new_amount_with_koef).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return String.format("%8.2f", result_round).replace(',', '.').trim();
    }

}
