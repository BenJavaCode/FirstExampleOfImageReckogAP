import java.io.IOException;
import java.util.Random;

public class Listen implements Runnable {
    private ThreadHandler threadHandler;
    private Random random = new Random();

    Listen(ThreadHandler threadHandler){
        this.threadHandler = threadHandler;
    }

    @Override
    public void run() {
        while (true){
            try {
                threadHandler.hear();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(random.nextInt(200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
