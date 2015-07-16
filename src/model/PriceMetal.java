package model;

import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Администратор on 16.07.2015.
 */
public class PriceMetal {

    private final static double PROCENT_OT_OCENOCHNOY_STOIMOSTI_1_DAY = 0.73;
    private final static double PROCENT_OT_OCENOCHNOY_STOIMOSTI_14_DAY = 0.7249;

    public static boolean changeGoldPrice(ObservableList<ConfValue> data, double new_price){
        for(ConfValue gold : data){
            String title = gold.getTitle();
            if(title.startsWith("Au")){
                int proba = Integer.parseInt(title.substring(2));
                gold.setPrice(newPrice(proba, new_price));
            }
        }
        return true;
    }

    private static String newPrice(int proba, double new_price) {
        double pure_gold = (new_price * 1000) / 585;
        double new_amount = (pure_gold * proba) / 1000;
        double new_assessed_value = new_amount / PROCENT_OT_OCENOCHNOY_STOIMOSTI_1_DAY;
        double result = new_assessed_value * PROCENT_OT_OCENOCHNOY_STOIMOSTI_14_DAY;
        System.out.println(result);
        double result_round = new BigDecimal(result).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return String.valueOf(result_round);
    }

}
