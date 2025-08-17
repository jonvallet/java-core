import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintInOrderTest {

    private class PrintFirst implements Runnable {
        @Override
        public void run() {
            System.out.println("PrintFirst");
        }
    }

    private class PrintSecond implements Runnable {
        @Override
        public void run() {
            System.out.println("PrintSecond");
        }
    }

    private class PrintThird implements Runnable {
        @Override
        public void run() {
            System.out.println("PrintThird");
        }
    }

    @Test
    void printInOrder() {

        ExecutorService service = Executors.newFixedThreadPool(3);
        PrintInOrder po = new PrintInOrder();

        service.submit(() -> po.first(new PrintFirst()));
        service.submit(() -> po.second(new PrintSecond()));
        service.submit(() -> po.third(new PrintThird()));

        var expectedValue = "FirstSecondThird";
        Awaitility.await().atMost(Duration.ofMillis(500)).untilAsserted(() -> assertEquals(expectedValue, po.getResult()));
    }

}