package testo.xlsx.streaming.database;

import testo.xlsx.streaming.importing.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class to represent a database
 */
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

    /**
     * Validate a chunk by printing it to the standard output
     * @param chunkData the data as a nice string
     */
    public void publishChunk(String chunkData) {
        System.out.println(chunkData);
    }

    /**
     * Test method to save chunks
     * @param chunk the chunk to save
     */
    public void saveChunk(List<Record> chunk) {
        savedChunks.add(chunk);
    }

    /**
     * Test method to get saved chunks
     * @return saved chunks
     */
    public List<List<Record>> getSavedChunks() {
        return savedChunks;
    }

    /**
     * Test method to reset database
     */
    public void clean() {
        this.chunkNumber = 0;
        this.savedChunks = new ArrayList<>();
    }
}
