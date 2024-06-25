package com.edwyn.threads.scope;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

public class CombinedScopeExample {

    // Méthode utilisant la portée par défaut
    public String retrieveAllData() throws InterruptedException {
        try (var scope = new StructuredTaskScope<String>()) {

            // Démarrage de sous-tâches liées
            Supplier<String> task1 = scope.fork(this::retrieveDataFromSource1);
            Supplier<String> task2 = scope.fork(this::retrieveDataFromSource2);

            // Rejoint toutes les tâches dans la portée
            scope.join();  // Et propage les erreurs si une tâche échoue

            // Obtient les résultats
            return task1.get() + " et " + task2.get();
        }
    }

    // Méthode utilisant la politique ShutdownOnSuccess

    public String retrieveFirstSuccessfulData() throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {

            // Démarrage de sous-tâches liées
            Supplier<String> task1 = scope.fork(this::retrieveDataFromSource1);
            Supplier<String> task2 = scope.fork(this::retrieveDataFromSource2);

            // Rejoint toutes les tâches dans la portée
            scope.join();  // Propage les erreurs si une tâche échoue

            // Obtient le résultat de la première tâche réussie
            return scope.result();
        }
    }

    // Méthode utilisant la politique ShutdownOnFailure
    public String retrieveAllDataOrFail() throws InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {

            // Démarrage de sous-tâches liées
            Supplier<String> task1 = scope.fork(this::retrieveDataFromSource1);
            Supplier<String> task2 = scope.fork(this::retrieveDataFromSource2);

            // Rejoint toutes les tâches dans la portée
            scope.join();  // Propage les erreurs si une tâche échoue

            // Obtient les résultats
            return task1.get() + " et " + task2.get();
        }
    }

    // Simule la récupération de données de la source 1 avec une opération d'I/O
    protected String retrieveDataFromSource1() throws InterruptedException {
        simulateRandomIO();
        return "Données de la source 1";
    }

    // Simule la récupération de données de la source 2 avec une opération d'I/O
    protected String retrieveDataFromSource2() throws InterruptedException {
        simulateRandomIO();
        return "Données de la source 2";
    }

    // Simule une opération d'I/O avec un délai aléatoire entre 1000 et 2000 ms
    protected void simulateRandomIO() throws InterruptedException {
        int delay = 1000 + (int) (Math.random() * 1000);
        Thread.sleep(delay);
    }
}
