package org.joshi.easy.cucumber;

import org.joshi.pirates.ui.ConsoleUtils;
import scs.comp5903.cucumber.model.annotation.JAndStep;
import scs.comp5903.cucumber.model.annotation.JGivenStep;
import scs.comp5903.cucumber.model.annotation.JThenStep;
import scs.comp5903.cucumber.model.annotation.JWhenStep;

import java.io.*;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains common step defs used across different features.
 */
public class CommonStepDefs {

    private Logger logger;
    private TestUtils testUtils;

    private Thread loggerThread;

    private Process server, player2, player3;

    private final List<String> playerNames = new ArrayList<>(3);

    private BufferedReader reader1, reader2, reader3;
    private BufferedWriter writer1, writer2, writer3;

    /**
     * The port number that is to be used with each test. It will be incremented each time a new server is started to
     * avoid conflicts.
     */
    private static final AtomicInteger port = new AtomicInteger(6794);

    public void setup(String tag) throws IOException {
        logger = new Logger(tag + "-EC.txt");
        loggerThread = new Thread(logger);
        loggerThread.start();
        testUtils = new TestUtils(logger);
    }

    public void teardown() {
        if (server != null)
            server.destroy();
        if (player2 != null)
            player2.destroy();
        if (player3 != null)
            player3.destroy();

        logger.stop();
        loggerThread.interrupt();
        try {
            loggerThread.join();
        } catch (InterruptedException ignore) {
        }
    }

    @JGivenStep("The game starts with {int} player")
    public void theGameStartsWithPlayer(int numPlayers) throws IOException {
        int p = port.addAndGet(1);
        server = startServer(p, numPlayers);

        writer1 = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        reader1 = new BufferedReader(new InputStreamReader(server.getInputStream()));


        // Multiplayer game
        if (numPlayers == 3) {
            // Wait for server to start
            testUtils.waitForPrompt(reader1, ConsoleUtils.getServerStartedMsg());

            try {
                player2 = startClient(p);
                writer2 = new BufferedWriter(new OutputStreamWriter(player2.getOutputStream()));
                reader2 = new BufferedReader(new InputStreamReader(player2.getInputStream()));

                player3 = startClient(p);
                writer3 = new BufferedWriter(new OutputStreamWriter(player3.getOutputStream()));
                reader3 = new BufferedReader(new InputStreamReader(player3.getInputStream()));
            } catch (Exception ignore) {
                fail();
            }

        }
    }

    @JAndStep("The player names are the following {string}")
    public void thePlayerNamesAreTheFollowing(String nameStr) throws IOException {
        var names = nameStr.split("\\s+");
        playerNames.addAll(List.of(names));

        // Wait for player name prompt to show up
        testUtils.waitForUserPrompt(reader1);
        testUtils.writeLine(writer1, playerNames.get(0));

        if (playerNames.size() == 3) {
            try {
                testUtils.waitForUserPrompt(reader2);
                testUtils.writeLine(writer2, playerNames.get(1));
                Thread.sleep(Duration.ofMillis(100).toMillis());
                testUtils.waitForUserPrompt(reader3);
                testUtils.writeLine(writer3, playerNames.get(2));
            } catch (Exception ignore) {
                fail();
            }
        }
    }

    @JWhenStep("{string} gets {string} fortune card")
    public void playerGetsFortuneCard(String playerName, String card) throws IOException {
        // Rig fortune card
        testUtils.rigFortuneCard(getReader(playerName), getWriter(playerName), RigUtils.getCard(card));
    }

    @JAndStep("{string} rolls the following {string}")
    public void playerRollsTheFollowing(String playerName, String rollStr) throws IOException {
        var roll = List.of(rollStr.split(","));
        // Rig roll
        testUtils.rigDice(
                getReader(playerName),
                getWriter(playerName),
                RigUtils.getDice(roll));
    }

    @JAndStep("{string} re-rolls dice with index {string} to get the following {string}")
    public void playerReRollsDiceWithIndexToGetTheFollowing(String playerName,
                                                            String index,
                                                            String rollStr) throws IOException {

        // Wait for prompt
        testUtils.waitForUserPrompt(getReader(playerName));

        // Re roll
        testUtils.writeLine(getWriter(playerName), "1 " + index);

        var roll = List.of(rollStr.split(","));

        // Rig re roll
        testUtils.rigDice(
                getReader(playerName),
                getWriter(playerName),
                RigUtils.getDice(index, roll));
    }

    @JThenStep("{string} gets disqualified")
    public void playerGetsDisqualified(String playerName) throws IOException {
        assertTrue(testUtils.playerDeadMsg(getReader(playerName)));
    }

    private void playerScoresAreTheFollowing(String expectedStrScores) throws IOException {

        var split1 = expectedStrScores.split("\\s+");
        List<String> expectedScores = new ArrayList<>();

        for (int i = 0; i < split1.length; i += 2) {
            expectedScores.add(split1[i] + " " + split1[i + 1]);
        }

        var scores = testUtils.readScores(reader1, expectedScores.size());

        for (var expectedScore : expectedScores) {
            var split = expectedScore.split("\\s+");
            assertEquals(Integer.parseInt(split[1]), scores.get(split[0]));
        }
    }

    @JAndStep("{string} puts dice with index {string} in treasure chest")
    public void playerPutsDiceWithIndexInTreasureChest(String playerName, String index) throws IOException {
        // Wait for prompt
        testUtils.waitForUserPrompt(getReader(playerName));

        // Put in treasure chest
        testUtils.writeLine(getWriter(playerName), "2 " + index);
    }

    @JAndStep("Player scores are the following {string}")
    public void andPlayerScoresAreTheFollowing(String expectedStrScores) throws IOException {
        playerScoresAreTheFollowing(expectedStrScores);
    }

    @JThenStep("Player scores are the following {string}")
    public void thenPlayerScoresAreTheFollowing(String expectedStrScores) throws IOException {
        playerScoresAreTheFollowing(expectedStrScores);
    }

    @JAndStep("{string} ends turn")
    public void playerEndsTurn(String playerName) throws IOException {
        testUtils.endTurn(getReader(playerName), getWriter(playerName));
    }

    public void playerInflictsDamage(String playerName, int expectedDamage) throws IOException {
        var damage = testUtils.getDamage(getReader(playerName));
        assertEquals(expectedDamage, damage);
    }

    @JAndStep("{string} inflicts {int} damage")
    public void andPlayerInflictsDamage(String playerName, int expectedDamage) throws IOException {
        playerInflictsDamage(playerName, expectedDamage);
    }

    @JThenStep("{string} inflicts {int} damage")
    public void thenPlayerInflictsDamage(String playerName, int expectedDamage) throws IOException {
        playerInflictsDamage(playerName, expectedDamage);
    }

    @JThenStep("{string} is disqualified due to no skulls being rolled")
    public void playerIsDisqualifiedDueToNoSkullsBeingRolled(String playerName) throws IOException {
        assertTrue(testUtils.playerDeadMsgNoSkullsRolled(getReader(playerName)));
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
        builder.redirectError(ProcessBuilder.Redirect.INHERIT);
        return builder.start();
    }

    private static Process startClient(int port) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(getJavaPath(),
                "-jar",
                "client.jar",
                "RIGGED",
                "PORT", String.valueOf(port));
        builder.directory(new File(getCurrentPath()));
        //builder.redirectError(ProcessBuilder.Redirect.INHERIT);
        return builder.start();
    }

    private BufferedReader getReader(String playerName) {
        var index = playerNames.indexOf(playerName);

        return switch (index) {
            case 0 -> reader1;
            case 1 -> reader2;
            case 2 -> reader3;
            default -> null;
        };
    }

    private BufferedWriter getWriter(String playerName) {
        var index = playerNames.indexOf(playerName);

        return switch (index) {
            case 0 -> writer1;
            case 1 -> writer2;
            case 2 -> writer3;
            default -> null;
        };
    }
}
