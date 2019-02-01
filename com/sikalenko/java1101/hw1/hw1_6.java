package com.sikalenko.java1101.hw1;

import java.util.Arrays;
import java.util.Scanner;

public class hw1_6 {
    public static void main(String[] args) {
        double[] a = {5, 6, 7, 5, 6, 7};
        Scanner in = new Scanner(System.in);
        System.out.print("vvedite nomer elementa ");
        int x = in.nextInt();
        a[x] = a[x]*1.1;
        System.out.println(Arrays.toString(a));
    }}