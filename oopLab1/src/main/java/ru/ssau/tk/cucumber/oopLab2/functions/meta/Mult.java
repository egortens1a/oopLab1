package ru.ssau.tk.cucumber.oopLab2.functions.meta;

import ru.ssau.tk.cucumber.oopLab2.functions.Function;

public class Mult implements Function {
    private double leftDomainBorder;
    private double rightDomainBorder;
    Function func1;
    Function func2;

    public Mult(Function func1, Function func2){
        this.leftDomainBorder = Math.max(func1.getLeftDomainBorder(), func2.getLeftDomainBorder());
        this.rightDomainBorder = Math.min(func1.getRightDomainBorder(), func2.getRightDomainBorder());
        this.func1 = func1;
        this.func2 = func2;
    }

    public double getLeftDomainBorder() {
        return this.leftDomainBorder;
    }

    public double getRightDomainBorder() {
        return rightDomainBorder;
    }

    public double getFunctionValue(double x) {
        return (func1.getFunctionValue(x)*func2.getFunctionValue(x));
    }
}
