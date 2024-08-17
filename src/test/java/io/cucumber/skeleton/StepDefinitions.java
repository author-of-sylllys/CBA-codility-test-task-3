package io.cucumber.skeleton;


import endpoints.AddNewPetEndpoint;
import endpoints.DeletePetByIdEndpoint;
import endpoints.FindPetByIdEndpoint;
import endpoints.UpdateExistingPetEndpoint;
import endpoints.UploadPetImageEndpoint;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.skeleton.ApplicationTestEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
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
  public void sendRequestAddPet(List<String> editDetails) throws Exception {

    System.out.println("Sending Add PET request after editing");

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(new AddNewPetEndpoint());
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.editBody(editDetails);
    applicationTestEndpoint.sendRequest();
  }

  @Then("I send a request to update an existing pet, with following details")
  public void sendRequestUpdatePet(List<String> editDetails) throws Exception {

    System.out.println("Sending Update PET request after editing");

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(new UpdateExistingPetEndpoint());
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.editBody(editDetails);
    applicationTestEndpoint.sendRequest();
  }

  @Then("^I send a request to find a pet using id:(.*)$")
  public void sendRequestToFindPet(String petId) throws Exception {

    System.out.println("Sending Find PET by ID request");

    FindPetByIdEndpoint findPetByIdEndpoint = new FindPetByIdEndpoint();
    findPetByIdEndpoint.setPathVariable("petId", petId.trim());

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(findPetByIdEndpoint);
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.sendRequest();
  }

  @Then("^I send a request to upload a pet image using id:(.*)$")
  public void sendRequestUploadPetImage(String petId) throws Exception {

    System.out.println("Uploading PET image using it's ID");

    UploadPetImageEndpoint uploadPetImageEndpoint = new UploadPetImageEndpoint();
    uploadPetImageEndpoint.setPathVariable("petId", petId.trim());

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(uploadPetImageEndpoint);
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.sendRequest();
  }

  @Then("^I send an empty request to upload a pet image using id:(.*)$")
  public void sendEmptyRequestUploadPetImage(String petId) throws Exception {

    System.out.println("Uploading PET image using it's ID with out file attached");

    UploadPetImageEndpoint uploadPetImageEndpoint = new UploadPetImageEndpoint();
    uploadPetImageEndpoint.setPathVariable("petId", petId.trim());
    uploadPetImageEndpoint.setBody(null);

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(uploadPetImageEndpoint);
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.sendRequest();
  }

  @Then("^I send a request with mandatory data to upload a pet image using id:(.*)$")
  public void sendRequestWithMandotoryUploadPetImage(String petId) throws Exception {

    System.out.println("Uploading PET image using it's ID with out file attached");

    UploadPetImageEndpoint uploadPetImageEndpoint = new UploadPetImageEndpoint();
    uploadPetImageEndpoint.setPathVariable("petId", petId.trim());
    HashMap<String, String> formData = (HashMap<String, String>) uploadPetImageEndpoint.getBody();
    formData.remove("additionalMetadata");
    uploadPetImageEndpoint.setBody(formData);

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(uploadPetImageEndpoint);
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.sendRequest();
  }

  @Given("a new pet is added to the store with id:{int}")
  public void addNewPetToStore(int petId) throws Exception {

    List<String> editDetails = new ArrayList<>();
    editDetails.add("$.id:" + petId);
    sendRequestAddPet(editDetails);

    JSONResponseValidationStepDefinitions jsonResponseValidationStepDefinitions = new JSONResponseValidationStepDefinitions();
    jsonResponseValidationStepDefinitions.verifyResponseCode(200);
  }

  @Then("^I send a request to delete a pet with id:(.*)$")
  public void sendRequestToDeletePet(String petId) throws Exception {

    System.out.println("Sending Delete PET by ID request");

    DeletePetByIdEndpoint deletePetByIdEndpoint = new DeletePetByIdEndpoint();
    deletePetByIdEndpoint.setPathVariable("petId", petId.trim());

    ApplicationTestEndpoint applicationTestEndpoint = new ApplicationTestEndpoint();
    applicationTestEndpoint.setEndPointDetails(deletePetByIdEndpoint);
    applicationTestEndpoint.constructRequest();
    applicationTestEndpoint.sendRequest();
  }
}
