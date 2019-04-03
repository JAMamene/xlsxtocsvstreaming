package testo.xlsx.streaming;

import org.apache.poi.ss.usermodel.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Class to store header information
 */
public class Header {

    private List<List<String>> fields;

    public Header() {
        fields = new ArrayList<>();
    }

    /**
     * Add a row to the header and creeps the merged data into it
     * @param r the row to add
     */
    public void addRow(Row r) {
        fields.add(StreamSupport.stream(r.spliterator(), false).map(cell -> cell.getStringCellValue().replace("\n", "")).collect(Collectors.toList()));
        creep();
    }

    /**
     * Creeps data from merges values into the new row added
     */
    private void creep() {
        List<String> newRow = fields.get(fields.size() - 1);
        if (fields.size() > 1) {
            List<String> previousRow = fields.get(fields.size() - 2);
            for (int i = 0; i < previousRow.size(); i++) {
                if (newRow.get(i).isEmpty()) {
                    newRow.set(i, previousRow.get(i));
                }
            }
        }
        for (int i = 1; i < newRow.size(); i++) {
            if (newRow.get(i).isEmpty()) {
                newRow.set(i, newRow.get(i - 1));
            }
        }
    }

    /**
     * merges columns of data into a unique string joined by "."
     * @return one row of header
     */
    public List<String> aggregate() {
        return IntStream.range(0, fields.get(0).size()).mapToObj(i -> fields.stream().map((field) -> field.get(i)).distinct().collect(Collectors.joining("."))).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return aggregate().toString();
    }
}
