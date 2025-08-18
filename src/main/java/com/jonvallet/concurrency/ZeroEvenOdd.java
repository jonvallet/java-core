package com.jonvallet.concurrency;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;
    private final Semaphore zero = new Semaphore(1);
    private final Semaphore even = new Semaphore(0);
    private final Semaphore odd = new Semaphore(0);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) {
        for (int i = 1; i <= n; i++) {
            try {
                zero.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            printNumber.accept(0);
            if (i % 2 == 0) {
                even.release();
            } else {
                odd.release();
            }
        }
    }

    public void even(IntConsumer printNumber) {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                try {
                    even.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                printNumber.accept(i);
                zero.release();
            }
        }
    }

    public void odd(IntConsumer printNumber) {
        for (int i = 1; i <= n; i++) {
            if (i % 2 != 0) {
                try {
                    odd.acquire();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                printNumber.accept(i);
                zero.release();
            }
        }
    }
}