package ru.ssau.tk.cucumber.oopLab2.functions;

public class TabulatedFunction {
    FunctionPoint[] values;
    double leftX;
    double rightX;

    public double getLeftDomainBorder(){
        return this.leftX;
    }
    public double getRightDomainBorder(){
        return this.rightX;
    }
    double getFunctionValue(double x){
        if (this.leftX < x && x < this.rightX){
            for (int i = 0; i < this.values.length-1; ++i){
                if (this.values[i].getX() > x){
                    double x1 = this.values[i].getX();
                    double x2 = this.values[i+1].getX();
                    double y1 = this.values[i].getY();
                    double y2 = this.values[i+1].getY();
                    /*
                    (x-x1)/(x2-x1) = (y-y1)/(y2-y1)
                                <==>
                    y = (x-x1)/(x2-x1)*(y2-y1)+y1
                    */
                    return (x-x1)/(x2-x1)*(y2-y1)+y1;
                }
            }
        }
        return Double.NaN;
    }

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
