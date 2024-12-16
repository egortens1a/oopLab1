package ru.ssau.tk.cucumber.oopLab2.threads;

import ru.ssau.tk.cucumber.oopLab2.functions.basic.Log;

import java.util.Random;

public class SimpleGenerator implements Runnable{
    private final Task task;

    public SimpleGenerator(Task task){
        this.task = task;
    }

    public void run() {
        while (task.getTaskCount() > 0) {
            Random random = new Random();
            synchronized (task) {
                this.task.update(
                        new Log(random.nextDouble() * 9 + 1),
                        random.nextDouble() * 100,
                        100 + random.nextDouble() * 100,
                        random.nextDouble() + 0.0001
                );
                task.notify();
                System.out.printf(task.getTaskCount() + ") Source " +
                        task.getLeftDomainBorder() + "   " +
                        task.getRightDomainBorder() + "   " +
                        task.getStep() + "\n");

                try {
                    task.wait(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
