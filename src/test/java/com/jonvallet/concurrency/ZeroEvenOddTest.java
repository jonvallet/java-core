package com.jonvallet.concurrency;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZeroEvenOddTest {

    @Test
    public void zeroEvenOddTest() throws InterruptedException {

        List<Integer> result = Collections.synchronizedList(new ArrayList<>());

        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(2);

        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
            executor.submit(() -> zeroEvenOdd.zero(result::add));
            executor.submit(() -> zeroEvenOdd.odd(result::add));
            executor.submit(() -> zeroEvenOdd.even(result::add));
        }
        assertEquals(List.of(0, 1, 0, 2), result);
        System.out.println(result);
    }
}