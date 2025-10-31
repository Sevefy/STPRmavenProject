package ru.malofeev.module1.Philosophers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    static List<ReentrantLock> forks = Arrays.asList(
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock()
    );

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Philosopher> philosophers = Arrays.asList(
                new Philosopher(100, true, 0, forks),
                new Philosopher(100, true, 1, forks),
                new Philosopher(100, true, 2, forks),
                new Philosopher(100, true, 3, forks),
                new Philosopher(100, true, 4, forks)
        );
        philosophers.forEach(executor::submit);

    }
}