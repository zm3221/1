package com.sikalenko.java1101.hw2;

public class hw2_2 {
    public static void main(String[] args) {
        String a = "sloooojna";
        System.out.println("originalnaya stroka");
        System.out.println(a);
        System.out.println("zadom na pered");
        for (int i = (a.length() - 1); i >= 0; i--) {
            System.out.print(a.charAt(i));
        }
    }
}