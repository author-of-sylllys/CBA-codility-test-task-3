package endpoints;

import io.restassured.skeleton.TestEndPointDetails;
import java.util.HashMap;
import utilities.DataMiner;

public class UploadPetImageEndpoint extends TestEndPointDetails {

  public UploadPetImageEndpoint() throws Exception {

    this.setUrl("https://petstore.swagger.io/v2/pet/{petId}/uploadImage");
    this.setMethod("POST");
    this.setHeader("accept", "application/json");
    this.setHeader("content-type", "multipart/form-data");

    HashMap<String, String> formData = new HashMap<>();
    formData.put("file",
        new java.io.File(".").getAbsolutePath() + "/src/test/resources/pet-image.jpg");
    formData.put("additionalMetadata", "sample");
    this.setBody(formData);

  }
}
