package endpoints;

import io.restassured.skeleton.TestEndPointDetails;
import utilities.DataMiner;

public class AddNewPetEndpoint extends TestEndPointDetails{

  public AddNewPetEndpoint() throws Exception {

    this.setUrl("https://petstore.swagger.io/v2/pet");
    this.setMethod("POST");
    this.setHeader("accept", "application/json");
    this.setHeader("Content-Type", "application/json");
    this.setBody(DataMiner.refactor("file:/json-body/add_pet_body.json"));

  }
}
