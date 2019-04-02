package testo.xlsx.streaming.recovery;

public class OperationHelper {
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