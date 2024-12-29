package ru.ssau.tk.cucumber.oopLab2.functions;

public interface TabulatedFunctionFactory {
    TabulatedFunction createTabulatedFunction(FunctionPoint[] points);
    TabulatedFunction createTabulatedFunction(double left, double right, int pointsCount);
    TabulatedFunction createTabulatedFunction(double left, double right, double[] val);
}
