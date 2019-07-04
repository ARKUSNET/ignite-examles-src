package example_thread;

public class MainATM {

    volatile static int amount = 100;

        static void getMoney(int a) {
//    synchronized static void getMoney(int a) {
            synchronized (MainATM.class) {
                if (amount >= a) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    amount -= a;
                    System.out.println("ok: " + amount);
                }
            }
    }

    public static void main(String[] args) {
        Runnable runnable = () -> getMoney(80);

        Thread thread1 = new Thread(runnable);
        thread1.start();

        Thread thread2 = new Thread(runnable);
        thread2.start();

        Thread thread3 = new Thread(runnable);
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.err.println("result amount: " + amount);
    }
}
