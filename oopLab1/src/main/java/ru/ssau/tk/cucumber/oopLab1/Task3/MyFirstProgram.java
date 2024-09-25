package ru.ssau.tk.cucumber.oopLab1.Task3;

class MyFirstClass {
    public static void main(String[] s) {
        System.out.println("Hello world!!!");
    }
}
class MySecondClass{
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
    public MySecondClass(int a, int b) {
        this.boka = a;
        this.zhoka = b;
    }
    public int activate(){
        return (boka + zhoka);
    }
    public static void main(String[] args) {
        MySecondClass o = new MySecondClass(5, 6);
        int i, j;
        for (i = 1; i <= 8; i++) {
            for(j = 1; j <= 8; j++) {
                o.setBoka(i);
                o.setZhoka(j);
                System.out.print(o.activate());
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}