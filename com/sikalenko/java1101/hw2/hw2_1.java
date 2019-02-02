package com.sikalenko.java1101.hw2;

public class hw2_1 {
    public static void main(String[] args) {
        String fio = "Сикаленко Андрей Александрович";
        for(int i=0;i<fio.length();i++){
            char c = fio.charAt(i);
            System.out.print((int)c + " ");
        }
        String[] parts = fio.split(" ");
        String f = parts[0];
        String i = parts[1];
        String o = parts[2];
        System.out.println("\n"+i+" "+o+" "+f);
        for (int j = (fio.length() - 1); j >= 0; j--) {
            System.out.print(fio.charAt(j));
        }



    }
}