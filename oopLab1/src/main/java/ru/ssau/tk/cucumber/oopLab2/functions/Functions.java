package ru.ssau.tk.cucumber.oopLab2.functions;

import ru.ssau.tk.cucumber.oopLab2.functions.meta.*;

public class Functions{
    public static Function shift(Function f, double shiftX, double shiftY){
        return new Shift(f, shiftX,shiftY);
    } // - возвращает объект функции, полученной из исходной сдвигом вдоль осей

    public static Function scale(Function f, double scaleX, double scaleY){
        return new Scale(f,scaleX,scaleY);
    } // - возвращает объект функции, полученной из исходной масштабированием вдоль осей;

    public static Function power(Function f, double power){
        return new Power(f,power);
    } // – возвращает объект функции, являющейся заданной степенью исходной;

    public static Function sum(Function f1, Function f2){
        return new Sum(f1,f2);
    }// – возвращает объект функции, являющейся суммой двух исходных;

    public static Function mult(Function f1, Function f2){
        return new Mult(f1,f2);
    }// – возвращает объект функции, являющейся произведением двух исходных;

    public static Function composition(Function f1, Function f2){
        return new Composition(f1,f2);
    }// – возвращает объект функции, являющейся композицией двух исходных.
}
