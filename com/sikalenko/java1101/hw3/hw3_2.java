package com.sikalenko.java1101.hw3;

import java.util.Scanner;

public class hw3_2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("skolvo gradusov ugol? ");
        String a = (in.nextLine());
        double b = Double.parseDouble(a);
        double c = Math.toRadians(b);
        System.out.println("sin = " + Math.sin(c));
        System.out.println("cos = " + Math.cos(c));
    }
}
