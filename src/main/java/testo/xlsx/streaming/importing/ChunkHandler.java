package testo.xlsx.streaming.importing;

import testo.xlsx.streaming.database.Database;

import java.util.ArrayList;
import java.util.List;

public class ChunkHandler {
    private int bufferSize;
    private List<Record> buffer;
    private Database db;

    public ChunkHandler(int bufferSize) {
        this.bufferSize = bufferSize;
        this.buffer = new ArrayList<>(bufferSize);
        this.db = Database.getInstance();
    }


    public void handle(Record r) {
        buffer.add(r);
        if (buffer.size() > bufferSize) {
            flush();
        }
    }

    public void flush() {
        StringBuilder sb = new StringBuilder();
        sb.append("Chunk ").append(db.getChunkNumber()).append(":\n");
        for (Record record : buffer) {
            sb.append('\t').append(record).append('\n');
        }
        db.publishChunk(sb.toString());
        db.incrementChunkNumber();
        buffer = new ArrayList<>(bufferSize);
    }
}
