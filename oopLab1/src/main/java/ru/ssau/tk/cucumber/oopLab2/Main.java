import functions.FunctionPoint;
import functions.TabulatedFunction;


public class Main {
    public static void main(String[] args) {
        double start = 0;
        double end = 10;
        double[] valuesY = new double[10];

        for (int i = 0; i < 10; ++i){
            double x = start + (end - start) / 10*i;
            valuesY[i] = x * x;
        }
        TabulatedFunction func1 = new TabulatedFunction(start, end, valuesY);
        print(func1);

        System.out.println("LLL__Вычисление__точек__с__помощью__getFunctionValue__|||");

        double step = 0.25;
        printWithGetFunctionValue(start, end, step, func1);

        System.out.println("Левая граница: " + func1.getLeftDomainBorder());
        System.out.println("Правая граница: " + func1.getRightDomainBorder());

        System.out.println("\nLLL__Добавление_____точек__|||");
        TabulatedFunction func2 = new TabulatedFunction(start, end, 5);

        for(int x = 0; x < func2.getPointsCount(); ++x){
            func2.getPoint(x).setY((double) x/2);
        }

        print(func2);

        func2.addPoint(new FunctionPoint(100,100));
        func2.addPoint(new FunctionPoint(4.5, 10000));
        func2.addPoint(new FunctionPoint(-1, -100));
        System.out.println("Обновленный набор точек: ");
        print(func2);

        System.out.println("Левая граница: " + func2.getLeftDomainBorder());
        System.out.println("Правая граница: " + func2.getRightDomainBorder());

        System.out.println("\nLLL__Удаление______точки__|||");
        func2.deletePoint(2);
        print(func2);

        System.out.println("____________");

    }

    public static void print(TabulatedFunction func) {
        System.out.println("x  |  y");
        for(int i = 0; i < func.getPointsCount(); ++i){
            System.out.println(func.getPointX(i) + " | " + func.getPointY(i));
        }
    }
    private static void printWithGetFunctionValue(double start, double end, double step, TabulatedFunction func){
        double x = start-1;
        System.out.println("x  |  y");
        System.out.println(x + " | " + func.getFunctionValue(x));

        for(int i = 0; i < func.getPointsCount(); ++i){
            x = func.getPointX(i);
            System.out.println(x + " | " + func.getFunctionValue(x));
            x+=step;
            System.out.println(x + " | " + func.getFunctionValue(x));
            x+=step;
            System.out.println(x + " | " + func.getFunctionValue(x));
        }

        x = end+1;
        System.out.println(x + " | " + func.getFunctionValue(x));

    }
}