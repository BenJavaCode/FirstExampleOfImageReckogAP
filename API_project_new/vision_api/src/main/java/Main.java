import java.net.DatagramSocket;

public class Main {

    public static void main(String... args) throws Exception {

        //the socket...Just for the sake of showing that the sokcet is passed, which is not necesarilly necessary for UDP
        DatagramSocket dgs = new DatagramSocket(6780);

        //controll que and send requests to visionApi
        VisionApiQueHandler visionApiQueHandler = new VisionApiQueHandler();
        Thread thread = new Thread(visionApiQueHandler);
        thread.start();

        //controll que and send requests to reckogAPi
        ReckogApiQueHandler reckogApiQueHandler = new ReckogApiQueHandler();
        Thread thread2 = new Thread(reckogApiQueHandler);
        thread2.start();

        //receive requests
        RequestHandler requestHandler = new RequestHandler(dgs);
        Thread thread3 = new Thread(requestHandler);
        thread3.start();

        }
    }


