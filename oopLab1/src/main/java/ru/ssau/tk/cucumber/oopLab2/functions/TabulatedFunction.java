package functions;

public interface TabulatedFunction {
    double getLeftDomainBorder();
    double getRightDomainBorder();
    double getFunctionValue(double x);
    int getPointsCount();
    double getPointX(int index);
    double getPointY(int index);
    FunctionPoint getPoint(int index);
    void setPointY(int index, double y);
    void setPointX(int index, double x) throws InappropriateFunctionPointException;
    void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException;
    void deletePoint(int index);
    void addPoint(FunctionPoint point) throws InappropriateFunctionPointException;
}
