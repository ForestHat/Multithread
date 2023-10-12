import java.util.Random;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] massive = generateMassive();

        CounterThread counterThreadFirst = new CounterThread(massive, 0, massive.length/2);
        CounterThread counterThreadSecond = new CounterThread(massive, massive.length/2, massive.length);

        Thread threadFirst = new Thread(counterThreadFirst);
        Thread threadSecond = new Thread(counterThreadSecond);

        long begin = System.currentTimeMillis();

        threadFirst.start();
        threadSecond.start();

        threadFirst.join();
        threadSecond.join();

        long experimentFirst = counterThreadFirst.getSum() + counterThreadSecond.getSum();

        long end = System.currentTimeMillis();

        System.out.println("Sum of numbers (with 2 threads): " + experimentFirst + ". Running time: " + (end-begin));

        long beginSecond = System.currentTimeMillis();
        long experimentSecond = oneThread(massive);
        long endSecond = System.currentTimeMillis();

        System.out.println("Sum of numbers (with 1 thread): " + experimentSecond + ". Running time: " + (endSecond-beginSecond));
    }

    public static int[] generateMassive() {
        int[] massive = new int[100000000];
        Random random = new Random();

        for (int i = 0; i < massive.length; i++) {
            massive[i] = random.nextInt(10);
        }

        return massive;
    }
    public static long oneThread(int[] massive) {
        long sum = 0;

        for (int j : massive) {
            sum += j;
        }

        return sum;
    }
}

class CounterThread extends Thread {
    private final int[] massive;
    private long sum;
    private final int begin;
    private final int end;

    public CounterThread(int[] massive, int begin, int end) {
        this.massive = massive;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = begin; i < end; i++) {
            sum += massive[i];
        }
    }

    public long getSum() {
        return sum;
    }
}