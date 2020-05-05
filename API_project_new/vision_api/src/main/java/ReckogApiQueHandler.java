import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.net.SocketException;
import java.util.ArrayList;

public class ReckogApiQueHandler implements Runnable {
    ArrayList<String> arrayList = new ArrayList<>();
    ResponseHandler responseHandler;

    public ReckogApiQueHandler() throws SocketException {
    }

    @Override
    public void run() {
        int count = 0;

        while (true) {
            if (Distributor.blockingQueueReckog.peek() != null) {

                RekogAPI rekogAPI = new RekogAPI();
                arrayList = (Distributor.blockingQueueReckog.poll());
                JSONObject jsonObject = null; // index 1 = msg
                try {
                    jsonObject = rekogAPI.evalPicture(arrayList.get(1));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                responseHandler = new ResponseHandler(arrayList.get(0), jsonObject.toString());
                Thread thread = new Thread(responseHandler);
                thread.start();

                //sessionManager.QueryAdresses(arrayList.get(0), jsonObject.toString());
            }
        }
    }
}
