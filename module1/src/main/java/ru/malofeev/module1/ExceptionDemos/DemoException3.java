package ru.malofeev.module1.ExceptionDemos;

public class DemoException3 {
    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Пустые параметры");
            return;
        }
        int sum = 0;
        for (String arg : args){
            try{
                int num = Integer.valueOf(arg);
                sum += num;
            } catch (NumberFormatException e){
                System.out.println("В параметрах есть нечисло: " + arg);
                return;
            }
        }
        System.out.println(sum);
    }
}
