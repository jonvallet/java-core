package concurrency;

import com.jonvallet.concurrency.PrintFooBar;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class PrintFooBarTest {
    @Test
    void printFooBar() {
        var n = 5;
        var printFooBar = new PrintFooBar(n);
        try (ExecutorService service = Executors.newFixedThreadPool(2)) {
            service.submit(() -> printFooBar.foo(() -> System.out.println("foo")));
            service.submit(() -> printFooBar.bar(() -> System.out.println("bar")));
        }

    }

}