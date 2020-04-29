import org.json.simple.JSONObject;

import java.net.SocketException;
import java.util.ArrayList;

public class VisionApiQueHandler implements Runnable {

    ArrayList<String> arrayList = new ArrayList<>();
    ResponseHandler responseHandler;

    public VisionApiQueHandler() throws SocketException {
    }

    @Override
    public void run() {
        int count = 0;

        while (true) {
            if (Distributor.blockingQueueVision.peek() != null) {

                VisionApi visionApi = new VisionApi();
                arrayList = (Distributor.blockingQueueVision.poll());
                JSONObject jsonObject = visionApi.evalPicture(arrayList.get(1)); // index 1 = msg

                responseHandler = new ResponseHandler(arrayList.get(0), jsonObject.toString());
                Thread thread = new Thread(responseHandler);
                thread.start();

                //sessionManager.QueryAdresses(arrayList.get(0), jsonObject.toString());
            }
        }
    }
}


