package ru.ssau.tk.cucumber.oopLab2;

import ru.ssau.tk.cucumber.oopLab2.functions.*;
import ru.ssau.tk.cucumber.oopLab2.functions.basic.*;

import java.io.*;

public class Main {
    public static void main(String[] args) throws InappropriateFunctionPointException, IOException, ClassNotFoundException {
        //lab3();
        lab4();
    }

    private static void lab4() throws IOException, ClassNotFoundException {

        System.out.println("\n\nLLL_4_ЛАБА__");

        System.out.println("Создайте по одному объекту классов Sin и Cos, " +
                "выведите в консоль значения этих функций на отрезке от 0 до  с шагом 0,1");

        Sin funcSin = new Sin();
        Cos funcCos = new Cos();
        System.out.println("\n SIN ||| COS\n");
        printFunctionWithStep(funcSin, funcCos, 0, 2*Math.PI, 0.1);



        System.out.println("С помощью метода TabulatedFunctions.tabulate() создайте табулированные аналоги этих функций на отрезке от 0 до 2pi с 10 точками. " +
                "Выведите в консоль значения этих функций на отрезке от 0 до 2pi с шагом 0,1 и сравните со значениями исходных функций.");

        TabulatedFunction tabulatedSin = TabulatedFunctions.tabulate(funcSin,0, 2*Math.PI,10);
        TabulatedFunction tabulatedCos = TabulatedFunctions.tabulate(funcCos,0, 2*Math.PI,10);

        System.out.println("\nРазница между табулированной и не табулированной функциями func-tabFunc");
        System.out.println("\nSIN ||| Tabulated SIN\n");
        printFunctionWithStep(funcSin, tabulatedSin, 0, 2*Math.PI, 0.1);
        System.out.println("\nCOS ||| Tabulated COS\n");
        printFunctionWithStep(funcCos, tabulatedCos, 0, 2*Math.PI, 0.1);



        System.out.println("С помощью методов класса Functions создайте объект функции, " +
                "являющейся суммой квадратов табулированных аналогов синуса и косинуса");

        Function funcCos2Sin2 = Functions.sum(Functions.power(tabulatedSin, 2), Functions.power(tabulatedCos, 2));
        printFunctionWithStep(funcCos2Sin2, 0, 2*Math.PI, 0.1);

        System.out.println("Табулиованная версия cos**2 + sin**2:");
        TabulatedFunction tabulatedFuncCos2Sin2 = TabulatedFunctions.tabulate(funcCos2Sin2,0, 2*Math.PI,5);
        printFunctionWithStep(tabulatedFuncCos2Sin2, 0, 2*Math.PI, 0.1);



        System.out.println("\nС помощью метода TabulatedFunctions.tabulate()" +
                " создайте табулированный аналог экспоненты на отрезке от 0 до 10 с 11 точками.");

        TabulatedFunction tabulatedExp = TabulatedFunctions.tabulate(new Exp(),0,10,11);
        //С помощью метода TabulatedFunctions.writeTabulatedFunction() выведите его в файл
        TabulatedFunctions.writeTabulatedFunction(tabulatedExp, new FileWriter("test_files/lab4_writer.txt"));
        TabulatedFunction readTabExp = TabulatedFunctions.readTabulatedFunction(new FileReader("test_files/lab4_writer.txt"));
        printFunctionWithStep(tabulatedExp, readTabExp, 0,10,1);



        System.out.println("С помощью метода TabulatedFunctions.tabulate() создайте табулированный аналог " +
                "логарифма по натуральному основанию на отрезке от 0 до 10 с 11 точками.");

        TabulatedFunction tabulatedLog = TabulatedFunctions.tabulate(new Log(),0,10,11);
        TabulatedFunctions.outputTabulatedFunction(tabulatedLog, new FileOutputStream("test_files/lab4_stream.txt"));
        TabulatedFunction readTabLog = TabulatedFunctions.inputTabulatedFunction(new FileInputStream("test_files/lab4_stream.txt"));
        printFunctionWithStep(tabulatedLog,readTabLog, 0,10,1);



        System.out.println("Сериализация! (Исходный ||| Сериализованный)");

        OutputStream outStream = new FileOutputStream("test_files/lab4_serialize.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
        objectOutputStream.writeObject(tabulatedLog);
        outStream.flush();

        InputStream inStream = new FileInputStream("test_files/lab4_serialize.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(inStream);
        TabulatedFunction resultFunc = (TabulatedFunction) objectInputStream.readObject();
        printFunctionWithStep(tabulatedLog, resultFunc, 0,10,1);
    }
    private static void lab3() throws InappropriateFunctionPointException {

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
        printFunctionWithStep(func1, start, end, step);

        printBordersAndPointsCount(func1);

        System.out.println("\nLLL__Другой_конструктор__");
        TabulatedFunction func2 = new LinkedListTabulatedFunction(start, end, 5);
        int i = 0;
        for(double x = 0; i < func2.getPointsCount(); x+=2.5){
            func2.setPointY(i++, x*x);
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

        System.out.println("\nLLL__Удаление__точки__(0,0)_index=2__");
        func2.deletePoint(2);

        print(func2);
        printBordersAndPointsCount(func2);

        checkErrors(func1);
    }

    private static void printFunctionWithStep(Function func, double currValue, double end, double step){
        System.out.println("x  |  y");

        while (currValue <= end){
            System.out.println(currValue + " | " + func.getFunctionValue(currValue));
            currValue+=step;
        }

        System.out.println("\n");
    }

    private static void printFunctionWithStep(Function func1, Function func2, double currValue, double end, double step){
        System.out.println("x  |  y1  |  y2");

        while (currValue <= end){
            System.out.println(currValue + " | " + func1.getFunctionValue(currValue)
            + " ||| " + func2.getFunctionValue(currValue));
            currValue+=step;
        }

        System.out.println("\n");
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
            func.setPointX(2, func.getPointX(4));
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