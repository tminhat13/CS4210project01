import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class raceCondition extends Thread {
    static final int BUFFER_SIZE = 10;
    static final int ROUND_NUM = 20;
    static int [] buffer = new int[BUFFER_SIZE];
    static Semaphore emptyBuffer = new Semaphore(BUFFER_SIZE);
    static Semaphore occupiedBuffer = new Semaphore(0);
    static int nextIn = 0;
    static int nextOut = 0;
    static int countDown = ROUND_NUM;
    static boolean exit = false;

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producerThreat();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumerThread();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
                thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }
    public static void producerThreat() throws InterruptedException {
        Random random = new Random();
        while (!exit) {
            int k1 = random.nextInt(BUFFER_SIZE / 2) + 1;
            for (int i = 0; i <= k1; i++) {
                if (emptyBuffer.availablePermits() > 0) {
                    emptyBuffer.acquire();
                    buffer[i] = 1;
                    occupiedBuffer.release();
                } else {
                    break;
                }
            }
            nextIn = (nextIn + k1) % buffer.length;
            System.out.println(Arrays.toString(buffer));
            countDown--;
            if (countDown < 0) {
                System.out.println("Producer exits!");
                exit = true;
            }
            Thread.sleep((int) Math.floor(Math.random() * (500 - 100 + 1) + 100));
        }
    }
    public static void consumerThread() throws InterruptedException {
        Random random = new Random();
        while (!exit){
            Thread.sleep((int) Math.floor(Math.random() * (500 - 100 + 1) + 100));
            int k2 = random.nextInt(BUFFER_SIZE / 2) + 1;
            int data;
            for(int i = 0; i <= k2; i++){
                occupiedBuffer.acquire();
                data=buffer[i];
                if(data != 1){
                    System.out.println("No value");
                    occupiedBuffer.release();
                }
            }
            nextOut = (nextOut + k2) % buffer.length;
            System.out.println(Arrays.toString(buffer));
            countDown--;
            if(countDown < 0) {
                System.out.println("Consumer exits!");
                exit = true;
            }
            Thread.sleep((int) Math.floor(Math.random() * (500 - 100 + 1) + 100));
        }
    }


}
