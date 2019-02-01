package com.sikalenko.java1101.hw1;

import java.util.Scanner;

public class hw1_12 {
    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        System.out.print("vvedite rastoyanie(v kilometrah) ");
        Double n = in.nextDouble();
        System.out.print("vvedite vremya(v chasah) ");
        double t = in.nextDouble();
        double a = n/t;
        System.out.print("neobhodimaya skorost' "+a+"km/chas");
}}
