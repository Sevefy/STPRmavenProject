package ru.malofeev.module1;

import org.apache.commons.lang3.StringUtils;
import ru.malofeev.module1.ExceptionDemos.CustomException;

public class App {

    private static long testString(String v1, String v2) {
        String result = "";
        long time = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            result += v1;
            result += v2;
        }
        time = System.nanoTime() - time;
        return time;
    }

    private static long testStringBuilder(String v1, String v2){
        StringBuilder result = new StringBuilder();
        long time = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            result.append(v1);
            result.append(v2);
        }
        time = System.nanoTime() - time;
        return time;
    }

    private static long testStringBuffer(String v1, String v2){
        StringBuffer result = new StringBuffer();
        long time = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            result.append(v1);
            result.append(v2);
        }
        time = System.nanoTime() - time;
        return time;
    }



    public static void main(String[] args) throws CustomException {
        String text = "hi from module1";
        System.out.println("Capitalized: " + StringUtils.capitalize(text));

        String v1 = "Первая строка";
        String v2 = "Вторая строка";
        long ts = testString(v1, v2);
        long tbd = testStringBuilder(v1, v2);
        long tbf = testStringBuffer(v1, v2);

        long fastest = Math.min(ts, Math.min(tbd, tbf));
        System.out.printf("%s %s %s\n", ts, tbd, tbf);
        if (fastest == ts) {
            System.out.printf("String - САМЫЙ БЫСТРЫЙ - %s", ts);
        } else if (fastest == tbd) {
            System.out.printf("StringBuilder - САМЫЙ БЫСТРЫЙ - %s", tbd);
        } else {
            System.out.printf("StringBuffer - САМЫЙ БЫСТРЫЙ - %s", tbf);
        }

    }
}
