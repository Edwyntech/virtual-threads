package com.edwyn.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class CPUProducerConsumer {

    static final Logger logger = LoggerFactory.getLogger(CPUProducerConsumer.class);
    private static int item = 0;
    private static final SystemInfo systemInfo = new SystemInfo();
    private static final HardwareAbstractionLayer hardware = systemInfo.getHardware();
    private static final CentralProcessor processor = hardware.getProcessor();
    private static long[] oldTicks = processor.getSystemCpuLoadTicks();

    public static void runProducerConsumer(ExecutorService executor, int duration, int productionTime, int consumptionTime) throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

        logCpuUsage("Before starting producer-consumer tasks");

        executor.submit(() -> {
            try {
                produce(queue, productionTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.submit(() -> {
            try {
                consume(queue, consumptionTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.awaitTermination(duration, TimeUnit.SECONDS);
        executor.shutdownNow();

        logCpuUsage("After completing producer-consumer tasks");
    }

    private static void produce(BlockingQueue<Integer> queue, int productionTime) throws InterruptedException {
        while (!Thread.currentThread().isInterrupted() && item < 30) {
            TimeUnit.MILLISECONDS.sleep(productionTime);
            queue.put(item);
            logger.info("Producteur a produit l'élément: " + item);
            item++;
        }
    }

    private static void consume(BlockingQueue<Integer> queue, int consumptionTime) throws InterruptedException {
        while (!Thread.currentThread().isInterrupted() && item < 30) {
            Integer item = queue.take();
            logger.info("Consommateur a consommé l'élément: " + item);
            TimeUnit.MILLISECONDS.sleep(consumptionTime);
        }
    }

    private static void logCpuUsage(String phase) {
        // Calculate CPU load between ticks
        long[] newTicks = processor.getSystemCpuLoadTicks();
        float cpuLoad = (float) (processor.getSystemCpuLoadBetweenTicks(oldTicks) * 100); // Convert to percentage
        oldTicks = newTicks; // Update oldTicks for the next measurement
        logger.info(phase + " - Current CPU load: " + cpuLoad + "%");
    }
}
