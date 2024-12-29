package ru.ssau.tk.cucumber.oopLab2.functions;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.lang.Math.abs;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable, Cloneable, Iterable<FunctionPoint> {

    @Serial
    private static final long serialVersionUID = 2964740730160414085L;
    private int pointsCount;
    private FunctionNode head;
    private FunctionNode lastUsedNode;
    private int lastUsedIndex;

    public double getLeftDomainBorder(){
        return this.head.point.getX();
    }

    public double getRightDomainBorder(){
        return this.getNodeByIndex(pointsCount-1).point.getX();
    }

    public double getFunctionValue(double x) {
        if (Double.compare(getLeftDomainBorder(), x) <= 0 && Double.compare(x, getRightDomainBorder()) <= 0) {//double compare
            FunctionNode curr = head.next;
            while (curr != null) {
                if (Double.compare(curr.point.getX(),x)>=0) {
                    double x1 = curr.prev.point.getX();
                    double x2 = curr.point.getX();
                    double y1 = curr.prev.point.getY();
                    double y2 = curr.point.getY();
                    /*
                    (x-x1)/(x2-x1) = (y-y1)/(y2-y1)
                            <==>
                    y = (x-x1)/(x2-x1)*(y2-y1)+y1
                    */
                    return (x - x1) / (x2 - x1) * (y2 - y1) + y1;
                }
                curr = curr.next;
            }
        }
        return Double.NaN;
    }

    public int getPointsCount() {
        return pointsCount;
    }

    public LinkedListTabulatedFunction(TabulatedFunction anotherFunc){//Копируем другую функцию
        this.pointsCount = anotherFunc.getPointsCount();
        this.head = new FunctionNode(anotherFunc.getPoint(0));

        FunctionNode lastNode = this.head;

        for (int i = 1; i < pointsCount; i++){
            lastNode.next = new FunctionNode(anotherFunc.getPoint(i));
            lastNode.next.prev=lastNode;
            lastNode = lastNode.next;
        }
    }

    public LinkedListTabulatedFunction(FunctionPoint[] points) throws IllegalArgumentException{
        this.pointsCount = points.length;
        if (this.pointsCount < 2){
            throw new IllegalArgumentException("Мало точек");
        }
        this.head = new FunctionNode(points[0]);
        FunctionNode lastNode = this.head;

        for (int i = 1; i<pointsCount; i++){
            if (Double.compare(points[i - 1].getX(), points[i].getX()) >= 0){
                throw new IllegalArgumentException("точки Х не по порядку");
            }
            lastNode.next = new FunctionNode(points[i]);
            lastNode.next.prev=lastNode;
            lastNode = lastNode.next;
        }
    }

    public LinkedListTabulatedFunction(double left, double right, int pointsCount) throws IllegalArgumentException, IllegalStateException{
        if (Double.compare(left, right) >= 0){
            throw new IllegalArgumentException("LeftDomainBorder > RightDomainBorder");
        }
        if (pointsCount < 3) {
            throw new IllegalStateException("Слишком мало точек");
        }
        this.pointsCount = pointsCount;
        double x = left;
        this.head = new FunctionNode(new FunctionPoint(x,0));
        x+=(right-left)/(pointsCount-1);

        FunctionNode lastNode = this.head;
        for (int i = 1; i < pointsCount; i++){
            lastNode.next = new FunctionNode(new FunctionPoint(x,0));
            lastNode.next.prev=lastNode;
            lastNode = lastNode.next;

            x+=(right-left)/(pointsCount-1);
        }
    }

    public LinkedListTabulatedFunction(double left, double right, double[] val) throws IllegalStateException, IllegalArgumentException {
        if (Double.compare(left, right) >= 0){
            throw new IllegalArgumentException("LeftDomainBorder > RightDomainBorder");
        }
        if (val.length < 3) {
            throw new IllegalStateException("Слишком мало точек");
        }
        this.pointsCount = val.length;
        double x = left;
        this.head = new FunctionNode(new FunctionPoint(x, val[0]));
        x+=(right-left)/pointsCount;

        FunctionNode lastNode = this.head;
        for (int i = 1; i < pointsCount; ++i){
            lastNode.next = new FunctionNode(new FunctionPoint(x, val[i]));
            lastNode.next.prev=lastNode;
            lastNode = lastNode.next;

            x+=(right-left)/pointsCount;
        }
    }

    public double getPointX(int index) throws FunctionPointIndexOutOfBoundsException{
        FunctionNode curr = this.head;
        for (int i = 0; i < index; i++){
            curr = curr.next;
        }
        return getNodeByIndex(index).point.getX();
    }

    public double getPointY(int index) throws FunctionPointIndexOutOfBoundsException{
        return getNodeByIndex(index).point.getY();
    }

    public FunctionPoint getPoint(int index) throws FunctionPointIndexOutOfBoundsException{
        return getNodeByIndex(index).point;
    }

    public void setPointY(int index, double y) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index > pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        getNodeByIndex(index).point.setY(y);
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        if ((index == 0 && !(Double.compare(x, getPointX(index+1)) == -1))
                ||
                (index == pointsCount-1 && !(Double.compare(x, getPointX(index-1)) == 1))
                ||
                index != 0 && index != pointsCount-1 &&!(Double.compare(x, getPointX(index-1)) == 1
                        && Double.compare(x, getPointX(index+1)) == -1)){
            throw new InappropriateFunctionPointException("x не попадает в промежуток (x[i-1],x[i+1]): ("+getPointX(index-1)+","+getPointX(index+1)+")");
        }
        getNodeByIndex(index).point.setX(x);
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException, FunctionPointIndexOutOfBoundsException {
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        if (!(Double.compare(point.getX(), getPointX(index-1)) >= 0 && Double.compare(point.getX(), getPointX(index+1)) <= 0)){
            throw new InappropriateFunctionPointException("x не попадает в промежуток (x[i-1],x[i+1]): ("+getPointX(index-1)+","+getPointX(index+1)+")");
        }
        FunctionNode curr = getNodeByIndex(index);
        if (Double.compare(curr.prev.point.getX(), point.getX()) == -1 && Double.compare(point.getX(), curr.next.point.getX()) == -1){
            curr.prev.point.setPoint(point);
        }
    }

    public void addPoint(FunctionPoint p) throws InappropriateFunctionPointException {
        FunctionNode curr = this.head;
        while(curr.next != null && Double.compare(p.getX(), curr.point.getX()) >= 0) {
            if (Double.compare(curr.point.getX(), p.getX()) == 0) {
                throw new InappropriateFunctionPointException("Такой X уже существует!");
            }
            curr = curr.next;
        }
        ++pointsCount;
        if (Double.compare(p.getX(), this.head.point.getX()) == -1){
            FunctionNode newHead = new FunctionNode(p);
            newHead.next = this.head;
            this.head = newHead;
            this.head.next.prev = this.head;
        } else if (Double.compare(p.getX(), curr.point.getX()) == -1){
            FunctionNode newNode = new FunctionNode(p);
            curr.prev.next = newNode;
            newNode.prev = curr.prev;
            curr.prev = newNode;
            newNode.next = curr;
        } else {
            FunctionNode newNode = new FunctionNode(p);
            curr.next = newNode;
            newNode.prev = curr;
        }
    }

    public void deletePoint(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException{
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        if (pointsCount < 3){
            throw new IllegalStateException("Слишком мало точек");
        }
         deleteNodeByIndex(index);
    }

    public String toString(){
        String[] points = new String[pointsCount];

        int i = 0;
        FunctionNode curr = head;
        while(curr != null){
            points[i] = curr.point.toString();
            i++;
            curr = curr.next;
        }

        return "{" + String.join(", ", points) + "}";
    }

    public boolean equals(Object o){
        if (!(o instanceof TabulatedFunction))
            return false;
        if (pointsCount != ((TabulatedFunction) o).getPointsCount())
            return false;
        if (o instanceof LinkedListTabulatedFunction){
            LinkedListTabulatedFunction function_o = (LinkedListTabulatedFunction) o;
            FunctionNode curr = head;
            FunctionNode curr_o = function_o.head;
            while (curr != null){
                if (!(curr.point.equals(curr_o.point))){
                    return false;
                }
                curr = curr.next;
                curr_o = curr_o.next;
            }
            return true;
        }

        TabulatedFunction function_o = (TabulatedFunction) o;
        FunctionNode curr = head;
        for (int i = 0; i<pointsCount; i++){
            if ( !(curr.point.equals(function_o.getPoint(i))) ){
                return false;
            }
            curr = curr.next;
        }
        return true;
    }

    public int hashCode(){
        return Objects.hash(pointsCount, this.toString());
    }

    public Object clone(){
        FunctionPoint[] cloned_points = new FunctionPoint[pointsCount];
        FunctionNode curr = head;
        for (int i = 0; i < pointsCount; i++) {
            try {
                cloned_points[i] = (FunctionPoint) curr.point.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            curr = curr.next;
        }
        return new LinkedListTabulatedFunction(cloned_points);
    }

    @Override
    public Iterator<FunctionPoint> iterator() {
        return new Iterator<FunctionPoint>() {
            private FunctionNode node = head;

            @Override
            public boolean hasNext() {
                return node != null;
            }

            @Override
            public FunctionPoint next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                FunctionPoint point = new FunctionPoint(node.point.getX(), node.point.getY());
                if (node.next == head) {
                    node = null;
                } else {
                    node = node.next;
                }
                return point;
            }
        };
    }

    private static class FunctionNode implements Serializable{
        @Serial
        private static final long serialVersionUID = 7368238319685449514L;
        private final FunctionPoint point;
        private FunctionNode next;
        private FunctionNode prev;

        FunctionNode(FunctionPoint point){
            this.point = point;
        }
    }

    private FunctionNode getNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }

        FunctionNode curr;
        int i = 0;

        if (abs(index-lastUsedIndex) < index){//если путь через lastUsedNode будет ближе, то пользуемся им, иначе идем с головы
            curr = lastUsedNode;
            if (index > lastUsedIndex){
                for (i = lastUsedIndex; i<index; ++i){
                    curr = curr.next;
                }
            } else {
                for (i = lastUsedIndex; i > index; --i){
                    curr = curr.prev;
                }
            }
        } else {
            curr = this.head;
            for (; i<index; ++i){
                curr = curr.next;
            }
        }
        lastUsedIndex = i;
        lastUsedNode = curr;
        return curr;
    }

    private FunctionNode addNodeToTail(FunctionPoint point){
        FunctionNode lastNode = getNodeByIndex(pointsCount-1);
        FunctionNode newNode = new FunctionNode(point);
        lastNode.next = newNode;
        newNode.prev = lastNode;
        pointsCount++;
        return newNode;
    }

    private FunctionNode addNodeByIndex(int index, FunctionPoint point)throws FunctionPointIndexOutOfBoundsException{
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        FunctionNode curr = getNodeByIndex(index);
        FunctionNode newNode = new FunctionNode(point);
        curr.prev.next = newNode;
        newNode.prev = curr.prev;
        curr.prev = newNode;
        newNode.next = curr;
        return newNode;
    }

    private FunctionNode deleteNodeByIndex(int index) throws FunctionPointIndexOutOfBoundsException, IllegalStateException{
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        if (pointsCount < 3) {
            throw new IllegalStateException("Слишком мало точек");
        }
        --pointsCount;
        FunctionNode curr = getNodeByIndex(index);
        if (curr == head){
            head = head.next;
            head.prev = null;

            return curr;
        }
        curr.prev.next = curr.next;
        curr.next.prev = curr.prev;
        return curr;
    }

    public static class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory{

        @Override
        public TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
            return new LinkedListTabulatedFunction(points);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double left, double right, int pointsCount) {
            return new LinkedListTabulatedFunction(left, right, pointsCount);
        }

        @Override
        public TabulatedFunction createTabulatedFunction(double left, double right, double[] val) {
            return new LinkedListTabulatedFunction(left, right, val);
        }
    }
}