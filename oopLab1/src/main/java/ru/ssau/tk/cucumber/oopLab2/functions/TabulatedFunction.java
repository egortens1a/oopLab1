package functions;

import static java.lang.Math.*;

public class TabulatedFunction {

    private FunctionPoint[] values;
    private int pointsCount;

    public double getLeftDomainBorder(){
        return values[0].getX();
    }

    public double getRightDomainBorder(){
        return values[pointsCount-1].getX();
    }

    public double getFunctionValue(double x){
        if (getLeftDomainBorder() <= x && x <= getRightDomainBorder()){
            if (abs(values[0].getX() - x) < 0.000001) {
                return values[0].getY();
            }
            for (int i = 1; i < values.length; ++i){
                if (abs(values[i].getX() - x) < 0.000001) {
                    return values[i].getY();
                }
                if (values[i].getX() > x){
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

    public TabulatedFunction(double left, double right, int pointsCount){
        this.pointsCount = pointsCount;
        values = new FunctionPoint[pointsCount+5];
        double x = left;
        for (int i = 0; i < pointsCount; ++i){
            values[i] = new FunctionPoint(x,0);
            x+=(right-left)/pointsCount;
        }
    }

    public TabulatedFunction(double left, double right, double[] val){
        this.pointsCount = val.length;
        values = new FunctionPoint[pointsCount+5];
        double x = left;
        for (int i = 0; i < pointsCount; ++i){
            values[i] = new FunctionPoint(x,val[i]);
            x+=(right-left)/val.length;
        }
    }

    public double getPointX(int index){
        return values[index].getX();
    }

    public double getPointY(int index){
        return values[index].getY();
    }

    public FunctionPoint getPoint(int index){
        return new FunctionPoint(values[index]);
    }

    public void setPointY(int index, double y){
        values[index].setY(y);
    }

    public void setPointX(int index, double x){
        setPoint(index, new FunctionPoint(0, getPointY(index)));
    }

    public void setPoint(int index, FunctionPoint point){
        if ( (index == 0 && values[1].getX() > values[0].getX())
                || (index == (values.length - 1) && values[index].getX() > values[index - 1].getX())
        || (values[index].getX() > values[index-1].getX() && values[index+1].getX() > values[index].getX()))
        {
            values[index].setPoint(point);
        }
    }

    public void deletePoint(int index){
        System.arraycopy(values, index+1, values, index, pointsCount - index-1);
        --pointsCount;
    }

    public void addPoint(FunctionPoint point){
        int index = 0; //индекс для нашей точки
        while(index != pointsCount && point.getX() > values[index].getX()) {
            if (abs(values[index].getX()-point.getX())<0.0000001) {
                values[index] = point;//чтобы не случилось неопределенности при вычислении getFunctionValue (2;4) и (2;5)
                return;
            }
            ++index;
        }
        ++pointsCount;
        if (pointsCount == values.length){
            System.out.println("Массив увеличился!");
            FunctionPoint[] newValues = new FunctionPoint[pointsCount+5];
            System.arraycopy(values, 0, newValues, 0, index);
            newValues[index] = point;
            System.arraycopy(values, index, newValues, index+1, pointsCount-index);
            values = newValues;
        } else {
            System.out.println("Массив НЕ увеличился!");
            System.arraycopy(values, index, values, index+1, pointsCount-index);
            values[index] = point;
        }
    }
}
