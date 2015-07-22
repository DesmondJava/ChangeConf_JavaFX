package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Администратор on 22.07.2015.
 */
public class Parse {


    public static List<ConfValue> parseFile(BufferedReader readFromFile){
        final int word1 = 0;
        final int word2 = 1;
        final int word3 = 2;
        ArrayList<ConfValue> confValues = new ArrayList<>();
        try {

            Properties properties = new Properties();
//            properties.load(getClass().getResourceAsStream("/resources/textConfFile.properties"));
            properties.load(new InputStreamReader(new FileInputStream("src/resources/textConfFile.properties"), "windows-1251"));
            String line;
            while ((line = readFromFile.readLine()) != null) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    line = line.replaceAll("\t", " ");
                    line = line.replaceAll(" +", " ");

                    String[] lineArr = line.trim().split(" ");
                    switch (lineArr[word1].substring(0, 2)){
                        case "Au":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("gold"), properties.getProperty("metal_descr")));
                            break;
                        case "Ag":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("silver"), properties.getProperty("metal_descr")));
                            break;
                        case "Pt":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("platina"), properties.getProperty("metal_descr")));
                            break;
                        case "Pl":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("pl"), properties.getProperty("metal_descr")));
                            break;
                    }
                    switch (lineArr[word1]){
                        case "fine":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("stavka"), properties.getProperty("stavka_descr")));
                            break;
                        case "interest":
                            confValues.add(new ConfValue(lineArr[word1] + " " + lineArr[word2], lineArr[word3],
                                    properties.getProperty("percent"), properties.getProperty("percent_descr")));
                            break;
                        case "credit":
                            confValues.add(new ConfValue(lineArr[word1] + " " + lineArr[word2], lineArr[word3],
                                    properties.getProperty("credit"), properties.getProperty("credit_descr")));
                            break;
                        case "discount":
                            confValues.add(new ConfValue(lineArr[word1] + " " + lineArr[word2], lineArr[word3],
                                    properties.getProperty("discount"), properties.getProperty("discount_descr")));
                            break;
                        case "bonus":
                            confValues.add(new ConfValue(lineArr[word1] + " " + lineArr[word2], lineArr[word3],
                                    properties.getProperty("bonus"), properties.getProperty("bonus_descr")));
                            break;
                        case "withdraw_term":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("withdraw_term")));
                            break;
                        case "repawn_percent":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("repawn_percent")));
                            break;
                        case "default_term":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("default_term")));
                            break;
                        case "serial_port":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("serial_port")));
                            break;
                        case "printer_name":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("printer_name")));
                            break;
                        case "discount_border":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("withdraw_term")));
                            break;
                        case "nowait_term":
                            confValues.add(new ConfValue(lineArr[word1], lineArr[word2],
                                    properties.getProperty("diff"), properties.getProperty("nowait_term")));
                            break;

                    }
                }
            }
            readFromFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return confValues;
    }

}
