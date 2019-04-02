package testo.xlsx.streaming;

import org.apache.commons.cli.*;
import testo.xlsx.streaming.importing.Streamer;
import testo.xlsx.streaming.recovery.Operation;
import testo.xlsx.streaming.recovery.OperationHelper;

import java.io.File;

/**
 * Main class
 */
public class Main {

    /**
     * Main loop of the program, by default it converts a file to csv and prints its content to the standard output
     * If an exception is manually thrown while parsing it should be able to recover from the last chunk and continue
     * the parsing
     * @param args arguments, namely the input file passed with -i or --input
     */
    public static void main(String[] args) {

        Options options = new Options();

        Option input = new Option("i", "input", true, "xlsx input file");
        input.setRequired(true);
        options.addOption(input);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
            String inputFileName = cmd.getOptionValue("input");
            File csvFile = new CSVImporter().convertToCSV(inputFileName);
            OperationHelper.doWithRetry(5, new Operation() {
                @Override
                public void process() throws Exception {
                    new Streamer().stream(csvFile, 60, false);
                }

                @Override
                public void handleException(Exception cause) {
                    System.out.println("Exception thrown, recovering...");
                }
            });
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("XLSX streaming test", options);
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
