package io.restassured.skeleton;

import io.restassured.http.Cookie;
import io.restassured.http.Method;
import java.util.List;
import utilities.JSONFactory;
import java.util.HashMap;

public class ApplicationTestEndpoint extends TestEndpoint {

  TestEndPointDetails endPointDetails;

  public void setEndPointDetails(TestEndPointDetails endPointDetails) {
    this.endPointDetails = endPointDetails;
  }

  @Override
  public void constructRequest() throws Exception {

    super.constructRequest(endPointDetails.getUrl(), endPointDetails.getPath_variables(),
        endPointDetails.getHeaders(),
        endPointDetails.getParameters(), endPointDetails.getBody());

    if (endPointDetails.getCookies() != null && endPointDetails.getCookies().size() > 0) {
      for (String cookie : endPointDetails.getCookies().keySet()) {
        Cookie cookieObject = new Cookie.Builder(cookie, endPointDetails.getCookies().get(cookie))
            .build();
        super.request.cookie(cookieObject);
      }
    }
  }

  @Override
  public void sendRequest() throws Exception {

    super.sendRequest(Method.valueOf(endPointDetails.getMethod()));
  }


  public void editBody(List<String> editDetails) {

    for (String editDetail : editDetails) {

      editBody(editDetail.split(":")[0], editDetail.split(":")[1]);

    }
  }

  public void editBody(String tupleName, String tupleValue) {

    endPointDetails.setBody(JSONFactory.editJSON(endPointDetails.getBody(),
        tupleName, tupleValue));
    super.request.body(endPointDetails.getBody());


  }
}