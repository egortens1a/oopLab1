package ru.ssau.tk.cucumber.oopLab2.functions;

public class TabulatedFunction {
    FunctionPoint[] values;
    double leftX;
    double rightX;
    TabulatedFunction(double leftX, double rightX, int pointsCount){
        this.leftX = leftX;
        this.rightX = rightX;
        this.values = new FunctionPoint[pointsCount];
        double x = leftX;
        for (int i = 0; i < pointsCount; ++i){
            this.values[i].setPoint(x,0);
            x+=(rightX-leftX)/values.length;
        }
    }
    TabulatedFunction(double leftX, double rightX, double[] values){
        this.leftX = leftX;
        this.rightX = rightX;
        this.values = new FunctionPoint[values.length];
        double x = leftX;
        for (int i = 0; i < values.length; ++i){
            this.values[i].setPoint(x,values[i]);
            x+=(rightX-leftX)/values.length;
        }
    }
}
