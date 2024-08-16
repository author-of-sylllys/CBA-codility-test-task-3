package io.cucumber.skeleton;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import endpoints.AddNewPetEndpoint;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.skeleton.ApplicationTestEndpoint;
import io.restassured.skeleton.PreviousTestEndpoint;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utilities.JSONFactory;


public class StepDefinitions {

  @Given("I send a request to add a new pet")
  public void send_valid_request_add_pet() throws Exception {

    System.out.println("Sending Add PET request");

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(new AddNewPetEndpoint());
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.sendRequest();
  }

  @Given("I send a request to add a new pet, with following details")
  public void send_valid_request_add_pet(List<String> editDetails) throws Exception {

    System.out.println("Sending Add PET request after editing");

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(new AddNewPetEndpoint());
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.editBody(editDetails);
    applicationTestEndpoint.sendRequest();
  }

}
