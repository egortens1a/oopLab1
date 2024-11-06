package ru.ssau.tk.cucumber.oopLab2.functions.meta;

import ru.ssau.tk.cucumber.oopLab2.functions.Function;

public class Scale implements Function {
    private final double leftDomainBorder;
    private final double rightDomainBorder;
    private final double scaleX;
    private final double scaleY;
    private final Function func;

    public Scale(Function func, double scaleX, double scaleY){
        leftDomainBorder = func.getLeftDomainBorder()/scaleX;
        rightDomainBorder = func.getRightDomainBorder()/scaleX;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.func = func;
    }
    public double getLeftDomainBorder() {
        return leftDomainBorder;
    }

    public double getRightDomainBorder() {
        return rightDomainBorder;
    }

    public double getFunctionValue(double x) {
        return func.getFunctionValue(x / scaleX) / scaleY;
    }
}
