
package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CheeseAnalyzer {

    private int pasteurized;
    private int rawMilk;
    private int organicOver41;
    private int cow;
    private int goat;
    private int ewe;
    private int buffalo;

    public void analyze(String fileName) throws IOException {
        pasteurized = 0;
        rawMilk = 0;
        organicOver41 = 0;
        cow = 0;
        goat = 0;
        ewe = 0;
        buffalo = 0;

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        reader.readLine();
        String line = reader.readLine();

        while ( line != null) {
            ArrayList<String> columns = splitLine(line);

            if ( columns.size() >= 13) {
                String treatment = columns.get(9);
                String milkType = columns.get(8);
                String organic = columns.get(6);
                String moisture = columns.get(3);

                if ( treatment.equalsIgnoreCase("Pasteurized") ) {
                    pasteurized = pasteurized + 1;
                }
                else if ( treatment.equalsIgnoreCase("Raw Milk") ) {
                    rawMilk = rawMilk + 1;
                }

                if ( organic.equals("1") && !moisture.isEmpty() ) {
                    try {
                        double percent = Double.parseDouble(moisture);

                        if ( percent > 41.0) {
                            organicOver41 = organicOver41 + 1;
                        }
                    }
                    catch (NumberFormatException e) {
                        System.out.println("Skipping invalid moisture value : " + moisture);
                    }
                }

                if ( milkType.equalsIgnoreCase("Cow") ) {
                    cow = cow + 1;
                }
                else if ( milkType.equalsIgnoreCase("Goat") ) {
                    goat = goat + 1;
                }
                else if ( milkType.equalsIgnoreCase("Ewe") ) {
                    ewe = ewe + 1;
                }
                else if ( milkType.equalsIgnoreCase("Buffalo Cow") ) {
                    buffalo = buffalo + 1;
                }
            }

            line = reader.readLine();
        }

        reader.close();
    }

    private ArrayList<String> splitLine(String line) {
        ArrayList<String> columns = new ArrayList<String>();
        String value = "";
        boolean inQuotes = false;

        for ( int i = 0; i < line.length(); i++ ) {
            char letter = line.charAt(i);

            if ( letter == '"' ) {
                if ( inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"' ) {
                    value = value + '"';
                    i = i + 1;
                }
                else {
                    inQuotes = !inQuotes;
                }
            }
            else if ( letter == ',' && !inQuotes ) {
                columns.add(value.trim());
                value = "";
            }
            else {
                value = value + letter;
            }
        }

        columns.add(value.trim());
        return columns;
    }

    public int getPasteurized() {
        return pasteurized;
    }

    public int getRawMilk() {
        return rawMilk;
    }

    public int getOrganicOver41() {
        return organicOver41;
    }

    public String getMostCommonMilk() {
        String result = "Cow";
        int largest = cow;

        if ( goat > largest) {
            result = "Goat";
            largest = goat;
        }
        if ( ewe > largest) {
            result = "Ewe";
            largest = ewe;
        }
        if ( buffalo > largest) {
            result = "Buffalo";
        }

        return result;
    }

    public int getMostCommonMilkCount() {
        int largest = cow;

        if ( goat > largest) {
            largest = goat;
        }
        if ( ewe > largest) {
            largest = ewe;
        }
        if ( buffalo > largest) {
            largest = buffalo;
        }

        return largest;
    }

    public void writeOutput(String fileName) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(fileName));

        writer.println("Canadian Cheese Dataset Results");
        writer.println("Pasteurized milk cheeses : " + pasteurized);
        writer.println("Raw milk cheeses : " + rawMilk);
        writer.println("Organic cheeses with moisture above 41.0% : " + organicOver41);
        writer.println("Most common milk type : " + getMostCommonMilk() + " (" + getMostCommonMilkCount() + " cheeses)");

        writer.close();
    }
}
