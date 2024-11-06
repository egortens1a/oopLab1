package ru.ssau.tk.cucumber.oopLab2.functions.meta;

import ru.ssau.tk.cucumber.oopLab2.functions.Function;

public class Composition implements Function {

    private double leftDomainBorder;
    private double rightDomainBorder;
    Function func1;
    Function func2;

    public Composition(Function func1, Function func2){
        rightDomainBorder = func2.getRightDomainBorder();
        leftDomainBorder = func2.getLeftDomainBorder();
        this.func1 = func1;
        this.func2 = func2;
    }
    public double getLeftDomainBorder() {
        return leftDomainBorder;
    }

    public double getRightDomainBorder() {
        return rightDomainBorder;
    }

    public double getFunctionValue(double x) {
        return func1.getFunctionValue(func2.getFunctionValue(x));
    }
}
