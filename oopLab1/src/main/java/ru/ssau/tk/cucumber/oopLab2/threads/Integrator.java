package ru.ssau.tk.cucumber.oopLab2.threads;

import ru.ssau.tk.cucumber.oopLab2.functions.Functions;
import java.util.concurrent.Semaphore;

public class Integrator extends Thread{
    private final Task task;
    private final Semaphore semaphore;
    public Integrator(Task task, Semaphore semaphore){
        this.task = task;
        this.semaphore = semaphore;}

    @Override
    public void run(){
        try {
            while (task.getTaskCount() > 0) {
                semaphore.acquire();
                System.out.printf(task.getTaskCount() + ") Result " + task.getLeftDomainBorder() + "   " +
                    task.getRightDomainBorder() + "   " +
                    task.getStep() + "   " +
                    Functions.integral(task.getFunction(),
                            task.getLeftDomainBorder(),
                            task.getRightDomainBorder(),
                            task.getStep()) + "\n");
                task.setTaskCount(task.getTaskCount() - 1);
                semaphore.release();
            }
        }catch (Exception e) {
            System.out.println("InterruptedException");}
    }
}