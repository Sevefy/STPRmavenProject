package ru.malofeev.module1.ShipTask;

import ru.malofeev.module1.ShipTask.Ship.Ship;
import ru.malofeev.module1.ShipTask.Ship.TypeShip;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        int totalShipsToGenerate = 20;
        long minGenerationInterval = 500;
        long maxGenerationInterval = 2000;
        long unloadTimePerUnit = 1000;

        BlockingQueue<Ship> harbor = new ArrayBlockingQueue<>(5);
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Generator generator = new Generator(harbor, totalShipsToGenerate,
                minGenerationInterval, maxGenerationInterval, "SHIP_");
        executor.submit(generator);

        List<Dock> docks = Arrays.asList(
                new Dock("Причал Хлеб", TypeShip.Bread, harbor, unloadTimePerUnit),
                new Dock("Причал Бананы", TypeShip.Banana, harbor, unloadTimePerUnit),
                new Dock("Причал Одежда", TypeShip.Cloth, harbor, unloadTimePerUnit)
        );

        docks.forEach(executor::submit);

        try {
            executor.shutdown(); // Запрещаем новые задачи

            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }

            System.out.println("Все корабли разгружены. Завершение программы.");

        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}