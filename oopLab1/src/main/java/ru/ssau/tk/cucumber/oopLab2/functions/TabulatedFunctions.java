package ru.ssau.tk.cucumber.oopLab2.functions;

import java.io.*;

public class TabulatedFunctions{

    private static TabulatedFunctionFactory tabulatedFunctionFactory = new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory tabulatedFunctionFactory){
        TabulatedFunctions.tabulatedFunctionFactory = tabulatedFunctionFactory;
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points){
        return tabulatedFunctionFactory.createTabulatedFunction(points);
    }
    public static TabulatedFunction createTabulatedFunction(double left, double right, int pointsCount){
        return tabulatedFunctionFactory.createTabulatedFunction(left, right, pointsCount);
    }
    public static TabulatedFunction createTabulatedFunction(double left, double right, double[] val){
        return tabulatedFunctionFactory.createTabulatedFunction(left, right, val);
    }

    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> classFunction, FunctionPoint[] points){
        try {
            return classFunction
                    .getDeclaredConstructor(FunctionPoint[].class)
                    .newInstance((Object) points);

        } catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }
    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> classFunction, double left, double right, int pointsCount){
        try {
            return classFunction
                    .getDeclaredConstructor(double.class, double.class, int.class)
                    .newInstance(left, right, pointsCount);

        } catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }
    public static TabulatedFunction createTabulatedFunction(Class<? extends TabulatedFunction> classFunction, double left, double right, double[] val){
        try {
            return classFunction
                    .getDeclaredConstructor(double.class, double.class, double[].class)
                    .newInstance(left, right, val);

        } catch (Exception e){
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount){
        if (Double.compare(leftX, function.getLeftDomainBorder()) < 0
                || Double.compare(rightX, function.getRightDomainBorder()) > 0){
            throw new IllegalArgumentException("Указанные границы для табулирования выходят за область определения функции");
        }
        FunctionPoint[] values = new FunctionPoint[pointsCount];
        double x = leftX;
        int i = 0;
        double step = (rightX-leftX)/(pointsCount-1);
        while (x <= rightX){
            values[i] = new FunctionPoint(x, function.getFunctionValue(x));
            i++;
            x+=step;
        }
        return tabulatedFunctionFactory.createTabulatedFunction(values);
    }
    public static TabulatedFunction tabulate(Class<? extends TabulatedFunction> classFunction, Function function, double leftX, double rightX, int pointsCount){
        if (Double.compare(leftX, function.getLeftDomainBorder()) < 0
                || Double.compare(rightX, function.getRightDomainBorder()) > 0){
            throw new IllegalArgumentException("Указанные границы для табулирования выходят за область определения функции");
        }
        FunctionPoint[] values = new FunctionPoint[pointsCount];
        double x = leftX;
        int i = 0;
        double step = (rightX-leftX)/(pointsCount-1);
        while (x <= rightX){
            values[i] = new FunctionPoint(x, function.getFunctionValue(x));
            i++;
            x+=step;
        }
        return TabulatedFunctions.createTabulatedFunction(classFunction, values);
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        try (DataOutputStream dataOutput = new DataOutputStream(out)) {
            int countPoints = function.getPointsCount();
            dataOutput.writeInt(countPoints);
            for (int i = 0; i < countPoints; i++) {
                FunctionPoint point = function.getPoint(i);
                dataOutput.writeDouble(point.getX());
                dataOutput.writeDouble(point.getY());
            }
        } catch (IOException e){
            System.out.println("Ошибка!");
            e.printStackTrace();
            throw e;
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        try (DataInputStream dataInput = new DataInputStream(in)) {
            int countPoints = dataInput.readInt();
            FunctionPoint[] points = new FunctionPoint[countPoints];
            for (int i = 0; i < countPoints; i++) {
                points[i] = new FunctionPoint(dataInput.readDouble(), dataInput.readDouble());
            }
            return tabulatedFunctionFactory.createTabulatedFunction(points);
        } catch (IOException e){
            System.out.println("Ошибка!");
            e.printStackTrace();
            throw e;
        }
    }
    public static TabulatedFunction inputTabulatedFunction(Class<? extends TabulatedFunction> classFunction,InputStream in) throws IOException {
        try (DataInputStream dataInput = new DataInputStream(in)) {
            int countPoints = dataInput.readInt();
            FunctionPoint[] points = new FunctionPoint[countPoints];
            for (int i = 0; i < countPoints; i++) {
                points[i] = new FunctionPoint(dataInput.readDouble(), dataInput.readDouble());
            }
            return TabulatedFunctions.createTabulatedFunction(classFunction, points);
        } catch (IOException e){
            System.out.println("Ошибка!");
            e.printStackTrace();
            throw e;
        }
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(out)) {
            writer.write(String.valueOf(function.getPointsCount()));
            for (int i = 0; i < function.getPointsCount(); i++) {
                FunctionPoint point = function.getPoint(i);
                writer.write(" " + point.getX() + " " + point.getY());
            }
        } catch (IOException e){
            System.out.println("Ошибка!");
            e.printStackTrace();
            throw e;
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        try (BufferedReader bufferedIn = new BufferedReader(in)) {
            StreamTokenizer tokenizer = new StreamTokenizer(bufferedIn);
            tokenizer.parseNumbers();
            tokenizer.nextToken();
            int pointsCount = (int) tokenizer.nval; // Читаем количество точек
            FunctionPoint[] values = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; i++) {
                tokenizer.nextToken();
                double x = tokenizer.nval;
                tokenizer.nextToken();
                double y = tokenizer.nval;
                FunctionPoint point = new FunctionPoint(x,y);
                values[i] = point;
            }
            return tabulatedFunctionFactory.createTabulatedFunction(values);
        } catch (IOException e){
            System.out.println("Ошибка!");
            e.printStackTrace();
            throw e;
        }
    }
    public static TabulatedFunction readTabulatedFunction(Class<? extends TabulatedFunction> classFunction, Reader in) throws IOException {
        try (BufferedReader bufferedIn = new BufferedReader(in)) {
            StreamTokenizer tokenizer = new StreamTokenizer(bufferedIn);
            tokenizer.parseNumbers();
            tokenizer.nextToken();
            int pointsCount = (int) tokenizer.nval; // Читаем количество точек
            FunctionPoint[] values = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; i++) {
                tokenizer.nextToken();
                double x = tokenizer.nval;
                tokenizer.nextToken();
                double y = tokenizer.nval;
                FunctionPoint point = new FunctionPoint(x,y);
                values[i] = point;
            }
            return TabulatedFunctions.createTabulatedFunction(classFunction, values);
        } catch (IOException e){
            System.out.println("Ошибка!");
            e.printStackTrace();
            throw e;
        }
    }
}
