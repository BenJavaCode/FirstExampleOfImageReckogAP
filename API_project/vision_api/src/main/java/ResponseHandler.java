import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class ResponseHandler implements Runnable {

    private String alias;
    private String jsonObject;
    ArrayList<Object> arrayList = new ArrayList<>();
    DatagramSocket datagramSocket;
    DatagramPacket datagramPacket;

    public ResponseHandler(String alias, String jsonObject) {
        this.alias = alias;
        this.jsonObject = jsonObject;
    }

    @Override
    public void run() {
        try {
            SessionManager sessionManager = new SessionManager();
            arrayList = sessionManager.queryAdresses(alias,jsonObject);
            datagramSocket = (DatagramSocket) arrayList.get(0);
            datagramPacket = (DatagramPacket) arrayList.get(1);

            datagramSocket.send(datagramPacket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
