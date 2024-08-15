package io.cucumber.skeleton;

import static org.junit.Assert.assertTrue;
import io.cucumber.java.en.Given;
import io.restassured.skeleton.ApplicationTestEndpoint;
import io.restassured.skeleton.TestEndPointDetails;

public class StepDefinitions {

  @Given("verify cucumber setup")
  public void verify_cucumber_setup() {

    System.out.println("Cucumber setup is working!");
  }


  @Given("I send a sample request")
  public void send_sample_request() throws Exception {

    System.out.println("Sending sample request");

    TestEndPointDetails testEndPointDetails = new TestEndPointDetails();

    testEndPointDetails.setName("Retrieve a pet");

    testEndPointDetails.setUrl("https://petstore.swagger.io/v2/pet/{petId}");
    testEndPointDetails.setPathVariable("petId","junk");
    testEndPointDetails.setMethod("GET");
    testEndPointDetails.setHeader("accept","application/json");

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();

    applicationTestEndpoint.setEndPointDetails(testEndPointDetails);
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.sendRequest();

    assertTrue("reponse code is not as expected: 404, actual:"
        + applicationTestEndpoint.getResponse().getStatusCode(),
        applicationTestEndpoint.getResponse().getStatusCode() == 404);

  }

}
