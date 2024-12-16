package ru.ssau.tk.cucumber.oopLab2.threads;

import ru.ssau.tk.cucumber.oopLab2.functions.Function;

public class Task {
    private Function function;
    private double leftDomainBorder;
    private double rightDomainBorder;
    private double step;
    private int taskCount;

    public Task(Function function, double leftDomainBorder, double rightDomainBorder, double step, int taskCount) {
        this.function = function;
        this.leftDomainBorder = leftDomainBorder;
        this.rightDomainBorder = rightDomainBorder;
        this.step = step;
        this.taskCount = taskCount;
    }
    public Function getFunction(){ return function; }
    public void setFunction(Function function) { this.function = function; }

    public double getLeftDomainBorder(){ return leftDomainBorder; }
    public void setLeftDomainBorder(double LDB){ this.leftDomainBorder = LDB; }

    public double getRightDomainBorder(){ return rightDomainBorder; }
    public void setRightDomainBorder(double RDB){ this.rightDomainBorder = RDB; }

    public double getStep(){ return step; }
    public void setStep(double step) { this.step = step; }

    public int getTaskCount(){ return taskCount; }
    public void setTaskCount(int count){ this.taskCount = count; }

    public void update(Function function, double leftDomainBorder, double rightDomainBorder, double step) {
        this.function = function;
        this.leftDomainBorder = leftDomainBorder;
        this.rightDomainBorder = rightDomainBorder;
        this.step = step;
    }
}
