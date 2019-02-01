package com.sikalenko.java1101.hw1;

import java.util.Scanner;

public class hw1_14 {
    public static void main(String []args) {
        System.out.println("vvedite kurs grivni k dollaru ");
        Scanner in = new Scanner(System.in);
        double k = in.nextDouble();
        System.out.println("skolko griven obmenyat' ");
        double g = in.nextDouble();
        double d = g/k;
        System.out.print(g+" griven = "+d+
                " dolarov po kursu "+k);
}}
