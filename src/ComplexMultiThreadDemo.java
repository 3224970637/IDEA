import java.util.concurrent.*;

public class ComplexMultiThreadDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<Integer> future1 = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                System.out.println("Hello from Thread 1!");
                return 1;
            }
        });

        Future<Integer> future2 = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                System.out.println("Hello from Thread 2!");
                return 2;
            }
        });

        System.out.println("Future1 result: " + future1.get());
        System.out.println("Future2 result: " + future2.get());

        executor.shutdown();
    }
}
