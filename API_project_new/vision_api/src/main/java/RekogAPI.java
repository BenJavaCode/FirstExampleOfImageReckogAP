import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.util.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

public class RekogAPI {

    public JSONObject evalPicture(String filePath) throws FileNotFoundException {
        
        String photo = filePath;


        ByteBuffer imageBytes = null;
        try (InputStream inputStream = new FileInputStream(new File(photo))) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }


        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                        .withBytes(imageBytes))
                .withMaxLabels(10)
                .withMinConfidence(77F);

        try {

            DetectLabelsResult result = rekognitionClient.detectLabels(request);
            List<Label> labels = result.getLabels();

            System.out.println("Detected labels for " + photo + "aws rekog request sent");
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (Label label: labels) {
                jsonObject.put(label.getName(), label.getConfidence().toString());
                jsonArray.add(jsonObject);
            }
           return (JSONObject)jsonArray.get(4);

        } catch (AmazonRekognitionException e) {
            e.printStackTrace();
        }
        return null;
    }

}

