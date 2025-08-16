import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest {

    @Test
    public void testHelloWorld() {
        var expectedValue = 4;
        var actualValue = 2 + 2;
        assertEquals(expectedValue, actualValue);
    }
}
