package io.cucumber.report;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.io.IOException;

/*
 * Contains all hooks that needs to be executed before or after a scenario
 */
public class UtilityHooks {

  private static Scenario scenario;

  public static CustomLogFilter getLogFilter() {
    return logFilter;
  }

  private static CustomLogFilter logFilter;

  @Before
  public void beforeScenario(Scenario scenario) throws IOException {

    this.scenario = scenario;
    logFilter = new CustomLogFilter();

  }

  public static void extractAPIDetailsIntoLogs(){

    scenario.log(
        "\n-----API Request details-----\n" +
            logFilter.getRequestBuilder() +
            "\n\n-----API Response details-----" +
            logFilter.getResponseBuilder());
  }

  public static void logToReport(String message){

    scenario.log(message);
  }
}