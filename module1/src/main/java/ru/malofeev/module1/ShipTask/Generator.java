package ru.malofeev.module1.ShipTask;

import lombok.AllArgsConstructor;
import ru.malofeev.module1.ShipTask.Ship.Ship;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class Generator implements Runnable {
    private final BlockingQueue<Ship> harbor;
    private final int totalShipsToGenerate;
    private final AtomicInteger generatedCount = new AtomicInteger(0);
    private final long minGenerationInterval;
    private final long maxGenerationInterval;
    private final String shipNamePrefix;


    @Override
    public void run() {
        try {
            while (generatedCount.get() < totalShipsToGenerate) {
                Ship ship = Ship.generate(shipNamePrefix, generatedCount.incrementAndGet());
                System.out.println("Генератор: Создан корабль " + ship.getName() +
                        " с грузом " + ship.getSize() + " единиц " + ship.getType());
                harbor.put(ship);
                long interval = minGenerationInterval + (long) (Math.random() * (maxGenerationInterval - minGenerationInterval));
                Thread.sleep(interval);
            }
            System.out.println("Генератор: Все " + totalShipsToGenerate + " кораблей созданы. Завершаю работу.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Генератор: Работа прервана.");
        }
    }
}