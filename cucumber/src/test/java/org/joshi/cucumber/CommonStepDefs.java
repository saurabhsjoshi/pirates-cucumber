package org.joshi.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class contains common step defs used across different features.
 */
public class CommonStepDefs {

    private Logger logger;
    private TestUtils testUtils;

    private Thread loggerThread;

    private Process server;

    private final List<String> playerNames = new ArrayList<>(3);

    private BufferedReader reader1;
    private BufferedWriter writer1;

    /**
     * The port number that is to be used with each test. It will be incremented each time a new server is started to
     * avoid conflicts.
     */
    private static final AtomicInteger port = new AtomicInteger(6794);

    @Before
    public void setup(Scenario scenario) throws IOException {
        logger = new Logger(new ArrayList<>(scenario.getSourceTagNames()).get(0).substring(1) + ".txt");
        loggerThread = new Thread(logger);
        loggerThread.start();
        testUtils = new TestUtils(logger);
    }

    @After
    public void teardown() {
        if (server != null)
            server.destroy();

        logger.stop();
        loggerThread.interrupt();
        try {
            loggerThread.join();
        } catch (InterruptedException ignore) {
        }
    }

    @Given("The game starts with {int} player")
    public void theGameStartsWithOnePlayer(int numPlayers) throws IOException {
        int p = port.addAndGet(1);
        server = startServer(p, numPlayers);

        writer1 = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        reader1 = new BufferedReader(new InputStreamReader(server.getInputStream()));
    }


    @And("The player names are the following")
    public void thePlayerNamesAreTheFollowing(List<String> names) throws IOException {
        playerNames.addAll(names);

        // Wait for player name prompt to show up
        testUtils.waitForUserPrompt(reader1);
        testUtils.writeLine(writer1, playerNames.get(0));
    }

    @When("{string} gets {string} fortune card")
    public void playerGetsFortuneCard(String playerName, String card) throws IOException {
        // Rig fortune card
        testUtils.rigFortuneCard(getReader(playerName), getWriter(playerName), RigUtils.getCard(card));
    }

    @When("{string} rolls the following")
    public void playerRollsTheFollowing(String playerName, List<String> roll) throws IOException {
        // Rig roll
        testUtils.rigDice(
                getReader(playerName),
                getWriter(playerName),
                RigUtils.getDice(roll));
    }

    @Then("{string} gets disqualified")
    public void playerGetsDisqualified(String playerName) {
        // TODO
    }

    @Then("{string} gets score of {int}")
    public void playerGetsScoreOf(String playerName, int score) {
        // TODO
    }

    private static String getJavaPath() {
        return ProcessHandle.current()
                .info()
                .command()
                .orElseThrow();
    }

    private static String getCurrentPath() {
        return Path.of("").toAbsolutePath().toString();
    }

    /**
     * Starts the server jar.
     *
     * @param port    the port number
     * @param players number of players in the game
     * @return process
     */
    private static Process startServer(int port, int players) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(getJavaPath(),
                "-jar",
                "server.jar",
                "PLAYERS", String.valueOf(players),
                "RIGGED",
                "PORT", String.valueOf(port));
        builder.directory(new File(getCurrentPath()));
        return builder.start();
    }

    private BufferedReader getReader(String playerName) {
        var index = playerNames.indexOf(playerName);

        if (index == 0) {
            return reader1;
        }

        return null;
    }

    private BufferedWriter getWriter(String playerName) {
        var index = playerNames.indexOf(playerName);

        if (index == 0) {
            return writer1;
        }

        return null;
    }
}
