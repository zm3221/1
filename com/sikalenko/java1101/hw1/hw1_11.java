package com.sikalenko.java1101.hw1;
import java.util.Scanner;

public class hw1_11 {
    public static void main(String []args){


        System.out.println("vvedite chislo ");
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        System.out.println("do kakogo znacheniya nujna tablitsa ");
        int x = in.nextInt();

        for(int i = 1; i<x;i++){
        System.out.println(a+"*"+i+"="+(a*i));}}}
