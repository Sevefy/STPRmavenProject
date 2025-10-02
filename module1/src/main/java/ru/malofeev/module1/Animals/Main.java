package ru.malofeev.module1.Animals;

import java.util.*;

import org.kohsuke.randname.RandomNameGenerator;
public class Main {
    public static Animal[] generatorAnimals(int N){
        Animal[] animals = new Animal[N];

        RandomNameGenerator rndName = new RandomNameGenerator();
        Random rndWeight = new Random();
        Random rndCoin = new Random();
        for (int i = 0; i < N; i++){
            float weight = rndWeight.nextFloat(2F, 100F);
            String name = rndName.next();
            if (rndCoin.nextBoolean()){
                animals[i] = new Dog(name, weight);
                continue;
            }
            animals[i] = new Cat(name, weight);
        }
        return animals;
    }


    public static void main(String[] args) {
        Collection<Animal> animals = new ArrayList<Animal>();
        animals.add(new Dog("Шарик", 10F));
        animals.add(new Dog("Бобик", 4.4F));
        animals.add(new Cat("Матроскин", 3.4F));
        animals.add(new Cat("Гараж", 4.1F));
//        animals.forEach(Animal::speak);

//        animals.forEach(System.out::println);

        if (args.length == 0) return;
        try{
            int N = Integer.valueOf(args[0]);
            Animal[] animals2 = generatorAnimals(N);
            Arrays.sort(animals2);
            for (Animal animal : animals2) {
                System.out.println(animal);
            }

        }catch (NumberFormatException ex){
            System.out.println("Введено не число, генерация невозможна");
            return;
        }


    }
}
