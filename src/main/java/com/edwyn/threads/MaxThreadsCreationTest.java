package com.edwyn.threads;

public class MaxThreadsCreationTest {

    public static void main(String[] args) {
        int count = 0;
        try {
            while (true) {
                //of virtual
                //of plateform
                Thread thread = Thread.ofVirtual().unstarted(() -> {
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                thread.start();
                count++;
                if (count % 1000 == 0) {
                    System.out.println("threads created: " + count);
                }
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError: " + count);
        } catch (Exception e) {
            System.out.println("Exception: " + count);
        }
    }
}
