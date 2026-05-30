package org.example.com;

/*
UberService class is responsible for
1. Booking Order
2. Send SMS
3. Calculate ETA
*/

// Using Thread & Runnable - Fire & Forget principle
// Using Callable - Wait for results - can return & throw exceptions

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class UberService {
    public static void main(String[] args) throws InterruptedException{
        // 1. Using Thread
//        SMSThread smsThread = new SMSThread();
//        EmailThread emailThread = new EmailThread();
//        System.out.println("Task started!");
//        smsThread.start();
//        System.out.println("Task 1 ongoing");
//        emailThread.start();
//        System.out.println("Task 2 ongoing");
//        try{
//            smsThread.join();
//            emailThread.join();
//            System.out.println("Tasks done!");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // 2. Using Runnable
//        Thread smsThread = new Thread(new SMSThreadRunnable());
//        Thread emailThread = new Thread(new EmailThreadRunnable());
//
//        System.out.println("Task started!");
//        smsThread.start();
//        System.out.println("Task 1 ongoing");
//        emailThread.start();
//        System.out.println("Task 2 ongoing");
//        try{
//            smsThread.join();
//            emailThread.join();
//            System.out.println("Tasks done!");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // 3. Callable
        Thread smsThread = new Thread(new SMSThreadRunnable());
        Thread emailThread = new Thread(new EmailThreadRunnable());
        // Note: Can't use Thread class coz it excepts a Runnable target as parameter.
        // Using Callable there is not possible. Hence, we use FutureTask which supports Callable parameter
        FutureTask etaThreadRunnable = new FutureTask<>(new ETACalculatorCallable("Bangalore"));
        Thread etaThread = new Thread(etaThreadRunnable);
        System.out.println("Task started!");
        smsThread.start();
        System.out.println("Task 1 ongoing");
        emailThread.start();
        System.out.println("Task 2 ongoing");
        etaThread.start();
        System.out.println("Task 3 ongoing");
        try{
            smsThread.join();
            emailThread.join();
            String eta = (String) etaThreadRunnable.get();
            System.out.println(eta);
            System.out.println("Tasks done!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

// Implementing Runnable (usually preferred as it decouples task from execution)
class SMSThreadRunnable implements Runnable{
    @Override
    public void run() {
        try{
            Thread.sleep(2000);
            System.out.println("SMS Sent using Thread!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class EmailThreadRunnable implements Runnable{
    @Override
    public void run() {
        try{
            Thread.sleep(3000);
            System.out.println("Email Sent using Thread!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ETAThreadRunnable implements Runnable{
    @Override
    public void run() {
        try{
            Thread.sleep(3000);
            System.out.println("ETA Calculated using Thread!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Extending Thread
class SMSThread extends Thread{
    @Override
    public void run() {
        try{
            Thread.sleep(2000);
            System.out.println("SMS Sent using Thread!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class EmailThread extends Thread{
    @Override
    public void run() {
        try{
            Thread.sleep(3000);
            System.out.println("Email Sent using Thread!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ETAThread extends Thread{
    @Override
    public void run() {
        try{
            Thread.sleep(3000);
            System.out.println("ETA Calculated using Thread!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ETACalculatorCallable implements Callable<String> {

    public final String location;

    public ETACalculatorCallable(String location){
        this.location = location;
    }

    public String call() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println("ETA Calculation using Callable");
        return "ETA for " + location + ": 20 minutes";
    }
}