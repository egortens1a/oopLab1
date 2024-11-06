package ru.ssau.tk.cucumber.oopLab2.functions;

import java.io.*;

public class TabulatedFunctions{
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount){
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()){
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
        return new LinkedListTabulatedFunction(values);
    }// получающий функцию и возвращающий её табулированный аналог на заданном отрезке с заданным количеством точек.

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) throws IOException {
        try (DataOutputStream dataOutput = new DataOutputStream(out);) {
            int countPoints = function.getPointsCount();
            dataOutput.writeInt(countPoints);
            for (int i = 0; i < countPoints; i++) {
                FunctionPoint point = function.getPoint(i);
                dataOutput.writeDouble(point.getX());
                dataOutput.writeDouble(point.getY());
            }
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException {
        try (DataInputStream dataInput = new DataInputStream(in);) {
            int countPoints = dataInput.readInt();
            FunctionPoint[] points = new FunctionPoint[countPoints];
            for (int i = 0; i < countPoints; i++) {
                points[i] = new FunctionPoint(dataInput.readDouble(), dataInput.readDouble());
            }
            return new LinkedListTabulatedFunction(points);
        }
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(out)) {
            writer.write(String.valueOf(function.getPointsCount()));
            for (int i = 0; i < function.getPointsCount(); i++) {
                FunctionPoint point = function.getPoint(i);
                writer.write(" " + point.getX() + " " + point.getY());
            }
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException {
        try (BufferedReader bufferedIn = new BufferedReader(in);) {
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

            return new LinkedListTabulatedFunction(values); // Возвращаем новый объект
        }
    }
}
