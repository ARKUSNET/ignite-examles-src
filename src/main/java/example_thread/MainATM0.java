package example_thread;

public class MainATM0 {

    static int amount = 100;

//    synchronized void getMoney(int a) {
    void getMoney(int a){
        synchronized (this) {
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
        Thread thread1 = new Thread(() -> new MainATM0().getMoney(80));
        thread1.start();

        Thread thread2 = new Thread(() -> new MainATM0().getMoney(80));
        thread2.start();

        Thread thread3 = new Thread(() -> new MainATM0().getMoney(80));
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
