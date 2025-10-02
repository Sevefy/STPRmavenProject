package ru.malofeev.module1.Animals;

import java.util.Objects;

abstract class Animal implements Comparable<Animal> {
    protected String name;
    protected float weight;

    public Animal(String name, float weight){
        this.name = name;
        this.weight = weight;
    }

    public abstract void speak();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Animal animal = (Animal) obj;
        return  Float.compare(animal.weight, weight) == 0 &&
                Objects.equals(name, animal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight);
    }

    @Override
    public int compareTo(Animal o) {
        return Float.compare(this.weight, o.weight);
    }

    @Override
    public String toString() {
        return String.format("%s: имя='%s', вес=%.1f",
                getClass().getSimpleName(), name, weight);
    }

    public float getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
}

