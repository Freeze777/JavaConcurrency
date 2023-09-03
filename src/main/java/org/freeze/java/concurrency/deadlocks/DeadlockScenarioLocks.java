package org.freeze.java.concurrency.deadlocks;

public class DeadlockScenarioLocks {
    private final Object lockA = new Object();
    private final Object lockB = new Object();

    public void doThis() throws InterruptedException {
        System.out.println("Entering doThis");
        synchronized (lockA) {
            Thread.sleep(10);
            synchronized (lockB) {
                System.out.println("[doThis] : Got both locks");
                Thread.sleep(100);
            }
        }
        System.out.println("Completed doThis");
    }

    public void doThat() throws InterruptedException {
        System.out.println("Entering doThat");
        // Note the order of locks is reversed compared to doThis
        synchronized (lockB) {
            Thread.sleep(10);
            synchronized (lockA) {
                System.out.println("[doThat] : Got both locks");
                Thread.sleep(100);
            }
        }
        System.out.println("Completed doThat");
    }

    public static void main(String[] args) {
        final DeadlockScenarioLocks deadlockScenarioLocks = new DeadlockScenarioLocks();
        new Thread(() -> {
            try {
                deadlockScenarioLocks.doThis();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                deadlockScenarioLocks.doThat();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
