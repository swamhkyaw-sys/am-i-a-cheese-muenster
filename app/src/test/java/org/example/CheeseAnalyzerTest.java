
package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CheeseAnalyzerTest {

    private String getFileName() {
        File file = new File("cheese_data.csv");

        if ( !file.exists() ) {
            file = new File("../cheese_data.csv");
        }

        return file.getPath();
    }

    @Test
    void testPasteurized() throws IOException {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        analyzer.analyze(getFileName());

        assertEquals(800 , analyzer.getPasteurized());
    }

    @Test
    void testRawMilk() throws IOException {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        analyzer.analyze(getFileName());

        assertEquals(115 , analyzer.getRawMilk());
    }

    @Test
    void testOrganicOver41() throws IOException {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        analyzer.analyze(getFileName());

        assertEquals(53 , analyzer.getOrganicOver41());
    }

    @Test
    void testMostCommonMilk() throws IOException {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        analyzer.analyze(getFileName());

        assertEquals("Cow" , analyzer.getMostCommonMilk());
        assertEquals(743 , analyzer.getMostCommonMilkCount());
    }

    @Test
    void testQuotedCommaAndMissingValues() throws IOException {
        File file = File.createTempFile("cheese_sample" , ".csv");
        file.deleteOnExit();

        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.println("CheeseId,ManufacturerProvCode,ManufacturingTypeEn,MoisturePercent,FlavourEn,CharacteristicsEn,Organic,CategoryTypeEn,MilkTypeEn,MilkTreatmentTypeEn,RindTypeEn,CheeseName,FatLevel");
        writer.println("200,ON,Farmstead,42.0,\"Sharp, lactic\",,1,Firm Cheese,Cow,Raw Milk,,Test Cheese,normal");
        writer.println("201,QC,Farmstead,,Mild,,1,Firm Cheese,Goat,Pasteurized,,Other Cheese,normal");
        writer.close();

        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        analyzer.analyze(file.getPath());

        assertEquals(1 , analyzer.getPasteurized());
        assertEquals(1 , analyzer.getRawMilk());
        assertEquals(1 , analyzer.getOrganicOver41());
        assertEquals("Cow" , analyzer.getMostCommonMilk());
    }
}
