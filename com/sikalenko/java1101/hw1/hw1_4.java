package com.sikalenko.java1101.hw1;

public class hw1_4 {
    public static void main(String []args){
        int n =362;
        int b =n/100;
        int c =n%100;
        int e = c/10;
        int f = c%10;
        int d =b+f+e;
        System.out.print("summa tsifr chisla "+n+" = "+d);
    }
}

