package utilities;

import static org.junit.Assert.assertTrue;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import java.util.Collections;


/*
 * This class contains code to read values from json tuple, verify json tuple exists, asserts value
 * in json tuple.
 */
public class JSONFactory {

  private static final Configuration configurationDefault =
      Configuration.builder()
          .jsonProvider(new JacksonJsonNodeJsonProvider())
          .mappingProvider(new JacksonMappingProvider())
          .build();


  private static final Configuration configurationForArrayAdditions =
      Configuration.defaultConfiguration()
          .addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);

  public static String getValue(String jsonContent, String path)
      throws PathNotFoundException {
    Object content = JsonPath.read(jsonContent, path);
    return content == null ? null : content.toString();
  }

  public static Object get(String jsonContent, String path)
      throws PathNotFoundException {
    Object content = JsonPath.read(jsonContent, path);
    return content;
  }

  public static boolean isExists(String jsonString, String path)
      throws PathNotFoundException {

    try {

      try {
        net.minidev.json.JSONArray actualList = (net.minidev.json.JSONArray) get(jsonString, path);

        if (actualList.size() == 0) {
          throw new PathNotFoundException();
        }
      } catch (ClassCastException e) {
        getValue(jsonString, path);
      }
    } catch (PathNotFoundException e) {
      return false;
    }

    return true;
  }

  public static boolean assertValue(String jsonString, String path, String expectedValue)
      throws Exception {

    try {
      String actualValue = getValue(jsonString, path);
      assertTrue("JSON value @ path:" + path + " is not as expected:" + expectedValue + ", actual:"
          + actualValue, expectedValue.equals(actualValue));
    } catch (PathNotFoundException e) {
      assertTrue("JSON @ path:" + path + " does not exists", false);
      return false;
    }

    return true;
  }

  public static boolean assertMatch(String jsonString, String path, String regex) throws Exception {

    try {
      String actualValue = getValue(jsonString, path);
      assertTrue(
          "JSON value @ path:" + path + " is not as expected:" + regex + ", actual:" + actualValue,
          actualValue.matches(regex));
    } catch (PathNotFoundException e) {
      assertTrue("JSON @ path:" + path + " does not exists", false);
      return false;
    }

    return true;
  }

  public static boolean assertContains(String jsonString, String path, String expectedValue)
      throws Exception {

    try {
      String actualValue = getValue(jsonString, path);
      assertTrue("JSON value @ path:" + path + " is not as expected:" + expectedValue + ", actual:"
          + actualValue, actualValue.contains(expectedValue));
    } catch (PathNotFoundException e) {
      assertTrue("JSON @ path:" + path + " does not exists", false);
      return false;
    }

    return true;
  }

  public static boolean assertNotContains(String jsonString, String path, String expectedValue)
      throws Exception {

    try {
      String actualValue = getValue(jsonString, path);
      assertTrue("JSON value @ path:" + path + " should not contain :" + expectedValue + ", actual:"
          + actualValue, !actualValue.contains(expectedValue));
    } catch (PathNotFoundException e) {
      assertTrue("JSON @ path:" + path + " does not exists", false);
      return false;
    }

    return true;
  }

  public static boolean assertItem(String jsonString, String path, String item) throws Exception {

    try {
      net.minidev.json.JSONArray actualList = (net.minidev.json.JSONArray) get(jsonString, path);
      assertTrue(
          "Array list @ path:" + path + " does not have item:" + item + ", actual list:"
              + actualList,
          actualList.toString().contains(item));
    } catch (PathNotFoundException e) {
      assertTrue("JSON @ path:" + path + " does not exists", false);
      return false;
    }

    return true;
  }

  public static boolean assertNoItem(String jsonString, String path, String item) throws Exception {

    try {
      net.minidev.json.JSONArray actualList = (net.minidev.json.JSONArray) get(jsonString, path);
      assertTrue(
          "Array list @ path:" + path + " does have item:" + item + ", actual list:"
              + actualList,
          !actualList.toString().contains(item));
    } catch (PathNotFoundException e) {
      assertTrue("JSON @ path:" + path + " does not exists", false);
      return false;
    }

    return true;
  }


  public static String editJSON(String body, String tupleName, String tupleValue) {

    tupleValue = tupleValue.equalsIgnoreCase("null") ? null : tupleValue;

    try {
      JsonPath.using(configurationDefault).parse(body).read(tupleName).toString();

      body = setJSON(body, tupleName, tupleValue);
    } catch (com.jayway.jsonpath.PathNotFoundException e) {

      body = addJSON(body, tupleName, tupleValue);
    }

    body = body.replaceAll("\"\\{\\s{0,}\\}\"", "{}").replaceAll("\"\\[\\s{0,}\\]\"", "[]");
    return body;
  }

  public static String setJSON(String body, String key, String value) {

    JsonNode updatedJson = JsonPath.using(configurationDefault).parse(body)
        .set(key, value).json();
    body = updatedJson.toString();

    return body;
  }

  public static String addJSON(String body, String key, String value) {
    String newField = key;
    String parentField = "";
    if (newField.contains(".")) {
      newField = key.substring(key.lastIndexOf('.') + 1);
      parentField = key.substring(0, key.lastIndexOf('.'));
    }

    JsonNode updatedJson = JsonPath.using(configurationDefault).parse(body)
        .put("$" + (parentField.equals("") ? "" : ("." + parentField)), newField,
            value).json();
    body = updatedJson.toString();

    try {

      JsonPath.using(configurationDefault).parse(body).read(key).toString();
    } catch (com.jayway.jsonpath.PathNotFoundException e) {

      JsonPath pathToArray = JsonPath.compile("$." + parentField.replaceAll("\\[\\d+\\]", ""));

      DocumentContext document = JsonPath.using(configurationForArrayAdditions).parse(body);
      body = document.add(pathToArray, Collections
          .singletonMap(newField, value)).jsonString();
    }

    return body;
  }
}