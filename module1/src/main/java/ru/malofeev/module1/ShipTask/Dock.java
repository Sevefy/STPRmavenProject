// Dock.java
package ru.malofeev.module1.ShipTask;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.malofeev.module1.ShipTask.Ship.Ship;
import ru.malofeev.module1.ShipTask.Ship.TypeShip;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@AllArgsConstructor
@Getter
public class Dock implements Runnable {
    private final String dockName;
    private final TypeShip handledTypeShip;
    private final List<Ship> port;
    private final Object monitor;
    private final long unloadTimePerUnit;
    private final AtomicInteger counter;


    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Ship shipToUnload = null;

                synchronized (monitor) {
                    while (shipToUnload == null) {
                        shipToUnload = findShipByType();
                        if (shipToUnload == null) {
                            if (counter.get() >= 20 && port.isEmpty()) {
                                return;
                            }
                            monitor.wait();
                        }
                    }

                    port.remove(shipToUnload);
                    System.out.println(dockName + ": Взял корабль " +  shipToUnload.getName() + " " + shipToUnload.getType() +
                            " на разгрузку. В порту осталось: " + port.size() + " кораблей.");

                    monitor.notifyAll();
                }

                long unloadTime = shipToUnload.getSize() * unloadTimePerUnit;
                System.out.println(dockName + ": Начинаю разгрузку корабля " +  shipToUnload.getName() + " " + shipToUnload.getType());
                Thread.sleep(unloadTime);

                counter.incrementAndGet();
                System.out.println(dockName + ": Корабль " +  shipToUnload.getName() + " " + shipToUnload.getType() +
                        " разгружен за " + unloadTime + " мс.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(dockName + ": Работа прервана.");
        }
    }

    private Ship findShipByType() {
        for (Ship ship : port) {
            if (ship.getType() == handledTypeShip) {
                return ship;
            }
        }
        return null;
    }
}