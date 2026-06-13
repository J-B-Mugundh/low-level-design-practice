package org.example.com;

/*
UberService class is responsible for
1. Booking Order
2. Send SMS
3. Calculate ETA
*/

// Using Thread & Runnable - Fire & Forget principle
// Using Callable - Wait for results - can return & throw exceptions
// Use Executor framework as replacement for manually creating and managing threads

import java.util.concurrent.*;

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
        FutureTask<String> etaThreadRunnable = new FutureTask<>(new ETACalculatorCallable("Bangalore"));
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
            String eta = etaThreadRunnable.get();
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

// Callable Example
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

// Executor Example
class EmailService {
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void sendEmail(String recipient) {

        executor.execute(() -> {
            System.out.println("Sending email to " + recipient + " on " + Thread.currentThread().getName());
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Email sent to " + recipient);
        });

    }

    public static void main(String[] args) {
        for(int i = 0; i < 24; i++){
            sendEmail("user" + i + "@gmal.com");
        }
        executor.shutdown();
    }
}

// Future Executor Example
class FutureExecutorExample{
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Integer> future1 = executor.submit(() -> {
            System.out.println("Using future executor 1 with " + Thread.currentThread().getName());
            Thread.sleep(1000);
            return 1000;
        });

        Future<Integer> future2 = executor.submit(() -> {
            System.out.println("Using future executor 2 with " + Thread.currentThread().getName());
            Thread.sleep(1000);
            return 1000;
        });

        Future<Integer> future3 = executor.submit(() -> {
            System.out.println("Using future executor 3 with " + Thread.currentThread().getName());
            Thread.sleep(1000);
            return 1000;
        });

        System.out.println("Doing some other work in " + Thread.currentThread().getName());

        System.out.println("Result of future executor 1: " + future1.get());
        System.out.println("Result of future executor 2: " + future2.get());
        System.out.println("Result of future executor 3: " + future3.get());

        executor.shutdown();
    }
}