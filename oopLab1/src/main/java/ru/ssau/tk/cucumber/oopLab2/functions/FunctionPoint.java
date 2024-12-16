package ru.ssau.tk.cucumber.oopLab2.functions;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class FunctionPoint implements Serializable, Cloneable {
    @Serial
    private static final long serialVersionUID = 5743089384985590368L;

    private double x;
    private double y;

    public FunctionPoint(){
        x = 0;
        y = 0;
    }
    public FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }
    public FunctionPoint(FunctionPoint point){
        x = point.getX();
        y = point.getY();
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    public void setPoint(FunctionPoint p) {
        x = p.getX();
        y = p.getY();
    }
    public String toString(){
        return "(" + String.format("%.10f", x) + "; "+ String.format("%.10f", y) + ")";
    }
    public boolean equals(Object o){
        if (this.getClass() != o.getClass())
            return false;
        else {
            return Math.abs(this.x - ((FunctionPoint) o).getX()) < 0.0000001
                    && Math.abs(this.y - ((FunctionPoint) o).getY()) < 0.0000001;
        }
    }

    public int hashCode() {
        return Objects.hash(x,y);
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
