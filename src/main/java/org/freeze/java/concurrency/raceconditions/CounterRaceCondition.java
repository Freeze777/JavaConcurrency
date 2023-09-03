package org.freeze.java.concurrency.raceconditions;

public class CounterRaceCondition {
    private int counter = 0; // making this volatile will not solve the problem

    private void sleep() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void increment() {
        sleep();
        counter++;
    }
    public void decrement() {
        sleep();
        counter--;
    }
    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        final CounterRaceCondition counterRaceCondition = new CounterRaceCondition();
        for (int i = 0; i < 50; i++) new Thread(counterRaceCondition::increment).start();
        for (int i = 0; i < 50; i++) new Thread(counterRaceCondition::decrement).start();
        System.out.println("Final counter value : " + counterRaceCondition.getCounter()); // We expect 0 but we get a random value
    }
}
