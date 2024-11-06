package ru.ssau.tk.cucumber.oopLab2.functions.basic;

import ru.ssau.tk.cucumber.oopLab2.functions.Function;

public class Log implements Function {
    public double getLeftDomainBorder() {
        return 0;
    }

    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    public double getFunctionValue(double x) {
        return Math.log(x);
    }
}
