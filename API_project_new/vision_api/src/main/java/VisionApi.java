import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VisionApi {

    public JSONObject evalPicture(String filePath){
        // Instantiates a client
        try (
                ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            // The path to the image file to annotate
            String fileName = filePath;

            // Reads the image file into memory
            Path path = Paths.get(fileName);
            byte[] data = Files.readAllBytes(path);
            ByteString imgBytes = ByteString.copyFrom(data);

            // Builds the image annotation request
            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request =
                    AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            requests.add(request);

            // Performs label detection on the image file
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            System.out.println("Detected labels for " + fileName + "google vision request sent");
            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.printf("Error: %s\n", res.getError().getMessage());
                    return null;
                }
                JSONArray jsonArray = new JSONArray(); //jsonArr
                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    //JsonObject jsonObject = new JsonObject(); google jsonobject.. works but like the other one better
                    JSONObject jsonObject = new JSONObject(); // JSONsimple jsonObject.. Both works
                    annotation

                            .getAllFields()

                            .forEach((k, v) -> jsonObject.put(k.toString(),v.toString()));
                    jsonArray.add(jsonObject);
                    //this gets the whole heap of informtion, maybe it should only get index 0 ?
                }


                return (JSONObject) jsonArray.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
