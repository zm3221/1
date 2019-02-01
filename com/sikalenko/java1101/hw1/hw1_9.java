package com.sikalenko.java1101.hw1;

import java.util.Scanner;

public class hw1_9 {
    public static void main(String []args){
/*
        System.out.println("vvedite chislo tipa Double ");
        Scanner in = new Scanner(System.in);
        double a = in.nextDouble();
*/
        double a=1.6;
        double b=a*10;
        b = (int) b;
        double c = b%10;
        if (c!=0) {
            System.out.println("Chislo ne tseloe");
        }else {
            System.out.println("chislo tseloe");
        }
}}
