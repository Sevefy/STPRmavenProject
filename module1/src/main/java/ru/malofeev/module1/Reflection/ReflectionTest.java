package ru.malofeev.module1.Reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;
import static ru.malofeev.module1.Reflection.ReflectionUser.*;
public class ReflectionTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String className = scanner.next().trim();
        Class cls = Class.forName(className);
        Method[] m = cls.getMethods();
        Arrays.stream(m).forEach(System.out::println);

        Field[] fields = cls.getDeclaredFields();
        Arrays.stream(fields).forEach(System.out::println);


    }
}
