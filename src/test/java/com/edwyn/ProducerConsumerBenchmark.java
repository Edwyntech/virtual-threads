package com.edwyn;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.*;

@State(Scope.Benchmark)
public class ProducerConsumerBenchmark {

    private BlockingQueue<Integer> queue;
    private ExecutorService virtualThreadExecutor;
    private ExecutorService platformThreadExecutor;

    @Setup(Level.Trial)
    public void setup() {
        queue = new LinkedBlockingQueue<>(10);
        virtualThreadExecutor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("virtual", 0).factory());
        platformThreadExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @TearDown(Level.Trial)
    public void tearDown() {
        virtualThreadExecutor.shutdown();
        platformThreadExecutor.shutdown();
    }

    @Benchmark
    @Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 30, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void benchmarkVirtualThreads() throws InterruptedException {
        ProducerConsumer.runProducerConsumer(virtualThreadExecutor, 30, 100, 299);
    }

    @Benchmark
    @Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.MINUTES)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void benchmarkPlatformThreads() throws InterruptedException {
        ProducerConsumer.runProducerConsumer(platformThreadExecutor, 30, 100, 299);
    }
}
