package testo.xlsx.streaming.importing;

import com.opencsv.CSVReader;
import testo.xlsx.streaming.database.Database;

import java.io.*;
import java.util.function.Function;
import java.util.stream.StreamSupport;

public class Streamer {


    public void stream(File csvFile, int bufferSize) throws IOException {
        CSVReader csvReader = new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(csvFile))));
        ChunkHandler handler = new ChunkHandler(bufferSize);
        StreamSupport.stream(csvReader.spliterator(), false)
                .skip((bufferSize + 1) * Database.getInstance().getChunkNumber())
                .map(mapToRecord)
                .forEach(handler::handle);
        handler.flush();
    }

    private Function<String[], Record> mapToRecord = (line) -> new Record(line[0], line);
}
