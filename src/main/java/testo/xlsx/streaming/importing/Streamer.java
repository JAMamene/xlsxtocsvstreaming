package testo.xlsx.streaming.importing;

import com.opencsv.CSVReader;
import testo.xlsx.streaming.Header;
import testo.xlsx.streaming.database.Database;

import java.io.*;
import java.util.function.Function;
import java.util.stream.StreamSupport;

/**
 * Class to read a csv file via a stream
 */
public class Streamer {

    /**
     * Read a file in a stream and handles it
     *
     * @param csvFile    the csv file to read
     * @param bufferSize the size of the chunks
     * @param debug      how to store result (false->Standard output, true->Stored in database)
     * @throws IOException if file cannot be read
     */
    public void stream(File csvFile, int bufferSize, boolean debug) throws IOException {
        CSVReader csvReader = new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(csvFile))));
        ChunkHandler handler = new ChunkHandler(bufferSize);
        System.out.println(mapToRecord.apply(csvReader.peek()));
        StreamSupport.stream(csvReader.spliterator(), false)
                .skip(1 + (bufferSize + 1) * Database.getInstance().getChunkNumber())
                .map(mapToRecord)
                .forEach(r -> handler.handle(r, debug));
        handler.flush(debug);
    }

    /**
     * Mapping function to create a record from a file
     */
    private Function<String[], Record> mapToRecord = (line) -> new Record(line[0], line);
}
