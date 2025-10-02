package ru.malofeev.module1.ExceptionDemos;

import java.util.Random;

public class DemoException {
    private void process(int value) throws CustomException {
        if (value < 0) throw new RuntimeException("Полученное число меньше нуля!");

        if (value > 10) throw new CustomException("Число больше 10");

        System.out.println("Число в рамке от 0 до 10");

    }
    public void run(){
        Random rand = new Random();
        for (int i = 1; i < 10; i++){
            int x = rand.nextInt(-5,15);
            try{
                process(x);
            }catch (CustomException | RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }
    public static void main(String[] args) {
        DemoException demo  = new DemoException();
        demo.run();
    }
}
