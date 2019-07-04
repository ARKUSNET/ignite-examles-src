package example_thread;

public class MainATM1 {

    static int amount = 100;
    static final Object KEY = new Object();

//    synchronized void getMoney(int a) {
    void getMoney(int a){
        synchronized (KEY) {
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
        Thread thread1 = new Thread(() -> new MainATM1().getMoney(80));
        thread1.start();

        Thread thread2 = new Thread(() -> new MainATM1().getMoney(80));
        thread2.start();

        Thread thread3 = new Thread(() -> new MainATM1().getMoney(80));
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
