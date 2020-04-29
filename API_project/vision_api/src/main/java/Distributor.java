import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Distributor implements Runnable {


    private DatagramPacket datagramPacket;
    private String initmsg;
    private Random random = new Random();

    //these are the static ques
    public static BlockingQueue<ArrayList<String>> blockingQueueVision = new ArrayBlockingQueue<ArrayList<String>>(1024);
    public static BlockingQueue<ArrayList<String>> blockingQueueReckog = new ArrayBlockingQueue<ArrayList<String>>(1024);



    Distributor(DatagramPacket datagramPacket, String initmsg) throws SocketException {
        this.datagramPacket = datagramPacket;
        this.initmsg = initmsg;
    }

    @Override
    public void run() {

        String msg = initmsg;
        int rand = random.nextInt(100);

        String alias = datagramPacket.getAddress().toString() + datagramPacket.getPort(); //new alias that is the same wherever instantiated, based on client ip/port
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(alias);
        arrayList.add(msg);

        if (rand<70){
            blockingQueueVision.add(arrayList);
        }else {
            blockingQueueReckog.add(arrayList);
        }


    }

}