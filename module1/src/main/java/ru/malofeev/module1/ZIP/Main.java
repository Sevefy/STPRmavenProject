package ru.malofeev.module1.ZIP;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MethodZIP inputMethod = null;


        Scanner scanner = new Scanner(System.in);
        System.out.println("Ввыберите метод:\n1 - Упаковка\n2 - Распаковка");
        do {
            try{
                int numberOperation = scanner.nextInt();
                switch (numberOperation){
                    case 1: inputMethod = MethodZIP.pack; break;
                    case 2: inputMethod = MethodZIP.unpack; break;
                    default:
                        System.out.println("Введите 1 или 2!");
                        break;
                }
            }catch (InputMismatchException e){
                System.out.println("1 или 2!");
                scanner.next();
            }
        }while (inputMethod == null);

        System.out.print("Название архива: ");
        String nameZIP = scanner.next().trim();

        System.out.print("Папка: ");
        String catalog = scanner.next().trim();

        Zip zip = new Zip(nameZIP, catalog);
        try {
            if (inputMethod.equals(MethodZIP.pack)) zip.pack();
            else zip.unpack();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
