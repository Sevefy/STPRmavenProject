// Generator.java
package ru.malofeev.module1.ShipTask;

import lombok.AllArgsConstructor;
import ru.malofeev.module1.ShipTask.Ship.Ship;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
public class Generator implements Runnable {
    private final List<Ship> port;
    private final Object monitor;
    private final int totalShipsToGenerate;
    private final long minGenerationInterval;
    private final long maxGenerationInterval;
    private final String shipNamePrefix;
    private final AtomicInteger globalCounter;

    @Override
    public void run() {
        try {
            while (globalCounter.get() < totalShipsToGenerate) {
                synchronized (monitor) {
                    while (port.size() >= 5) {
                        monitor.wait();
                    }

                    Ship ship = Ship.generate(shipNamePrefix, globalCounter.incrementAndGet());
                    port.add(ship);
                    System.out.println("Генератор: Корабль " + ship.getName() + " " + ship.getType() +
                            " вошел в порт. В порту: " + port.size() + " кораблей.");

                    monitor.notifyAll();
                }

                long interval = minGenerationInterval +
                        (long) (Math.random() * (maxGenerationInterval - minGenerationInterval));
                Thread.sleep(interval);
            }
            System.out.println("Генератор: Все " + totalShipsToGenerate + " кораблей созданы. Завершаю работу.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Генератор: Работа прервана.");
        }
    }
}