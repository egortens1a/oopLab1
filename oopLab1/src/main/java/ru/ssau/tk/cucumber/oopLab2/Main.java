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

        System.out.println("\nLLL__Вычисление__точек__с__помощью__getFunctionValue__|||");

        double step = 0.25;
        printWithGetFunctionValue(start, end, step, func1);

        printBordersAndPointsCount(func1);

        System.out.println("\nLLL__Добавление_____точек__|||");
        TabulatedFunction func2 = new TabulatedFunction(start, end, 5);

        for(int x = 0; x < func2.getPointsCount(); ++x){
            func2.setPointY(x, 4*(x*x));
        }

        print(func2);
        printBordersAndPointsCount(func2);
        System.out.println("\nДобавление точек");
        func2.addPoint(new FunctionPoint(10000, 99999));
        func2.addPoint(new FunctionPoint(4.5, 99999));
        func2.addPoint(new FunctionPoint(-1, 99999));
        func2.addPoint(new FunctionPoint(-2, 99999));
        func2.addPoint(new FunctionPoint(100, 99999));
        func2.addPoint(new FunctionPoint(111, 99999));
        System.out.println("\nОбновленный набор точек: ");
        print(func2);

        printBordersAndPointsCount(func2);

        System.out.println("\nLLL__Удаление______точки__|||");
        func2.deletePoint(2);
        print(func2);
        printBordersAndPointsCount(func2);
    }

    private static void printBordersAndPointsCount(TabulatedFunction func){
        System.out.println("Левая граница: " + func.getLeftDomainBorder());
        System.out.println("Правая граница: " + func.getRightDomainBorder());
        System.out.println("Количество точек: " + func.getPointsCount());
    }
    private static void print(TabulatedFunction func) {
        System.out.println("x  |  y");
        for(int i = 0; i < func.getPointsCount(); ++i){
            System.out.println(func.getPointX(i) + " | " + func.getPointY(i));
        }
    }
    private static void printWithGetFunctionValue(double start, double end, double step, TabulatedFunction func){
        System.out.println("x  |  y");

        for(double x = start-1; x < func.getRightDomainBorder(); x+=0.25){
            System.out.println(x + " | " + func.getFunctionValue(x));
            x+=step;
            System.out.println(x + " | " + func.getFunctionValue(x));
            x+=step;
            System.out.println(x + " | " + func.getFunctionValue(x));
            x+=step;
            System.out.println(x + " | " + func.getFunctionValue(x));
        }
    }
}