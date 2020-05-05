import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class RequestHandler implements Runnable {


    DatagramSocket dgs;
    byte[] recv = new byte[1000]; //1500 er max

    public RequestHandler(DatagramSocket dgs) throws SocketException {
        this.dgs = dgs;
    }

    public void run() {

        while (true){
            String msgrecived = null;
            DatagramPacket dtg = null;

            try {
                dtg = new DatagramPacket(recv, recv.length);
                dgs.receive(dtg);
                msgrecived = new String(recv, 0, dtg.getLength());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (msgrecived != null){
                Distributor distributor = null;
                SessionManager sessionManager = null;
                try {
                    distributor = new Distributor(dtg, msgrecived);
                    sessionManager = new SessionManager(dtg, msgrecived, dgs);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                //delegate request to Distributor(EchoListener)
                Thread thread = new Thread(distributor);
                thread.start();

                //delegate request to SessionManager
                Thread sessionManagerThread = new Thread(sessionManager);
                sessionManagerThread.start();
            }

        }
    }
}
