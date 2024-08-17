package endpoints;

import io.restassured.skeleton.TestEndPointDetails;
import java.util.HashMap;

public class UpdatePetUsingFormDataEndpoint extends TestEndPointDetails {

  public UpdatePetUsingFormDataEndpoint() throws Exception {

    this.setUrl("https://petstore.swagger.io/v2/pet/{petId}");
    this.setMethod("POST");
    this.setHeader("accept", "application/json");
    this.setHeader("content-type", "application/x-www-form-urlencoded");

    HashMap<String, String> formData = new HashMap<>();
    formData.put("name", "tommy");
    formData.put("status", "pending");
    this.setBody(formData);
  }
}
