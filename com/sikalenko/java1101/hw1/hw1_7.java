package com.sikalenko.java1101.hw1;

import java.util.Scanner;

public class hw1_7 {
    public static void main(String []args){
        Scanner in = new Scanner(System.in);
        System.out.print("vvedite pervoe chislo ");
        int a = in.nextInt();
        System.out.print("vvedite vtoroe chislo ");
        int b = in.nextInt();
        if(a>b) {
            System.out.print(a + ">" + b);
        }
            else{
                if(a==b) {
                    System.out.print(a+"="+b);}
                    else{ System.out.print(a+"<"+b);
                }
        }
    }
}
