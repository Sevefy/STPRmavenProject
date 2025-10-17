package ru.malofeev.module1.Reflection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReflectionUser {

    private int age;
    private double weight;
    private double height;

    public double proportion(){
        return height / weight;
    }

    public double proportionByW(double w){
        return (height / weight) * w;
    }
}
