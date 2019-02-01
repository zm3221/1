package com.sikalenko.java1101.hw1;

import java.util.Scanner;

public class hw1_8 {
    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        System.out.print("vvedite chislo ");
        int a = in.nextInt();
        int b = a % 2;
        if (b == 1) {
            System.out.print("NE chetnoe ");
        } else {
            System.out.print("chetnoe ");
        }
    }}