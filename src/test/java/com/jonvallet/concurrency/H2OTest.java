package com.jonvallet.concurrency;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class H2OTest {

    @Test
    public void testH20() {
        H2O h2O = new H2O();
        int hydrogenAtoms = 4;
        int oxygenAtoms = 2;
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {

            for (int i = 0; i < oxygenAtoms; i++) {
                executorService.submit(() -> {
                    try {
                        h2O.oxygen(() -> list.add("O"));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            for (int i = 0; i < hydrogenAtoms; i++) {
                executorService.submit(() -> {
                    try {
                        h2O.hydrogen(() -> list.add("H"));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        assertEquals(List.of("H", "H", "O", "H", "H", "O"), list);
        System.out.println(list);
    }

}