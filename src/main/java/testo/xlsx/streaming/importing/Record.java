package testo.xlsx.streaming.importing;

import java.util.Arrays;

public class Record {
    private String productId;
    private String[] fields;

    public Record(String productId, String[] fields) {
        this.productId = productId;
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "Record{" +
                "productId='" + productId + '\'' +
                ", fields=" + Arrays.toString(fields) +
                '}';
    }

    public String getProductId() {
        return productId;
    }

    public String[] getFields() {
        return fields;
    }
}
