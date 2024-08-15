package io.cucumber.skeleton;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "io.cucumber")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
    value = "junit:target/cucumber-reports/Cucumber.xml,json:target/cucumber-reports/Cucumber.json,html:target/cucumber-reports/Cucumber.html,timeline:target/cucumber-reports/CucumberTimeline")
public class RunCucumberTest {

}