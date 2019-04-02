package testo.xlsx.streaming.importing;

import testo.xlsx.streaming.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to handle the chunk logic and when to flush or not
 */
public class ChunkHandler {
    private int bufferSize;
    private List<Record> buffer;
    private Database db;

    /**
     * Constructor for chunkhandler
     *
     * @param bufferSize the size of a chunk
     */
    public ChunkHandler(int bufferSize) {
        this.bufferSize = bufferSize;
        this.buffer = new ArrayList<>(bufferSize);
        this.db = Database.getInstance();
    }

    /**
     * handle the current record and maybe flush it if buffer is full
     *
     * @param r     the record to handle
     * @param debug print or save for testing
     */
    public void handle(Record r, boolean debug) {
        buffer.add(r);
        if (buffer.size() > bufferSize - 1) {
            flush(debug);
        }
    }

    /**
     * flush/save the chunk and sets up for a new one
     *
     * @param debug if debug is active, chunk will be flushed differently and will be saved
     */
    public void flush(boolean debug) {
        if (!debug) {
            StringBuilder sb = new StringBuilder();
            sb.append("Chunk ").append(db.getChunkNumber()).append(":\n");
            for (Record record : buffer) {
                sb.append('\t').append(record).append('\n');
            }
            db.publishChunk(sb.toString());
        } else {
            db.saveChunk(buffer);
        }
        db.incrementChunkNumber();
        buffer = new ArrayList<>(bufferSize);
    }
}
