package com.sikalenko.java1101.hw1;

import java.util.Scanner;

public class hw1_13 {
    public static void main(String []args) {
        Scanner in = new Scanner(System.in);
        System.out.print("vvedite kolichesvo sutok ");
        double n = in.nextDouble();
        System.out.println("kolichesvo chasov "+n*24);
        System.out.println("kolichesvo minut "+n*24*60);
        System.out.println("kolichesvo sekund "+n*24*60*60);
}}
