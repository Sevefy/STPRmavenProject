package ru.malofeev.module1.ShipTask;

import lombok.AllArgsConstructor;
import ru.malofeev.module1.ShipTask.Ship.Ship;
import ru.malofeev.module1.ShipTask.Ship.TypeShip;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class Dock implements Runnable {
    private final String dockName;
    private final TypeShip handledTypeShip;
    private final BlockingQueue<Ship> harbor;
    private final long unloadTimePerUnit;

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Ship ship = harbor.take();

                if (ship.getType() == handledTypeShip) {
                    System.out.println(dockName + ": Начинаю разгрузку корабля " + ship.getName() +
                            " с грузом " + ship.getSize() + " единиц " + ship.getType());

                    long unloadTime = ship.getSize() * unloadTimePerUnit;
                    Thread.sleep(unloadTime);

                    System.out.println(dockName + ": Корабль " + ship.getName() + " успешно разгружен за " + unloadTime + " мс.");

                } else {
                    harbor.put(ship);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println(dockName + ": Работа прервана.");
        }
    }
}