
package org.example;
import java.io.File;
import java.io.IOException;


public class App {
    public static void main(String[] args) {
        CheeseAnalyzer analyzer = new CheeseAnalyzer();
        File input = new File("cheese_data.csv");

        if ( !input.exists() ) {
            input = new File("../cheese_data.csv");
        }

        if ( !input.exists() ) {
            System.out.println("Could not find cheese_data.csv");
            return;
        }

        File output = new File(input.getAbsoluteFile().getParentFile() , "output.txt");

        try {
            analyzer.analyze(input.getPath());
            analyzer.writeOutput(output.getPath());

            System.out.println("Cheese calculations are complete!");
            System.out.println("Results saved to " + output.getAbsolutePath());
        }
        catch (IOException e) {
            System.out.println("There was a problem reading or writing the file.");
            System.out.println(e.getMessage());
        }

    }
}
