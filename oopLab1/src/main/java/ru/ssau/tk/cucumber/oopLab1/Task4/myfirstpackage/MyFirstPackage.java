package myfirstpackage;

public class MyFirstPackage{
    private int boka;
    private int zhoka;

    public int getBoka() {
        return boka;
    }
    public int getZhoka() {
        return zhoka;
    }
    public void setBoka(int boka) {
        this.boka = boka;
    }
    public void setZhoka(int zhoka) {
        this.zhoka = zhoka;
    }
    public MyFirstPackage(int a, int b) {
        this.boka = a;
        this.zhoka = b;
    }
    public int activate(){
        return (boka + zhoka);
    }
}