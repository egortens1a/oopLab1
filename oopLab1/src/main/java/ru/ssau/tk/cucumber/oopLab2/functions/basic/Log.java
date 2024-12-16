package ru.ssau.tk.cucumber.oopLab2.functions.basic;

import ru.ssau.tk.cucumber.oopLab2.functions.Function;

public class Log implements Function {
    private double base;

    public Log(double base){
        this.base = base;
    }

    public Log(){
        this.base = Math.E;
    }

    public double getLeftDomainBorder() {
        return 0;
    }

    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    public double getFunctionValue(double x) {
        return Math.log(x);
    }

    public double getFunctionValue(double x, double a) {
        return Math.log(x) / Math.log(a);
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }
}
