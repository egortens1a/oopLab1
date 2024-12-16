package ru.ssau.tk.cucumber.oopLab2.threads;

import ru.ssau.tk.cucumber.oopLab2.functions.basic.Log;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Generator extends Thread{
    private final Task task;
    private final Semaphore semaphore;
    public Generator(Task task, Semaphore semaphore){
        this.task = task;
        this.semaphore = semaphore;}

    @Override
    public void run(){
        try {
            while (task.getTaskCount() > 0) {
                semaphore.acquire();
                Random random = new Random();
                this.task.update(
                        new Log(random.nextDouble() * 9 + 1),
                        random.nextDouble() * 100,
                        100 + random.nextDouble() * 100,
                        random.nextDouble() + 0.0001
                );
                System.out.printf(task.getTaskCount() + ") Source " +
                        task.getLeftDomainBorder() + "   " +
                        task.getRightDomainBorder() + "   " +
                        task.getStep() + "\n");
                semaphore.release();
            }
        }catch (Exception e) {
            System.out.println("InterruptedException");
        }
    }
}
