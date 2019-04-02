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
import java.util.List;

public class CSVImporter {

    public File convertToCSV(String fileName) throws IOException {
        ZipSecureFile.setMinInflateRatio(0.00001);
        ClassLoader classLoader = this.getClass().getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());
        InputStream is = new FileInputStream(file);
        Workbook workbook = StreamingReader.builder()
                .rowCacheSize(20)
                .bufferSize(4096)
                .open(is);

        File csvFile = File.createTempFile("tmpstream", ".csv", null);
        System.out.println(csvFile.getAbsolutePath());
        CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile));
        Sheet sheet = workbook.getSheetAt(0);
        int i = 0;
        for (Row r : sheet) {
            if (i++ >= 7) {
                List<String> cells = new ArrayList<>(r.getPhysicalNumberOfCells());
                for (Cell c : r) {
                    cells.add(c.getStringCellValue());
                }
                csvWriter.writeNext(cells.toArray(new String[0]), false);
            }
        }
        return csvFile;
    }
}
