package org.joshi.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains common step defs used across different features.
 */
public class CommonStepDefs {

    private Logger logger;
    private Thread loggerThread;

    @Before
    public void setup(Scenario scenario) {
        logger = new Logger(new ArrayList<>(scenario.getSourceTagNames()).get(0).substring(1) + ".txt");
        loggerThread = new Thread(logger);
        loggerThread.start();
    }

    @After
    public void teardown() {
        logger.stop();
        loggerThread.interrupt();
        try {
            loggerThread.join();
        } catch (InterruptedException ignore) {
        }
    }

    @Given("The game starts with {int} player")
    public void theGameStartsWithOnePlayer(int numPlayers) {
        // TODO
    }


    @And("The player names are the following")
    public void thePlayerNamesAreTheFollowing(List<String> names) {
        //TODO
    }

    @When("{string} rolls the following")
    public void playerRollsTheFollowing(String playerName, List<String> roll) {

    }

    @Then("{string} gets score of {int}")
    public void playerGetsScoreOf(String playerName, int score) {
        // TODO
    }
}
