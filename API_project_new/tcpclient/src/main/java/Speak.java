import java.io.IOException;
import java.util.Random;

public class Speak implements Runnable {
    private Random random = new Random();
    private ThreadHandler threadHandler;

    Speak(ThreadHandler threadHandler){
        this.threadHandler = threadHandler;
    }

    @Override
    public void run() {

        while (true){

            try {
                threadHandler.speak();

            } catch (Exception e) {
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
