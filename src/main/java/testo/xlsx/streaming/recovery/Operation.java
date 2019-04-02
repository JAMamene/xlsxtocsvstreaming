package testo.xlsx.streaming.recovery;

/**
 * Interface to describe an operation
 */
public interface Operation {
    /**
     * What to do
     * @throws Exception various exception that could be thrown
     */
    void process() throws Exception;

    /**
     * What to do in case of exception
     * @param cause the exception
     */
    void handleException(Exception cause);
}
