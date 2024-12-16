package ru.ssau.tk.cucumber.oopLab2;

import ru.ssau.tk.cucumber.oopLab2.functions.*;
import ru.ssau.tk.cucumber.oopLab2.functions.basic.*;
import ru.ssau.tk.cucumber.oopLab2.threads.*;

import java.util.Random;

import java.io.*;
import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException,InappropriateFunctionPointException {
        // lab3();
        // lab4();
        // lab5();
        //lab6();

    }
    private static void complicatedThreads(){
        int taskCount = 100; // Минимум 100 заданий
        Task task = new Task(null, 0, 0, 0, taskCount);
        Semaphore semaphore = new Semaphore(1, true);
        Thread generatorThread = new Thread(new Generator(task, semaphore));
        generatorThread.start();
        Thread integratorThread = new Thread(new Integrator(task, semaphore));
        integratorThread.start();

        long start = System.currentTimeMillis() + 50;
        while(start >= System.currentTimeMillis());
        generatorThread.interrupt();
        integratorThread.interrupt();
    }
    private static void nonThread(){
        Random random = new Random(); // random.nextInt(max - min + 1) + min;
        int taskCount = random.nextInt(51) + 100;; // Минимум 100 заданий
        Task task = new Task(new Log(random.nextDouble() * 9 + 1),
                random.nextDouble() * 100,
                100 + random.nextDouble() * 100,
                random.nextDouble(),
                taskCount);
        while (task.getTaskCount() > 0){
            task.update(new Log(random.nextDouble() * 9 + 1),
                    random.nextDouble() * 100,
                    100 + random.nextDouble() * 100,
                    random.nextDouble());
            System.out.printf("Source %.2f %.2f %.2f%n",
                    task.getLeftDomainBorder(),
                    task.getRightDomainBorder(),
                    task.getStep());
            System.out.printf("Result %.2f %.2f %.2f %.4f%n \n",
                    task.getLeftDomainBorder(),
                    task.getRightDomainBorder(),
                    task.getStep(),
                    Functions.integral(task.getFunction(),
                            task.getLeftDomainBorder(),
                            task.getRightDomainBorder(),
                            task.getStep()));
            task.setTaskCount(task.getTaskCount() - 1);
        }

    }
    private static void simpleThreads() {
        int taskCount = 100; // Минимум 100 заданий
        Task task = new Task(null, 0, 0, 0, taskCount); // Создание объекта задания

        // Создание и запуск потоков
        Thread generatorThread = new Thread(new SimpleGenerator(task));
        generatorThread.start();
        Thread integratorThread = new Thread(new SimpleIntegrator(task));
        integratorThread.start();
    }
    private static void lab6(){
        System.out.println("\n\nLLL_6_ЛАБА__");
        System.out.println("\nЗадание 1. Интеграл");
        System.out.println(Math.E - 1);
        System.out.println(Functions.integral(new Exp(),0,1, 0.001));
        System.out.println("\nЗадание 2. nonThread");
        nonThread();
        System.out.println("\nЗадание 3. simpleThreads");
        simpleThreads();

        System.out.println("\nЗадание 4. complicatedThreads");
        complicatedThreads();
    }
    private static void compareFunctions_lab5(Object obj1, Object obj2){
        System.out.println(obj1.getClass().toString() +'\n' + obj1
                +'\n'+obj2.getClass()+'\n'+obj2);
        System.out.println(obj1.equals(obj2));
        System.out.println("HashCodes");
        System.out.println(obj1.hashCode());
        System.out.println(obj2.hashCode());
    }
    private static void lab5() throws InappropriateFunctionPointException {
        System.out.println("\n\nLLL_5_ЛАБА__");
        System.out.println("\n\nПроверьте работу метода toString() для объектов типов ArrayTabulatedFunction\n" +
                " и LinkedListTabulatedFunction, выведя строковое представление объектов в консоль. log(exp(x))\n");
        TabulatedFunction function1 = TabulatedFunctions.tabulate(
                Functions.composition(new Log(), new Exp()),0,10,11);
        System.out.println(function1);


        System.out.println("\n\nПроверьте работу метода equals(), " +
                "вызывая его для одинаковых и различающихся объектов одинаковых и различающихся классов.");

        System.out.println("\n1) Две табулированных функции, которые совпадают");
        TabulatedFunction function2 = TabulatedFunctions.tabulate(
                Functions.composition(new Exp(), new Log()),0,10,11);
        compareFunctions_lab5(function1,function2);

        System.out.println("\n2) Две табулированных функции которые не совпадают");
        TabulatedFunction function3 = TabulatedFunctions.tabulate(new Cos(),0, 10, 6);
        compareFunctions_lab5(function3,function2);

        System.out.println("\n3) Разные объекты");
        FunctionPoint point1 = new FunctionPoint(0,1);
        compareFunctions_lab5(point1,function2);

        System.out.println("\n4) Array & List");
        TabulatedFunction arrTabFunc = new ArrayTabulatedFunction(
                0,10, new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        compareFunctions_lab5(arrTabFunc, function1);

        System.out.println("\n5) Также попробуйте незначительно" +
                " изменить один из объектов " +
                "(например, изменить одну из координат одной из точек на несколько тысячных)");
        function1.setPointX(0,0.000005);
        compareFunctions_lab5(function1, function2);



        System.out.println("\n\nКлонирование\n");

        System.out.println("array");
        ArrayTabulatedFunction cloned_arrTabFunc = (ArrayTabulatedFunction) arrTabFunc.clone();

        System.out.println(arrTabFunc);
        System.out.println(cloned_arrTabFunc);

        cloned_arrTabFunc.setPointY(0,10000);
        System.out.println("*Изменили клона*");

        System.out.println(arrTabFunc);
        System.out.println(cloned_arrTabFunc);


        System.out.println("\nlist");
        LinkedListTabulatedFunction cloned_listFunc = (LinkedListTabulatedFunction) function1.clone();

        System.out.println(function1);
        System.out.println(cloned_listFunc);

        cloned_listFunc.setPointY(0,10000);
        System.out.println("*Изменили клона*");

        System.out.println(function1);
        System.out.println(cloned_listFunc);
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
        TabulatedFunction tabulatedLogExp = TabulatedFunctions.tabulate(
                Functions.composition(new Log(), new Exp()),0,10,11
        );
        OutputStream outStream = new FileOutputStream("test_files/lab4_serialize.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);
        objectOutputStream.writeObject(tabulatedLogExp);
        outStream.flush();

        InputStream inStream = new FileInputStream("test_files/lab4_serialize.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(inStream);
        TabulatedFunction resultFunc = (TabulatedFunction) objectInputStream.readObject();
        printFunctionWithStep(tabulatedLogExp, resultFunc, 0,10,1);
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

        while (Double.compare(currValue, end) <= 0){
            System.out.println(currValue + " | " + func.getFunctionValue(currValue));
            currValue+=step;
        }

        System.out.println("\n");
    }
    private static void printFunctionWithStep(Function func1, Function func2, double currValue, double end, double step){
        System.out.println("x  |  y1  |  y2");

        while (Double.compare(currValue, end) <= 0){
            System.out.println(currValue
                    + " | " + func1.getFunctionValue(currValue)
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