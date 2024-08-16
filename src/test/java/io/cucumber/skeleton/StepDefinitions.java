package io.cucumber.skeleton;


import endpoints.AddNewPetEndpoint;
import endpoints.FindPetByIdEndpoint;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.skeleton.ApplicationTestEndpoint;
import java.util.ArrayList;
import java.util.List;


public class StepDefinitions {

  @Given("I send a request to add a new pet")
  public void sendValidRequestAddPet() throws Exception {

    System.out.println("Sending Add PET request");

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(new AddNewPetEndpoint());
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.sendRequest();
  }

  @Given("I send a request to add a new pet, with following details")
  public void sendValidRequestAddPet(List<String> editDetails) throws Exception {

    System.out.println("Sending Add PET request after editing");

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(new AddNewPetEndpoint());
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.editBody(editDetails);
    applicationTestEndpoint.sendRequest();
  }

  @Then("^I send a request to fina a pet using id:(.*)$")
  public void sendValidRequestAddPet(String petId) throws Exception {

    System.out.println("Sending Find PET by ID request");

    FindPetByIdEndpoint findPetByIdEndpoint = new FindPetByIdEndpoint();
    findPetByIdEndpoint.setPathVariable("petId", petId.trim());

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(findPetByIdEndpoint);
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.sendRequest();
  }

  @Given("a new pet is added to the store with id:{int}")
  public void addNewPetToStore(int petId) throws Exception {

    List<String> editDetails = new ArrayList<>();
    editDetails.add("$.id:" + petId);
    sendValidRequestAddPet(editDetails);

    JSONResponseValidationStepDefinitions jsonResponseValidationStepDefinitions = new JSONResponseValidationStepDefinitions();
    jsonResponseValidationStepDefinitions.verifyResponseCode(200);
  }
}
