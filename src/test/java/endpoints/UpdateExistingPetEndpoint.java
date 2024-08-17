package endpoints;

import io.restassured.skeleton.TestEndPointDetails;
import utilities.DataMiner;

public class UpdateExistingPetEndpoint extends TestEndPointDetails{

  public UpdateExistingPetEndpoint() throws Exception {

    this.setUrl("https://petstore.swagger.io/v2/pet");
    this.setMethod("PUT");
    this.setHeader("accept", "application/json");
    this.setHeader("Content-Type", "application/json");
    this.setBody(DataMiner.refactor("file:/json-body/update_pet_body.json"));

  }
}
