package ru.ssau.tk.cucumber.oopLab2.threads;

import ru.ssau.tk.cucumber.oopLab2.functions.Functions;

public class SimpleIntegrator implements Runnable{
    private final Task task;
    public SimpleIntegrator(Task task){
        this.task = task;
    }
    @Override
    public void run() {
        double area;

        while (task.getTaskCount() > 0) {
            synchronized (task) {
                try {
                    area = Functions.integral(task.getFunction(),
                            task.getLeftDomainBorder(),
                            task.getRightDomainBorder(),
                            task.getStep());
                } catch (Exception e) {
                    throw new RuntimeException();
                }
                System.out.printf(task.getTaskCount() + ") Result " + task.getLeftDomainBorder() + "   " +
                        task.getRightDomainBorder() + "   " +
                        task.getStep() + "   " +
                        area
                        + "\n");
                task.setTaskCount(task.getTaskCount() - 1);
                try {
                    task.wait(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                task.notify();
            }
        }
    }
}
