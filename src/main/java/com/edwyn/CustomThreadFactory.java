package com.edwyn;

public class CustomThreadFactory {

    /**
     * Crée et démarre un thread de plateforme.
     *
     * @param name     le nom du thread
     * @param runnable la tâche à exécuter par le thread
     * @return le thread de plateforme créé et démarré
     */
    public static Thread platformThread(String name, Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(name);
        thread.start();
        return thread;
    }

    /**
     * Crée et démarre un thread virtuel.
     *
     * @param name     le nom du thread
     * @param runnable la tâche à exécuter par le thread
     * @return le thread virtuel créé et démarré
     */
    public static Thread virtualThread(String name, Runnable runnable) {
        return Thread.ofVirtual()
                .name(name)
                .start(runnable);
    }

}
