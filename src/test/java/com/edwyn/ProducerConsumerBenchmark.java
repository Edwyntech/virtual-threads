package com.edwyn;

import com.edwyn.threads.CPUProducerConsumer;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class ProducerConsumerBenchmark {

    private ExecutorService virtualThreadExecutor;
    private ExecutorService platformThreadExecutor;

    @Setup(Level.Invocation)
    public void setup() {
        virtualThreadExecutor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("virtual", 0).factory());
        platformThreadExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
        virtualThreadExecutor.shutdownNow();
        platformThreadExecutor.shutdownNow();
    }

    @Benchmark
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 3, time = 30, timeUnit = TimeUnit.SECONDS)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void benchmarkVirtualThreads() throws InterruptedException {
        CPUProducerConsumer.runProducerConsumer(virtualThreadExecutor, 60, 250, 250);
    }

    @Benchmark
    @Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.SECONDS)
    @Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.MINUTES)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void benchmarkPlatformThreads() throws InterruptedException {
        CPUProducerConsumer.runProducerConsumer(platformThreadExecutor, 60, 250, 250);
    }
}
