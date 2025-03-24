package core.thread;

import java.util.concurrent.*;

class MyThread extends Thread {
    public void run() {
        for(int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class ThreadExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Thread thread1 = new Thread(new MyRunnable());
        executor.submit(thread1);
        Thread thread2 = new Thread(new MyRunnable());
        executor.execute(thread2);

        Callable<Integer> callable = new Task();

        Future<Integer> future = executor.submit(callable);

        System.out.println(future.get());
    }
}

class MyRunnable implements  Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);
            try { Thread.sleep((long) (Math.random() * 1000)); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}

class Task implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
