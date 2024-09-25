package ru.ssau.tk.cucumber.oopLab1.Task5;

import ru.ssau.tk.cucumber.oopLab1.Task5.myfirstpackage.MyFirstPackage;

class MyFirstClass {
    public static void main(String[] args) {
        MyFirstPackage o = new MyFirstPackage(5, 6);
        int i, j;
        for (i = 1; i <= 8; i++) {
            for(j = 1; j <= 8; j++) {
                o.setBoka(i);
                o.setZhoka(j);
                System.out.print(o.activate());
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}