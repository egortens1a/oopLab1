import functions.*;

public class Main {
    public static void main(String[] args) throws InappropriateFunctionPointException {
        double start = 0;
        double end = 10;
        double[] valuesY = new double[10];

        for (int i = 0; i < 10; ++i){
            double x = start + (end - start) / 10*i;
            valuesY[i] = x * x;
        }
        TabulatedFunction func1 = new LinkedListTabulatedFunction(start, end, valuesY);
        print(func1);

        double step = 0.25;
        printWithGetFunctionValue(start, end, step, func1);

        printBordersAndPointsCount(func1);

        System.out.println("\nLLL__Другой_конструктор__");
        TabulatedFunction func2 = new LinkedListTabulatedFunction(start, end, 5);

        for(int x = 0; x < func2.getPointsCount(); ++x){
            func2.setPointY(x, 4*(x*x));
        }

        print(func2);
        printBordersAndPointsCount(func2);
        func2.addPoint(new FunctionPoint(10000, 99999));
        func2.addPoint(new FunctionPoint(4.5, 99999));
        func2.addPoint(new FunctionPoint(-1, 99999));
        func2.addPoint(new FunctionPoint(-2, 99999));
        func2.addPoint(new FunctionPoint(100, 99999));
        func2.addPoint(new FunctionPoint(111, 99999));
        System.out.println("\n\nLLL__Обновленный_набор_точек__");
        print(func2);

        printBordersAndPointsCount(func2);

        System.out.println("\nLLL__Удаление__точки__(0;0)_index=2__");
        func2.deletePoint(2);

        print(func2);
        printBordersAndPointsCount(func2);

        checkErrors(func1);
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
        System.out.println("\nLLL__Вычисление__точек__с__помощью__getFunctionValue__");
        System.out.println("x  |  y");
        double x = start-1;
        for(; x < func.getRightDomainBorder(); x+=0.25){
            System.out.println(x + " | " + func.getFunctionValue(x));
            x+=step;
            System.out.println(x + " | " + func.getFunctionValue(x));
            x+=step;
            System.out.println(x + " | " + func.getFunctionValue(x));
            x+=step;
            System.out.println(x + " | " + func.getFunctionValue(x));
        }
        System.out.println(x + " | " + func.getFunctionValue(x));
        x+=step;
        System.out.println(x + " | " + func.getFunctionValue(x));
    }

    
    private static void checkErrors(TabulatedFunction func){
        System.out.println("\nLLL__Обработка_ошибок__ \n");
        try{
            FunctionPoint a = func.getPoint(10000);
        }
        catch(FunctionPointIndexOutOfBoundsException e){
            System.out.println(e);
        }

        try{
            TabulatedFunction f = new LinkedListTabulatedFunction(10,-10,5);
        }
        catch(IllegalArgumentException e){
            System.out.println(e);
        }

        try{
            func.setPointX(2, func.getPointX(3));
        }
        catch(InappropriateFunctionPointException e){
            System.out.println(e);
        }

        try{
            func.addPoint(new FunctionPoint(func.getPoint(2)));
        }
        catch(InappropriateFunctionPointException e){
            System.out.println(e);
        }

        try{
            while(func.getPointsCount() != 0){
                func.deletePoint(0);
            }
        }
        catch(IllegalStateException e){
            System.out.println(e);
        }
    }
}