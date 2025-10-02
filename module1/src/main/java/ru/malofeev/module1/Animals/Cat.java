package ru.malofeev.module1.Animals;

import java.util.Objects;

class Cat extends Animal {

    public Cat(String name, float weight) {
        super(name, weight);
    }

    @Override
    public void speak() {
        System.out.printf("Мяу, мое имя %s, мой вес %f\n", this.name, this.weight);
    }

    @Override
    public String toString() {
        if (Objects.equals(name, "Матроскин")) return "Я Кот по имени %s. Умею вышивать на швейной машинке. Мой вес %f".formatted(this.name, this.weight);
        return "Я Кот по имени %s. Хочу быть как Матроскин. Мой вес %f".formatted(this.name, this.weight);
    }

}
