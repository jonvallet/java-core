import java.util.concurrent.atomic.AtomicBoolean;

class PrintInOrder {

    private AtomicBoolean firstFinished = new AtomicBoolean(false);
    private AtomicBoolean secondFinished = new AtomicBoolean(false);
    private String result = "";

    public void first(Runnable printFirst)  {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        addResult("First");
        firstFinished.set(true);
    }

    public void second(Runnable printSecond)  {

        // printSecond.run() outputs "second". Do not change or remove this line.
        while (!firstFinished.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        printSecond.run();
        addResult("Second");
        secondFinished.set(true);

    }

    public void third(Runnable printThird) {

        // printThird.run() outputs "third". Do not change or remove this line.
        while (!secondFinished.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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