package com.edwyn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ProducerConsumer {

    static final Logger logger = LoggerFactory.getLogger(ProducerConsumer.class);
    private static int item = 0;

    /**
     * Exécute le scénario de producteur-consommateur avec les paramètres spécifiés.
     *
     * @param executor        l'executor à utiliser pour les threads.
     * @param duration        la durée totale de l'exécution en secondes.
     * @param productionTime  le temps de production en millisecondes.
     * @param consumptionTime le temps de consommation en millisecondes.
     */
    public static void runProducerConsumer(ExecutorService executor, int duration, int productionTime, int consumptionTime) throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

        // Lancement du producteur
        executor.submit(() -> {
            try {
                produce(queue, productionTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Lancement du consommateur
        executor.submit(() -> {
            try {
                consume(queue, consumptionTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Arrêt de l'executor après un certain temps pour la démonstration
        executor.shutdown();
        executor.awaitTermination(duration, TimeUnit.SECONDS);
    }

    /**
     * Méthode de production.
     *
     * @param queue          la queue partagée pour la synchronisation.
     * @param productionTime le temps de production en millisecondes.
     * @throws InterruptedException si l'attente est interrompue.
     */
    private static void produce(BlockingQueue<Integer> queue, int productionTime) throws InterruptedException {
        while (!Thread.currentThread().isInterrupted() && item < 50) {
            TimeUnit.MILLISECONDS.sleep(productionTime); // Simuler le temps de production
            queue.put(item);
            logger.info("Producteur a produit l'élément: " + item);
            item++;
        }
    }

    /**
     * Méthode de consommation.
     *
     * @param queue           la queue partagée pour la synchronisation.
     * @param consumptionTime le temps de consommation en millisecondes.
     * @throws InterruptedException si l'attente est interrompue.
     */
    private static void consume(BlockingQueue<Integer> queue, int consumptionTime) throws InterruptedException {
        while (!Thread.currentThread().isInterrupted() && item < 50) {
            Integer item = queue.take();
            logger.info("Consommateur a consommé l'élément: " + item);
            TimeUnit.MILLISECONDS.sleep(consumptionTime); // Simuler le temps de consommation
        }
    }
}
