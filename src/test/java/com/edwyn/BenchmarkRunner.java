package com.edwyn;

import org.openjdk.jmh.profile.StackProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        Options opt = new OptionsBuilder()
                .include(ConcurrentCallVirtualThreadsBenchmark.class.getSimpleName())
                .forks(1)
                .addProfiler(StackProfiler.class) // Add CPU profiling
                //.addProfiler(GCProfiler.class) // Add normalized CPU profiling
                .build();

        new Runner(opt).run();
    }
}

