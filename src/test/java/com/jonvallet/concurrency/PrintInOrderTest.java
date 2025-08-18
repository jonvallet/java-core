package com.jonvallet.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintInOrderTest {

    @Test
    void printInOrder() {

        PrintInOrder po;
        try (ExecutorService service = Executors.newFixedThreadPool(3)) {
            po = new PrintInOrder();

            service.submit(() -> po.first(() -> System.out.println("first")));
            service.submit(() -> po.second(() -> System.out.println("second")));
            service.submit(() -> po.third(() -> System.out.println("third")));
        }

        var expectedValue = "FirstSecondThird";
        assertEquals(expectedValue, po.getResult());
    }

}