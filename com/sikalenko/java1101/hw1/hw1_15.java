package com.sikalenko.java1101.hw1;

import java.util.Scanner;
import static java.lang.Math.*;

public class hw1_15 {
    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        System.out.print("vvedite katet a ");
        double a = in.nextDouble();
        System.out.print("vvedite katet b ");
        double b = in.nextDouble();
        double c = sqrt((a*a)+(b*b));
        System.out.println("ploshad' = "+(0.5*a*b));
        System.out.println("perimetr = "+(a+b+c));
        System.out.println("gipotenuza = "+c);
}}
