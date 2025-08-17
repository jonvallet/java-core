package com.jonvallet.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class PrintInOrder {

    private CountDownLatch firstFinished = new CountDownLatch(1);
    private CountDownLatch secondFinished = new CountDownLatch(1);
    private String result = "";

    public void first(Runnable printFirst) {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        addResult("First");
        firstFinished.countDown();
    }

    public void second(Runnable printSecond) {

        // printSecond.run() outputs "second". Do not change or remove this line.
        try {
            if (!firstFinished.await(100, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("First timed out");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        printSecond.run();
        addResult("Second");
        secondFinished.countDown();
    }

    public void third(Runnable printThird) {

        // printThird.run() outputs "third". Do not change or remove this line.
        try {
            if (!secondFinished.await(100, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Second timed out");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        printThird.run();
        addResult("Third");
    }

    private synchronized void addResult(String result) {
        this.result = this.result + result;
    }

    public synchronized String getResult() {
        return this.result;
    }
}