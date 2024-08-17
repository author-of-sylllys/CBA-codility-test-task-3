package endpoints;

import io.restassured.skeleton.TestEndPointDetails;

public class DeletePetByIdEndpoint extends TestEndPointDetails{

  public DeletePetByIdEndpoint() throws Exception {

    this.setUrl("https://petstore.swagger.io/v2/pet/{petId}");
    this.setMethod("DELETE");
    this.setHeader("accept", "application/json");

  }
}
