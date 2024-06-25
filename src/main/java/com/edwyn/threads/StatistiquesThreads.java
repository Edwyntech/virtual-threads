package com.edwyn.threads;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class StatistiquesThreads {

    public static void main(String[] args) throws InterruptedException {
        // Déclaration de sets pour stocker les noms de pools et de threads de plateforme
        Set<String> poolNames = ConcurrentHashMap.newKeySet();
        Set<String> pThreadsNames = ConcurrentHashMap.newKeySet();

        // Création d'une liste de milliers threads non démarrés
        List<Thread> threads = IntStream.range(0, 10000).mapToObj(i -> Thread.ofPlatform().unstarted(() -> {
            // Lecture et stockage du nom du pool
            String poolName = readPoolName();
            poolNames.add(poolName);

            // Lecture et stockage du nom du thread de plateforme
            String workerName = readWorkerName();
            pThreadsNames.add(workerName);
        })).toList();

        // Enregistrer le temps de début
        Instant begin = Instant.now();

        // Démarrer tous les threads
        threads.forEach(Thread::start);

        // Joindre tous les threads pour s'assurer qu'ils terminent avant de continuer
        for (Thread thread : threads) {
            thread.join();
        }

        // Enregistrer le temps de fin
        Instant end = Instant.now();

        // Calculer et afficher la durée de l'exécution
        System.out.println("Time = " + Duration.between(begin, end).toMillis() + " ms");

        // Afficher le nombre de cœurs disponibles
        System.out.println("# core = " + Runtime.getRuntime().availableProcessors());

        // Afficher le nombre de pools utilisés
        System.out.println("# Pools : " + poolNames.size());

        // Afficher le nombre de threads de plateforme utilisés
        System.out.println("# Platform threads : " + pThreadsNames.size());
    }

    // Méthode fictive pour lire le nom du pool
    private static String readPoolName() {
        // Logique pour lire et retourner le nom du pool
        return Thread.currentThread().getThreadGroup().getName();
    }

    // Méthode fictive pour lire le nom du thread de plateforme
    private static String readWorkerName() {
        // Logique pour lire et retourner le nom du thread de plateforme
        return Thread.currentThread().getName();
    }
}
