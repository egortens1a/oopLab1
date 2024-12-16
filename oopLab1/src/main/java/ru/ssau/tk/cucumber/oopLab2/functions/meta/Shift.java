package ru.ssau.tk.cucumber.oopLab2.functions.meta;

import ru.ssau.tk.cucumber.oopLab2.functions.Function;

public class Shift implements Function {
    private final double leftDomainBorder;
    private final double rightDomainBorder;
    private final double shiftX;
    private final double shiftY;
    private final Function func;

    public Shift(Function func, double shiftX, double shiftY){
        leftDomainBorder = func.getLeftDomainBorder()+shiftX;
        rightDomainBorder = func.getRightDomainBorder()+shiftX;
        this.func = func;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }


    public double getLeftDomainBorder() { return leftDomainBorder; }

    public double getRightDomainBorder() { return rightDomainBorder; }

    public double getFunctionValue(double x) { return func.getFunctionValue(x - shiftX) - shiftY; }
}