package testo.xlsx.streaming.recovery;

/**
 * Helper class to attempt multiple times an operation even if exceptions are thrown
 */
public class OperationHelper {

    /**
     * Static method to attempt multiple times the operation
     * @param maxAttempts max attempts before the exception is rethrown
     * @param operation operation describing what to do normally and when there is an exception
     * @throws Exception if it failed more than maxAttempts times
     */
    public static void doWithRetry(int maxAttempts, Operation operation) throws Exception {
        for (int count = 0; count < maxAttempts; count++) {
            try {
                operation.process();
                count = maxAttempts;
            } catch (Exception e) {
                if (count == maxAttempts - 1) {
                    throw e;
                }
                operation.handleException(e);
            }
        }
    }
}