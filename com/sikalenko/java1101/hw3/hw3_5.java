package com.sikalenko.java1101.hw3;

public class hw3_5 {
    public static void main(String[] args) {
        int[] a = new int[10];
        System.out.println("do sortirovki");
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (Math.random() * 10);
            System.out.print(a[i] + " ");
        }
        System.out.println("\n" + "posle sortirovki");
        for (int i = 0; i < a.length; i++) {
            for (int j = a.length - 1; j > i; j--) {
                if (a[j - 1] > a[j]) {
                    int b = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = b;
                }
            }
            System.out.print(a[i] + " ");
        }
    }
}

