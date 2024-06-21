package com.edwyn;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkProducerConsumer {
    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(ProducerConsumerBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
