import myfirstpackage.MyFirstPackage;

class My4Class {
    public static void not_main(String[] args) {
        MyFirstPackage o = new MyFirstPackage(5, 6);
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