package com.edwyn.threads.scope;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

class CombinedScopeExampleTest {

    private final CombinedScopeExample combinedScopeExample = new CombinedScopeExample();

    // Teste la méthode avec la portée par défaut
    @Test
    void testRetrieveAllData() {
        try {
            String result = combinedScopeExample.retrieveAllData();
            assertNotNull(result);
            assertTrue(result.contains("Données de la source 1") && result.contains("Données de la source 2"));
        } catch (InterruptedException e) {
            fail("Exception thrown during data retrieval: " + e.getMessage());
        }
    }

    // Teste la méthode avec la politique ShutdownOnSuccess
    @Test
    void testRetrieveFirstSuccessfulData() {
        try {
            String result = combinedScopeExample.retrieveFirstSuccessfulData();
            assertNotNull(result);
            assertTrue(result.contains("Données de la source 1") || result.contains("Données de la source 2"));
        } catch (ExecutionException | InterruptedException e) {
            fail("Exception thrown during data retrieval: " + e.getMessage());
        }
    }

    // Teste la méthode avec la politique ShutdownOnFailure
    @Test
    void testRetrieveAllDataOrFail() {
        try {
            String result = combinedScopeExample.retrieveAllDataOrFail();
            assertNotNull(result);
            assertTrue(result.contains("Données de la source 1") && result.contains("Données de la source 2"));
        } catch (InterruptedException e) {
            fail("Exception thrown during data retrieval: " + e.getMessage());
        }
    }

    // Teste la méthode avec la politique ShutdownOnFailure et une exception
    @Test
    void testRetrieveAllDataOrFailWithException() {
        CombinedScopeExample faultyService = new CombinedScopeExample() {
            @Override
            protected String retrieveDataFromSource1() throws InterruptedException {
                throw new RuntimeException("Simulated exception");
            }
        };

        assertThrows(IllegalStateException.class, faultyService::retrieveAllDataOrFail);
    }

    // Teste la méthode avec la politique ShutdownOnSuccess et une exception
    @Test
    void testRetrieveFirstSuccessfulDataWithException() throws InterruptedException, ExecutionException {
        CombinedScopeExample faultyService = new CombinedScopeExample() {
            @Override
            protected String retrieveDataFromSource1() throws InterruptedException {
                throw new InterruptedException("Simulated exception");
            }
        };

        String result = faultyService.retrieveFirstSuccessfulData();
        assertNotNull(result);

        assertTrue(result.contains("Données de la source 2"));
    }

    // Teste la méthode avec la politique ShutdownOnSuccess et une interruption
    @Test
    void testRetrieveFirstSuccessfulDataWithInterruptedException() throws ExecutionException, InterruptedException {
        CombinedScopeExample interruptedService = new CombinedScopeExample() {
            @Override
            protected String retrieveDataFromSource1() throws InterruptedException {
                throw new RuntimeException("Simulated interruption");
            }
        };
        String result = interruptedService.retrieveFirstSuccessfulData();
        assertNotNull(result);

        assertTrue(result.contains("Données de la source 2"));
    }
}
