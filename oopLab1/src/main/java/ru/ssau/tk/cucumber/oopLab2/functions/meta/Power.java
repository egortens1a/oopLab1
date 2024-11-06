package ru.ssau.tk.cucumber.oopLab2.functions.meta;

import ru.ssau.tk.cucumber.oopLab2.functions.Function;

public class Power implements Function {
    private final double leftDomainBorder;
    private final double rightDomainBorder;
    private final double degree;
    private final Function func;

    public Power(Function func, double degree){
        leftDomainBorder = func.getLeftDomainBorder();
        rightDomainBorder = func.getRightDomainBorder();
        this.func = func;
        this.degree = degree;
    }

    public double getLeftDomainBorder() {
        return leftDomainBorder;
    }

    public double getRightDomainBorder() {
        return rightDomainBorder;
    }

    public double getFunctionValue(double x) {
        return Math.pow(func.getFunctionValue(x), degree);
    }
}
