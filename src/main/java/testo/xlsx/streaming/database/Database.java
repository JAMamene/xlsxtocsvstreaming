package testo.xlsx.streaming.database;

public class Database {

    private int chunkNumber;

    private Database() {
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
}
