package com.sikalenko.java1101.hw1;

import java.util.Scanner;

public class hw1_16 {
    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        System.out.print("vvedite temperaturu po tselsiu ");
        double a = in.nextDouble();
        System.out.print("temperatura po farengeytu = "+((a*9/5) + 32));
}}
