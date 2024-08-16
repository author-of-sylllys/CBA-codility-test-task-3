package io.cucumber.skeleton;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import endpoints.AddNewPetEndpoint;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.skeleton.ApplicationTestEndpoint;
import io.restassured.skeleton.PreviousTestEndpoint;
import java.util.List;
import java.util.Map;
import utilities.JSONFactory;


public class JSONResponseValidationStepDefinitions {

  @Then("^verify response code is (.*)$")
  public void verifyResponseCode(int expectedResponseCode) throws Exception {

    Response response = PreviousTestEndpoint.response;

    assertTrue("response code is not as expected:" + expectedResponseCode + ", actual:"
        + response.getStatusCode(), response.getStatusCode() == expectedResponseCode);

  }

  @Then("^verify response body is JSON with tuple\\(s\\)$")
  public void verifyJSONResponseBody(Map<String, String> tuples) throws Exception {

    Response response = PreviousTestEndpoint.response;
    verifyJSONResponseBody(tuples, response.getBody().asString());
  }

  private void verifyJSONResponseBody(Map<String, String> tuples, String body) throws Exception {

    for (String key : tuples.keySet()) {

      String value = tuples.get(key);

      if (value == null) {
        assertTrue("JSON tuple:" + key + " is not null", JSONFactory.getValue(body, key) == null);
      } else if (value.equalsIgnoreCase("does not exists") || value
          .equalsIgnoreCase("doesn't exists")) {
        assertFalse("JSON tuple:" + key + " exists",
            JSONFactory.isExists(body, key));
      } else if (value.startsWith("contains ")) {
        JSONFactory.assertContains(body, key, value.substring(9));
      } else if (value.startsWith("!contains ")) {
        JSONFactory.assertNotContains(body, key, value.substring(10));
      } else if (value.startsWith("regex ")) {
        JSONFactory.assertMatch(body, key, value.substring(6));
      } else if (value.startsWith("has item ")) {
        JSONFactory.assertItem(body, key, value.substring(9));
      } else if (value.startsWith("does not have item ")) {
        JSONFactory.assertNoItem(body, key, value.substring(19));
      } else {
        JSONFactory.assertValue(body, key, value);
      }

    }

  }

}
