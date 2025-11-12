// Main.java
package ru.malofeev.module1.ShipTask;

import ru.malofeev.module1.ShipTask.Ship.Ship;
import ru.malofeev.module1.ShipTask.Ship.TypeShip;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        int totalShipsToGenerate = 20;
        long minGenerationInterval = Long.parseLong(args[0]);
        long maxGenerationInterval = Long.parseLong(args[1]);
        long unloadTimePerUnit = Long.parseLong(args[2]);
        AtomicInteger counter = new AtomicInteger();

        // Общий ресурс: список кораблей в порту (ожидание + разгрузка)
        List<Ship> port = Collections.synchronizedList(new ArrayList<>());
        Object monitor = new Object(); // Объект-монитор для синхронизации

        List<Thread> threads = new ArrayList<>();

        Generator generator = new Generator(port, monitor, totalShipsToGenerate,
                minGenerationInterval, maxGenerationInterval, "SHIP_", counter);
        Thread generatorThread = new Thread(generator, "Generator");
        threads.add(generatorThread);
        generatorThread.start();

        List<Dock> docks = Arrays.asList(
                new Dock("Причал Хлеб", TypeShip.Bread, port, monitor, unloadTimePerUnit, counter),
                new Dock("Причал Бананы", TypeShip.Banana, port, monitor, unloadTimePerUnit, counter),
                new Dock("Причал Одежда", TypeShip.Cloth, port, monitor, unloadTimePerUnit, counter)
        );

        for (Dock dock : docks) {
            Thread dockThread = new Thread(dock, dock.getDockName());
            threads.add(dockThread);
            dockThread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
            System.out.println("Все корабли разгружены. Завершение программы.");
        } catch (InterruptedException e) {
            threads.forEach(Thread::interrupt);
            Thread.currentThread().interrupt();
        }
    }
}