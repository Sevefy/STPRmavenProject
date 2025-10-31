package ru.malofeev.module1.ShipTask.Ship;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@AllArgsConstructor
public class Ship {
    private String name;
    private TypeShip type;
    private int size;


    public static Ship generate(String name, int number){
        String localName = name + number;
        TypeShip localType = TypeShip.values()[ThreadLocalRandom.current().nextInt(TypeShip.values().length)];
        int localSize = ThreadLocalRandom.current().nextInt(1,6);
        return new Ship(localName, localType, localSize);
    }

    @Override
    public String toString(){
        return type + " - " + name + ":" + size;
    }
}
