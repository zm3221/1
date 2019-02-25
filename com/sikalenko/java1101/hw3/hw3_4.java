package com.sikalenko.java1101.hw3;

import java.util.Scanner;

public class hw3_4 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("vvedite 1 katet ");
        double a = (in.nextDouble());
        System.out.print("vvedite 2 katet ");
        double b = (in.nextDouble());
        double c = (a * a) + (b * b);
        c = Math.sqrt(c);
        System.out.println("gipotenuza = " + c);
        System.out.println("ugol vozle 1 kateta = " + Math.toDegrees(Math.asin(b / c)));
        System.out.println("ugol vozle 2 kateta = " + Math.toDegrees(Math.asin(a / c)));
    }
}
