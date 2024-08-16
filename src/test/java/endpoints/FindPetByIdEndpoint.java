package endpoints;

import io.restassured.skeleton.TestEndPointDetails;
import utilities.DataMiner;

public class FindPetByIdEndpoint extends TestEndPointDetails{

  public FindPetByIdEndpoint() throws Exception {

    this.setUrl("https://petstore.swagger.io/v2/pet/{petId}");
    this.setMethod("GET");
    this.setHeader("accept", "application/json");

  }
}
