package ru.malofeev.module1.CollectionDemos;

import java.util.*;

public class Main {
    static class Pair {
        int num1;
        int num2;

        Pair(int num1, int num2) {
            this.num1 = num1;
            this.num2 = num2;
        }

        int getSum() {
            return num1 + num2;
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) return;
        try {
            int N = Integer.valueOf(args[0]);
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < N; i++){
                list.add(new Random().nextInt(10000));
            }
            Set<Integer> set = new HashSet<>(list);
            System.out.printf("Всего элементов: %d\nУникальных: %d\n", list.size(), set.size());
            Collections.sort(list);

            List<Pair> pairs = new ArrayList<>();
            int left = 0;
            int right = list.size() - 1;

            while (left < right) {
                pairs.add(new Pair(list.get(left), list.get(right)));
                left++;
                right--;
            }

            // Если нечетное количество элементов, добавляем оставшийся элемент с 0
            if (left == right) {
                pairs.add(new Pair(list.get(left), 0));
            }
            double average = list.stream().mapToInt(Integer::intValue).average().orElse(0);

            double totalDev = 0;
            for (Pair elem : pairs){
                System.out.printf("%d + %d = %d\n", elem.num1, elem.num2, elem.getSum());
                totalDev += Math.abs(elem.getSum() - average * 2);
            }
            System.out.printf("Среднее значение списка: %f\n", average);
            System.out.printf("Сумма отклонений: %f", totalDev);


        }catch (NumberFormatException ex){
            System.out.println("Надо число!");
            return;
        }
    }
}
