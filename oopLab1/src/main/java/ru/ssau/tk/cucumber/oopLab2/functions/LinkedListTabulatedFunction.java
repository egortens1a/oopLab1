package functions;

import static java.lang.Math.abs;

public class LinkedListTabulatedFunction implements TabulatedFunction{
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

    public LinkedListTabulatedFunction(double left, double right, int pointsCount){
        if (Double.compare(left, right) == 1){
            throw new IllegalArgumentException("LeftDomainBorder > RightDomainBorder");
        }
        this.pointsCount = pointsCount;
        double x = left;
        this.head = new FunctionNode(new FunctionPoint(x,0));
        x+=(right-left)/pointsCount;

        FunctionNode lastNode = this.head;
        for (int i = 1; i < pointsCount; i++){
            lastNode.next = new FunctionNode(new FunctionPoint(x,0));
            lastNode.next.prev=lastNode;
            lastNode = lastNode.next;

            x+=(right-left)/pointsCount;
        }
    }
    public LinkedListTabulatedFunction(double left, double right, double[] val){
        if (Double.compare(left, right) == 1){
            throw new IllegalArgumentException("LeftDomainBorder > RightDomainBorder");
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
    public double getPointX(int index){
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        FunctionNode curr = this.head;
        for (int i = 0; i < index; i++){
            curr = curr.next;
        }
        return getNodeByIndex(index).point.getX();
    }
    public double getPointY(int index){
        if (index < 0 || index > pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        return getNodeByIndex(index).point.getY();
    }

    public FunctionPoint getPoint(int index){
        if (index < 0 || index > pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }

        return getNodeByIndex(index).point;
    }
    public void setPointY(int index, double y){
        if (index < 0 || index > pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        getNodeByIndex(index).point.setY(y);
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        if (!(Double.compare(x, getPointX(index-1)) == 1 && Double.compare(x, getPointX(index+1)) == -1)){
            throw new InappropriateFunctionPointException("x не попадает в промежуток (x[i-1],x[i+1]): ("+getPointX(index-1)+","+getPointX(index+1)+")");
        }
        FunctionNode curr = getNodeByIndex(index);
        if (Double.compare(curr.prev.point.getX(), x) == -1 && Double.compare(x, curr.next.point.getX()) == -1){
            curr.prev.point.setX(x);
        }
    }
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
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
        while(curr.next != null && Double.compare(p.getX(),curr.point.getX())>=0) {//пока не приходим в последний элемент
            if (Double.compare(curr.point.getX(),p.getX()) == 0) { //ИЛИ не нашли такой х, который будет меньше нашего point.X
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

    public void deletePoint(int index){
        if (index < 0 || index >= pointsCount){
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        if (pointsCount < 3){
            throw new IllegalStateException("Слишком мало точек");
        }
         deleteNodeByIndex(index);
    }

    static class FunctionNode {
        private final FunctionPoint point;
        private FunctionNode next;
        private FunctionNode prev;
        FunctionNode(FunctionPoint point){
            this.point = point;
        }
    }
    private FunctionNode getNodeByIndex(int index){
        if (index < 0 || index >= pointsCount) {
            throw new FunctionPointIndexOutOfBoundsException("Нет такого индекса!");
        }
        FunctionNode curr;
        if (abs(index-lastUsedIndex) < index){//если путь через lastUsedNode будет ближе, то пользуемся им, иначе идем с головы
            curr = lastUsedNode;
            int i = 0;
            if (index > lastUsedIndex){
                for (i = lastUsedIndex; i<index; ++i){
                    curr = curr.next;
                }
            } else {
                for (i = lastUsedIndex; i > index; --i){
                    curr = curr.prev;
                }
            }
            lastUsedIndex = i;
            lastUsedNode = curr;
        } else {
            curr = this.head;
            int i = 0;
            for (; i<index; ++i){
                curr = curr.next;
            }
            lastUsedIndex = i;
            lastUsedNode = curr;
        }
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
    private FunctionNode addNodeByIndex(int index, FunctionPoint point){
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
    private FunctionNode deleteNodeByIndex(int index){
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
}