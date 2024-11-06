package ru.ssau.tk.cucumber.oopLab2.functions.basic;

import ru.ssau.tk.cucumber.oopLab2.functions.Function;

public class TrigonometricFunction implements Function {
    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
    }

    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    public double getFunctionValue(double x) {
        return Double.NaN;
    }
}
