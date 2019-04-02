package testo.xlsx.streaming;

import com.monitorjbl.xlsx.StreamingReader;
import com.opencsv.CSVWriter;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVImporter {

    public File convertToCSV(String fileName) throws IOException {
        ZipSecureFile.setMinInflateRatio(0.00001);
        ClassLoader classLoader = this.getClass().getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());
        File csvFile = File.createTempFile("tmpstream", ".csv", null);
        System.out.println(csvFile.getAbsolutePath());
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = StreamingReader.builder().rowCacheSize(10).bufferSize(1024).open(is)) {
            try (CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile))) {
                Sheet sheet = workbook.getSheetAt(0);
                for (Row r : sheet) {
                    if (r.getRowNum() >= 7) {
                        String[] cells = new String[r.getLastCellNum()];
                        Arrays.fill(cells,"");
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
