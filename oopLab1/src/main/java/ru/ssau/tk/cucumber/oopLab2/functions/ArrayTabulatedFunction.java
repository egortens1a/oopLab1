package ru.ssau.tk.cucumber.oopLab2.functions;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable, Cloneable, Iterable<FunctionPoint> {

    @Serial
    private static final long serialVersionUID = -6256072091640092596L;

    private FunctionPoint[] values;
    private int pointsCount;

    public double getLeftDomainBorder() {
        return values[0].getX();
    }

    public double getRightDomainBorder(){
        return values[pointsCount-1].getX();
    }

    public double getFunctionValue(double x){
        if (Double.compare(getLeftDomainBorder(), x)<=0 && Double.compare(x, getRightDomainBorder())<=0){//double compare
            for (int i = 1; i < pointsCount; ++i){
                if (Double.compare(values[i].getX(), x) >= 0){
                    double x1 = values[i-1].getX();
                    double x2 = values[i].getX();
                    double y1 = values[i-1].getY();
                    double y2 = values[i].getY();
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

    public int getPointsCount() {
        return pointsCount;
    }

    public ArrayTabulatedFunction(double left, double right, int pointsCount) throws IllegalStateException{
        if (Double.compare(left, right) >= 0){
            throw new IllegalArgumentException("LeftDomainBorder > RightDomainBorder");
        }
        this.pointsCount = pointsCount;
        values = new FunctionPoint[pointsCount+5];
        double x = left;
        for (int i = 0; i < pointsCount; ++i){
            values[i] = new FunctionPoint(x,0);
            x+=(right-left)/(pointsCount-1);
        }
    }
    public ArrayTabulatedFunction(FunctionPoint[] points) throws IllegalStateException{
        this.pointsCount = points.length;
        if (this.pointsCount < 2){
            throw new IllegalArgumentException("Мало точек");
        }
        for (int i = 1; i<pointsCount; i++){
            if (Double.compare(points[i - 1].getX(), points[i].getX()) >= 0){
                throw new IllegalArgumentException("точки Х не по порядку");
            }
        }
        values = new FunctionPoint[pointsCount+5];
        System.arraycopy(points, 0, values, 0, this.pointsCount);
    }
    public ArrayTabulatedFunction(double left, double right, double[] val) throws IllegalStateException{
        if (Double.compare(left, right) >= 0){
            throw new IllegalArgumentException("LeftDomainBorder > RightDomainBorder");
        }
        this.pointsCount = val.length;
        values = new FunctionPoint[pointsCount+5];
        double x = left;
        for (int i = 0; i < pointsCount; ++i){
            values[i] = new FunctionPoint(x,val[i]);
            x+=(right-left)/(pointsCount-1);
        }
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        return values[index].getX();
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        return values[index].getY();
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        return values[index];
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        values[index].setY(y);
    }

    public void setPointX(int index, double x) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        setPoint(index, new FunctionPoint(x, getPointY(index)));
    }

    //координата x задаваемой точки лежит вне интервала, определяемого значениями соседних точек табулированной функции.
    public void setPoint(int index, FunctionPoint point) throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException {

        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        if (!(Double.compare(point.getX(), getPointX(index-1)) == 1 && Double.compare(point.getX(), getPointX(index+1)) == -1)){
            throw new InappropriateFunctionPointException("x не попадает в промежуток (x[i-1],x[i+1]): ("+getPointX(index-1)+","+getPointX(index+1)+")");
        }

        if ((index == 0 && Double.compare(values[1].getX(), values[0].getX()) == 1)
                || (index == (pointsCount - 1) 
                        && Double.compare(values[index].getX(), values[index - 1].getX()) == 1)
                || (Double.compare(values[index].getX(), values[index-1].getX()) == 1 
                        && Double.compare(values[index+1].getX(), values[index].getX()) == 1))
        {
            values[index].setPoint(point);
        }
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException{
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        if (pointsCount < 3){
            throw new IllegalStateException("Слишком мало точек");
        }
        System.arraycopy(values, index+1, values, index, pointsCount - index-1);
        --pointsCount;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        int index = 0; //индекс для нашей точки
        while(index != pointsCount && Double.compare(point.getX(), values[index].getX()) == 1) {
            if (Double.compare(values[index].getX(), point.getX()) == 0) {
                throw new InappropriateFunctionPointException("Такой X уже существует!");
            }
            ++index;
        }

        ++pointsCount;

        if (pointsCount == values.length){
            System.out.println("массив увеличился!");
            FunctionPoint[] newValues = new FunctionPoint[pointsCount+5];
            System.arraycopy(values, 0, newValues, 0, index);
            newValues[index] = point;
            System.arraycopy(values, index, newValues, index+1, pointsCount-index);
            values = newValues;
        }
        else {
            System.out.println("массив НЕ увеличился!");
            System.arraycopy(values, index, values, index+1, pointsCount-index);
            values[index] = point;
        }
    }

    public String toString(){
        String[] points = new String[pointsCount];
        for (int i = 0; i<pointsCount; i++){
            points[i] = values[i].toString();
        }
        return "{" + String.join(", ", points) + "}";
    }

    public boolean equals(Object o){
        if (!(o instanceof TabulatedFunction))
            return false;

        TabulatedFunction function_o = (TabulatedFunction) o;

        if (function_o.getPointsCount() != pointsCount)
            return false;
        else {
            for (int i = 0; i <pointsCount; i++){
                if (!this.getPoint(i).equals(function_o.getPoint(i)))
                    return false;
            }
        }
        return true;
    }
    public int hashCode(){
        return Objects.hash(pointsCount, this.toString());
    }

    public Object clone() {
        FunctionPoint[] cloned_points = new FunctionPoint[pointsCount];
        for (int i = 0; i < pointsCount; i++) {
            try {
                cloned_points[i] = (FunctionPoint) values[i].clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return new ArrayTabulatedFunction(cloned_points);
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index != pointsCount;
            }

            @Override
            public FunctionPoint next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                FunctionPoint point = null;
                try {
                    point = (FunctionPoint) getPoint(index).clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
                index++;
                return point;
            }
        };
    }
    public static class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory{

        @Override
        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new ArrayTabulatedFunction(points);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double left, double right, int pointsCount) {
            return new ArrayTabulatedFunction(left, right, pointsCount);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double left, double right, double[] val) {
            return new ArrayTabulatedFunction(left, right, val);
        }
    }

}
