package ru.ssau.tk.cucumber.oopLab2.functions.meta;

import ru.ssau.tk.cucumber.oopLab2.functions.Function;

public class Sum implements Function {
    private double leftDomainBorder;
    private double rightDomainBorder;
    Function func1;
    Function func2;

    public Sum(Function func1, Function func2){
        this.leftDomainBorder = Math.max(func1.getLeftDomainBorder(), func2.getLeftDomainBorder());
        this.rightDomainBorder = Math.min(func1.getRightDomainBorder(), func2.getRightDomainBorder());
        this.func1 = func1;
        this.func2 = func2;
    }
    @Override
    public double getLeftDomainBorder() {
        return leftDomainBorder;
    }

    @Override
    public double getRightDomainBorder() {
        return rightDomainBorder;
    }

    @Override
    public double getFunctionValue(double x) {
        return (func1.getFunctionValue(x)+func2.getFunctionValue(x));
    }
}
