package ru.malofeev.module1.Animals;

import java.util.Objects;

class Dog extends Animal{
    public Dog(String name, float weight) {
        super(name, weight);
    }
    @Override
    public void speak(){
        System.out.printf("Гав, меня зовут %s, мой вес %f\n", this.name, this.weight);;
    }

    @Override
    public String toString() {
        if(Objects.equals(name, "Шарик")) return "Я Пёс по имени %s, и у меня есть фоторужьё. Мой вес %f".formatted(this.name, this.weight);
        return "Я Пёс по имени %s, и у меня есть только хвост. Мой вес %f".formatted(this.name, this.weight);
    }

}
