package com.edwyn;

import com.edwyn.threads.ConcurrentCallVirtualThreads;
import org.openjdk.jmh.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class ConcurrentCallVirtualThreadsBenchmark {

    static final Logger logger = LoggerFactory.getLogger(ConcurrentCallVirtualThreadsBenchmark.class);

    private ExecutorService virtualThreadExecutor;
    private ExecutorService platformThreadExecutor;
    private OperatingSystemMXBean osBean;
    private int NUMBER_OF_TASKS = 100;

    @Setup
    public void setup() {
        virtualThreadExecutor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("virtual-", 0).factory());
        platformThreadExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        osBean = ManagementFactory.getOperatingSystemMXBean();
    }

    @TearDown
    public void tearDown() {
        virtualThreadExecutor.shutdown();
        platformThreadExecutor.shutdown();
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void benchmarkAPiVirtualThreads() throws InterruptedException, ExecutionException {
        logCpuUsage("Before Virtual Threads");
        ConcurrentCallVirtualThreads.runConcurrentApiTasks(virtualThreadExecutor);
        logCpuUsage("After Virtual Threads");
    }

    @Benchmark
    @Warmup(iterations = 2, time = 1)
    @Measurement(iterations = 5, time = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void benchmarkAPiPlatformThreads() throws InterruptedException, ExecutionException {
        logCpuUsage("Before Platform Threads");
        ConcurrentCallVirtualThreads.runConcurrentApiTasks(platformThreadExecutor);
        logCpuUsage("After Platform Threads");
    }

    @Benchmark
    @Warmup(iterations = 3, time = 1)
    @Measurement(iterations = 5, time = 1)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void benchmarkTasksVirtualThreads() throws InterruptedException, ExecutionException {
        ConcurrentCallVirtualThreads.runConcurrentTasks(virtualThreadExecutor, NUMBER_OF_TASKS);
    }

    @Benchmark
    @Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void benchmarkTasksPlatformThreads() throws InterruptedException, ExecutionException {
        ConcurrentCallVirtualThreads.runConcurrentTasks(platformThreadExecutor, NUMBER_OF_TASKS);
    }

    private void logCpuUsage(String threadType) {
        double cpuLoad = osBean.getSystemLoadAverage();
        logger.info(threadType + " - Current CPU load: " + cpuLoad);
    }
}
