package testo.xlsx.streaming.recovery;

public interface Operation {
    void process() throws Exception;

    void handleException(Exception cause);
}
