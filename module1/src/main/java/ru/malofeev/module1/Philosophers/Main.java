// Main.java
package ru.malofeev.module1.Philosophers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        long min = Long.parseLong(args[0]);
        long max = Long.parseLong(args[1]);
        long eatTime = Long.parseLong(args[2]);
        long awaitTime = Long.parseLong(args[3]);

        // Создаем вилки
        List<ReentrantLock> forks = Arrays.asList(
                new ReentrantLock(), new ReentrantLock(), new ReentrantLock(),
                new ReentrantLock(), new ReentrantLock()
        );

        // Создаем философов
        List<Philosopher> philosophers = Arrays.asList(
                new Philosopher(0, forks, min, max, eatTime, awaitTime),
                new Philosopher(1, forks, min, max, eatTime, awaitTime),
                new Philosopher(2, forks, min, max, eatTime, awaitTime),
                new Philosopher(3, forks, min, max, eatTime, awaitTime),
                new Philosopher(4, forks, min, max, eatTime, awaitTime)
        );

        // Запускаем потоки
        List<Thread> threads = new ArrayList<>();
        for (Philosopher phil : philosophers) {
            Thread tphil = new Thread(phil, String.valueOf(phil.getId()));
            threads.add(tphil);
            tphil.start();
        }
// Ждем завершения всех философов
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Все философы ушли.");
    }
}