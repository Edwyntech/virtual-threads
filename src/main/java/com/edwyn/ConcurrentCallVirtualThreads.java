package com.edwyn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ConcurrentCallVirtualThreads {

    static final Logger logger = LoggerFactory.getLogger(ConcurrentCallVirtualThreads.class);

    public static void runConcurrentApiTasks(ExecutorService executor) throws ExecutionException, InterruptedException {
        Future<String> dbFuture = executor.submit(() -> fetchFromDatabase());
        Future<String> apiFuture = executor.submit(() -> fetchFromApi());
        Future<String> fileFuture = executor.submit(() -> fetchFromFileSystem());

        String dbResult = dbFuture.get();
        String apiResult = apiFuture.get();
        String fileResult = fileFuture.get();

        String finalResult = aggregateResults(dbResult, apiResult, fileResult);
        logger.info("Final Result: " + finalResult);
    }

    private static String fetchFromDatabase() {
        try {
            logger.info("fetching from database....");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Data from DB";
    }

    private static String fetchFromApi() {
        try {
            logger.info("fetching from API....");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Data from API";
    }

    private static String fetchFromFileSystem() {
        try {
            logger.info("fetching from FS....");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Data from File System";
    }

    private static String aggregateResults(String dbResult, String apiResult, String fileResult) {
        return dbResult + " | " + apiResult + " | " + fileResult;
    }

    public static void runConcurrentTasks(ExecutorService executor, int numberOfTasks) throws InterruptedException, ExecutionException {
        Future<?>[] futures = new Future<?>[numberOfTasks];

        for (int i = 0; i < numberOfTasks; i++) {
            futures[i] = executor.submit(createTask(i));
        }

        for (Future<?> future : futures) {
            future.get();
        }
    }

    private static Callable<Void> createTask(int taskNumber) {
        return () -> {
            logger.info("Task " + taskNumber + " started.");
            // Simulation de I/O operation
            Thread.sleep(1000);
            logger.info("Task " + taskNumber + " finished.");
            return null;
        };
    }
}
