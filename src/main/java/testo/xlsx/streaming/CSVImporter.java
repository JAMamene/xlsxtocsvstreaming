package testo.xlsx.streaming;

import com.monitorjbl.xlsx.StreamingReader;
import com.opencsv.CSVWriter;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Class to convert xlsx to csv
 */
public class CSVImporter {

    private Header header;
    private int headerSize;

    public CSVImporter(int headerSize) {
        this.headerSize = headerSize;
        this.header = new Header();
    }

    /**
     * Converts the xlsx file passed by name in the resources folder to a csv file and returns it
     *
     * @param fileName the name of the xlsx file to convert
     * @return the new CSV file
     * @throws IOException if somehow some files could not be opened or created
     */
    public File convertToCSV(String fileName) throws IOException {
        ZipSecureFile.setMinInflateRatio(0.00001);
        ClassLoader classLoader = this.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            System.err.println("Resource " + fileName + " not found in resources directory");
            System.exit(1);
        }
        File file = new File(resource.getFile());
        File csvFile = File.createTempFile("tmpstream", ".csv", null);
        System.out.println(csvFile.getAbsolutePath());
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = StreamingReader.builder().rowCacheSize(10).bufferSize(1024).open(is)) {
            try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
                Sheet sheet = workbook.getSheetAt(0);
                for (Row r : sheet) {
                    if (r.getRowNum() < headerSize - 1) {
                        header.addRow(r);
                    } else if (r.getRowNum() == headerSize - 1) {
                        header.addRow(r);
                        csvWriter.writeNext(header.aggregate().toArray(new String[0]), false);
                    } else {
                        String[] cells = new String[r.getLastCellNum()];
                        Arrays.fill(cells, "");
                        for (Cell c : r) {
                            cells[c.getColumnIndex()] = c.getStringCellValue();
                        }
                        csvWriter.writeNext(cells, false);
                    }
                }
            }
        }
        return csvFile;
    }
}
