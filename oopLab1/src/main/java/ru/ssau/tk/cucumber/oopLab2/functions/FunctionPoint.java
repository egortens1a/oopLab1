package functions;

public class FunctionPoint {
    private double x;
    private double y;
    public FunctionPoint(){
        x = 0;
        y = 0;
    }
    public FunctionPoint(double x, double y){
        this.x = x;
        this.y = y;
    }
    public FunctionPoint(FunctionPoint point){
        x = point.getX();
        y = point.getY();
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void setPoint(FunctionPoint p) {
        x = p.getX();
        y = p.getY();
    }
}
