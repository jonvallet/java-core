package com.jonvallet.concurrency;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FizzBuzzTest {

    @Test
    public void testFizzBuzz() {
        List<String> expectedValue = List.of("1", "2", "fizz", "4", "buzz", "fizz", "7", "8", "fizz", "buzz", "11", "fizz", "13", "14", "fizzbuzz");
        List<String> actualValue = Collections.synchronizedList(new ArrayList<>());
        var fizzBuzz = new FizzBuzz(15);
        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            executor.submit(() -> {
                try {
                    fizzBuzz.fizz(() -> actualValue.add("fizz"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.submit(() -> {
                try {
                    fizzBuzz.buzz(() -> actualValue.add("buzz"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.submit(() -> {
                try {
                    fizzBuzz.fizzbuzz(() -> actualValue.add("fizzbuzz"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            executor.submit(() -> {
                try {
                    fizzBuzz.number((n) -> actualValue.add(Integer.toString(n)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        assertEquals(expectedValue, actualValue);
    }

}