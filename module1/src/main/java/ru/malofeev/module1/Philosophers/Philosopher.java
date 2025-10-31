package ru.malofeev.module1.Philosophers;

import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher implements Runnable {
    private int health;
    private boolean isLive;
    private final int id;
    private List<ReentrantLock> forks;
    private Random random = new Random();

    public Philosopher(int health, boolean isLive, int id, List<ReentrantLock> forks) {
        this.health = health;
        this.isLive = isLive;
        this.id = id;
        this.forks = forks;
    }

    @Override
    public void run() {
        while (isLive) {
            think();
            attemptEat();
        }
        System.out.println("Философ " + id + " умер!");
    }

    private void think() {
        try {
            System.out.println("Философ " + id + " размышляет");
            Thread.sleep(random.nextInt(1000) + 500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void attemptEat() {
        ReentrantLock leftFork = forks.get(id);
        ReentrantLock rightFork = forks.get((id + 1) % forks.size());

        boolean hasLeftFork = false;
        boolean hasRightFork = false;

        try {
            if (leftFork.tryLock(100, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                hasLeftFork = true;
                if (rightFork.tryLock(100, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                    hasRightFork = true;
                    System.out.println("Философ " + id + " ЕСТ");
                    eat();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (hasRightFork) {
                rightFork.unlock();
            }
            if (hasLeftFork) {
                leftFork.unlock();
            }
            if (!hasRightFork || !hasLeftFork) {
                didntEat();
                System.out.println("Философ " + id + " не смог поесть. Здоровье: " + health);
            }
        }
    }

    public void eat() throws InterruptedException {
        health += 25;
        if (health > 100) {
            health = 100;
        }
        Thread.sleep(random.nextInt( 500));
        System.out.println("Философ " + id + " поел. Здоровье: " + health);
    }

    public void didntEat() {
        health -= 25;
        if (health <= 0) {
            health = 0;
            isLive = false;
        }
    }
}