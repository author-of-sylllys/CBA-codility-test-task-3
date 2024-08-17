package io.restassured.skeleton;

import io.cucumber.report.UtilityHooks;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class TestEndpoint {

  protected URL endPointURL = null;
  protected Response response = null;
  protected RequestSpecification request = null;
  protected Map<String, String> urlQueryParameters = null;

  public Response getResponse() {
    return this.response;
  }

  public abstract void constructRequest() throws Exception;

  protected void constructRequest(String url, HashMap<String, String> pathVariables,
      HashMap<String, String> requestHeaders,
      HashMap<String, String> queryParams, Object body) throws Exception {

    request = RestAssured.given();
    request.given().filter(UtilityHooks.getLogFilter()).contentType(ContentType.JSON);

    endPointURL = new URL(url);

    if (pathVariables != null) {

      request.pathParams(pathVariables);
    }

    if (queryParams != null) {

      urlQueryParameters = new HashMap<String, String>(queryParams);

      while (queryParams.values().remove(null)) {
        ;
      }

      urlQueryParameters = queryParams;

      request.queryParams(queryParams);
    }

    if (requestHeaders != null) {

      while (requestHeaders.values().remove(null)) {
        ;
      }

      request.headers(requestHeaders);
    }

    if (body != null) {

      if (requestHeaders != null && requestHeaders.containsKey("content-type") && requestHeaders
          .get("content-type")
          .contains("multipart/form-data")) {

        HashMap<String, String> formData = (HashMap<String, String>) body;

        for (String key : formData.keySet()) {
          if (key.equals("file") || key.equals("files")) {

            String filePaths = formData.get(key);

            for (String filePath : filePaths.split(";")) {
              request.contentType(requestHeaders.get("content-type"))
                  .multiPart(key, new File(filePath));
            }
          } else {

            request.contentType(requestHeaders.get("content-type"))
                .multiPart(key, formData.get(key));
          }
        }
      } else if (requestHeaders != null && requestHeaders.containsKey("content-type")
          && requestHeaders
          .get("content-type")
          .contains("application/x-www-form-urlencoded")) {

        request.contentType(requestHeaders.get("content-type"))
            .formParams((Map<String, String>) body);

      } else {

        request.body(body.toString());
      }
    }
  }

  public abstract void sendRequest() throws Exception;

  protected void sendRequest(Method httpMethod) throws Exception {

    response = request.request(httpMethod, endPointURL);

    UtilityHooks.extractAPIDetailsIntoLogs();

    PreviousTestEndpoint.details(request, response, endPointURL, httpMethod);
  }

}