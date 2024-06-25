package com.edwyn.threads;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CPUProducerConsumerTest {

    private ExecutorService executor;

    private int duration = 60; // durée de l'exécution en secondes
    private int productionTime = 1000; // temps de production en millisecondes
    private int consumptionTime = 1500; // temps de consommation en millisecondes

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
        }
    }

    @Test
    void testProducerConsumer() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);

        // Exécution de la méthode à tester
        CPUProducerConsumer.runProducerConsumer(executor, duration, productionTime, consumptionTime);

        Assertions.assertTrue(true);

    }

    @Test
    void testVirtualProducerConsumer() throws InterruptedException {
        executor = Executors.newVirtualThreadPerTaskExecutor();

        // Exécution de la méthode à tester
        CPUProducerConsumer.runProducerConsumer(executor, duration, productionTime, consumptionTime);

        Assertions.assertTrue(true);

    }
}
