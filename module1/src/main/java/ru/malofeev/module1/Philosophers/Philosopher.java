// Philosopher.java
package ru.malofeev.module1.Philosophers;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

@Getter
public class Philosopher implements Runnable {
    private final int id;
    private final String name;
    private final List<ReentrantLock> forks;
    private final long minThinkTime;
    private final long maxThinkTime;
    private final long eatTime;
    private final long awaitTime;
    private long lastEatTime = System.currentTimeMillis();

    public Philosopher(int id, List<ReentrantLock> forks, long minThinkTime, long maxThinkTime,
                       long eatTime, long awaitTime) {
        this.id = id;
        this.name = "Philosopher-" + id;
        this.forks = forks;
        this.minThinkTime = minThinkTime;
        this.maxThinkTime = maxThinkTime;
        this.eatTime = eatTime;
        this.awaitTime = awaitTime;

    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Проверяем голод ДО размышления (опционально, но логично)
            if (System.currentTimeMillis() - lastEatTime > awaitTime) {
                System.out.println("Философ " + id + " голодает слишком долго и уходит!");
                break;
            }

            think();

            // Теперь передаем awaitTime в метод
            if (attemptEat(awaitTime)) {
                // Успешно поели
            }
        }
        System.out.println("Философ " + id + " ушел!");
    }

    private void think() {
        try {
            long thinkTime = ThreadLocalRandom.current().nextLong(minThinkTime, maxThinkTime);
            System.out.println(name + " размышляет " + thinkTime + "мс");
            Thread.sleep(thinkTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean attemptEat(long maxWaitTime) {
        ReentrantLock leftFork = forks.get(id);
        ReentrantLock rightFork = forks.get((id + 1) % forks.size());

        long waitStartTime = System.currentTimeMillis();
        boolean hasLeftFork = false;
        boolean hasRightFork = false;

        try {
            if (leftFork.tryLock(maxWaitTime, TimeUnit.MILLISECONDS)) {
                hasLeftFork = true;

                long timeRemaining = maxWaitTime - (System.currentTimeMillis() - waitStartTime);

                if (timeRemaining > 0) {
                    if (rightFork.tryLock(timeRemaining, TimeUnit.MILLISECONDS)) {
                        hasRightFork = true;
                        eat();
                        return true;
                    }
                }
                System.out.println("Философ " + id + " кладет вилку (время вышло или не дождался второй)");
            }
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } finally {
            if (hasRightFork) {
                rightFork.unlock();
            }
            if (hasLeftFork) {
                leftFork.unlock();
            }
        }
    }

    private void eat() throws InterruptedException {
        System.out.println(name + " ЕСТ (вилки " + id + " и " + ((id + 1) % forks.size()) + ")");
        lastEatTime = System.currentTimeMillis();
        Thread.sleep(eatTime);
        System.out.println(name + " закончил есть!");
    }

}