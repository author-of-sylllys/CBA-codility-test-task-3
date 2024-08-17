package endpoints;

import io.restassured.skeleton.TestEndPointDetails;

public class FindPetsByStatusEndpoint extends TestEndPointDetails{

  public FindPetsByStatusEndpoint() throws Exception {

    this.setUrl("https://petstore.swagger.io/v2/pet/findByStatus");
    this.setMethod("GET");
    this.setHeader("accept", "application/json");
    this.setParameter("status", "available");
  }
}
