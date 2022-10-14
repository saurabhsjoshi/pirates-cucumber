package org.joshi.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HelloStepDefs {
    @Given("I want to say {string}")
    public void iWantToSayHello(String hello) {
        System.out.println("User says " + hello);
    }

    @When("I am in the {string}")
    public void iAmInTheWorld(String place) {
        System.out.println("User is in " + place);
    }

    @Then("Should see {string}")
    public void shouldSeeHelloWorld(String msg) {
        System.out.println("User sees " + msg);
    }
}
