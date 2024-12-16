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

    public static double integral(Function f1, double leftDomainBorder, double rightDomainBorder, double step) throws IllegalArgumentException{
        try {
            if (step <= 0) {
                throw new IllegalArgumentException("Шаг должен быть >0 (step = " + step + ")");
            }
            double area = 0.0;
            double x = leftDomainBorder;

            while (x < rightDomainBorder) {
                double currX = Math.min(x + step, rightDomainBorder);
                area += (f1.getFunctionValue(x) + f1.getFunctionValue(currX)) / 2 * (currX - x);
                x = currX;
            }
            return area;
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
