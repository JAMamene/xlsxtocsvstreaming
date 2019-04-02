package testo.xlsx.streaming.database;

import testo.xlsx.streaming.importing.Record;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private int chunkNumber;

    private List<List<Record>> savedChunks;

    private Database() {
        savedChunks = new ArrayList<>();
        chunkNumber = 0;
    }

    private static Database INSTANCE = new Database();

    public static Database getInstance() {
        return INSTANCE;
    }

    public int getChunkNumber() {
        return chunkNumber;
    }

    public void incrementChunkNumber() {
        this.chunkNumber++;
    }

    public void publishChunk(String chunkData) {
        System.out.println(chunkData);
    }

    public void saveChunk(List<Record> chunk) {
        savedChunks.add(chunk);
    }

    public List<List<Record>> getSavedChunks() {
        return savedChunks;
    }

    public void clean() {
        this.savedChunks = new ArrayList<>();
        this.chunkNumber = 0;
    }
}
