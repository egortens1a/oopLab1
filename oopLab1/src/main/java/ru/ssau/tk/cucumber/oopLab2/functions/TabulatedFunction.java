package ru.ssau.tk.cucumber.oopLab2.functions;

public interface TabulatedFunction extends Function, Cloneable {
    int getPointsCount();

    double getPointX(int index)
            throws FunctionPointIndexOutOfBoundsException;

    double getPointY(int index)
            throws FunctionPointIndexOutOfBoundsException;

    FunctionPoint getPoint(int index)
            throws FunctionPointIndexOutOfBoundsException;

    void setPointY(int index, double y)
            throws FunctionPointIndexOutOfBoundsException;

    void setPointX(int index, double x)
            throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    void setPoint(int index, FunctionPoint point)
            throws FunctionPointIndexOutOfBoundsException, InappropriateFunctionPointException;

    void deletePoint(int index)
            throws FunctionPointIndexOutOfBoundsException, IllegalStateException;

    void addPoint(FunctionPoint point)
            throws InappropriateFunctionPointException;
    Object clone();
}
