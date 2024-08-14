package io.cucumber.skeleton;

import io.cucumber.java.en.Given;

public class StepDefinitions {

  @Given("verify cucumber setup")
  public void verify_cucumber_setup() {

    System.out.println("Cucumber setup is working!");
  }

}
