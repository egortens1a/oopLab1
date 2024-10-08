package ru.ssau.tk.cucumber.oopLab2.functions;

public class FunctionPoint {
    double x;
    double y;
    FunctionPoint(){
        this.x = 0;
        this.y = 0;
    }
    FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }
    FunctionPoint(FunctionPoint point){
        this.x = point.getX();
        this.y = point.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public void setPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
