package functions;

import static java.lang.Math.*;

public class ArrayTabulatedFunction implements TabulatedFunction{

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

    public ArrayTabulatedFunction(double left, double right, int pointsCount){
        if (Double.compare(left, right) == 1){
            throw new IllegalArgumentException("LeftDomainBorder > RightDomainBorder");
        }
        this.pointsCount = pointsCount;
        values = new FunctionPoint[pointsCount+5];
        double x = left;
        for (int i = 0; i < pointsCount; ++i){
            values[i] = new FunctionPoint(x,0);
            x+=(right-left)/pointsCount;
        }
    }

    public ArrayTabulatedFunction(double left, double right, double[] val){
        if (Double.compare(left, right) == 1){
            throw new IllegalArgumentException("LeftDomainBorder > RightDomainBorder");
        }
        this.pointsCount = val.length;
        values = new FunctionPoint[pointsCount+5];
        double x = left;
        for (int i = 0; i < pointsCount; ++i){
            values[i] = new FunctionPoint(x,val[i]);
            x+=(right-left)/val.length;
        }
    }

    public double getPointX(int index){
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        return values[index].getX();
    }

    public double getPointY(int index){
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        return values[index].getY();
    }

    public FunctionPoint getPoint(int index){
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        return values[index];
    }

    public void setPointY(int index, double y){
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        values[index].setY(y);
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        setPoint(index, new FunctionPoint(x, getPointY(index)));
    }

    //координата x задаваемой точки лежит вне интервала, определяемого значениями соседних точек табулированной функции.
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {

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

    public void deletePoint(int index){
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
}
