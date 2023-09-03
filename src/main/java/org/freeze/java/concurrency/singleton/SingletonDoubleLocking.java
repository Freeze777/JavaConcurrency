package org.freeze.java.concurrency.singleton;

import java.util.concurrent.ThreadLocalRandom;

public class SingletonDoubleLocking {
    private static volatile SingletonDoubleLocking instance = null; // volatile to prevent reordering of low-level instructions by the compiler and the CPU
    private final int field;

    private SingletonDoubleLocking() {
        field = ThreadLocalRandom.current().nextInt(); // some complex initialization
    }

    public static SingletonDoubleLocking getInstance() {
        if (instance == null) { // Optional : performance optimization to avoid synchronization if instance is already initialized
            synchronized (SingletonDoubleLocking.class) { // locking at class level to prevent multiple threads from creating multiple instances
                if (instance == null)
                    instance = new SingletonDoubleLocking(); // double check to prevent multiple threads from creating multiple instances
            }
        }
        return instance;
    }

    public void compute() {
        System.out.println("some complex compute using field : " + field);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(() -> SingletonDoubleLocking.getInstance().compute()).start();
        }
    }
}
