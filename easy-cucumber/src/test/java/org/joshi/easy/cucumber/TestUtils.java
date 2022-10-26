package org.joshi.easy.cucumber;

import org.joshi.pirates.Turn;
import org.joshi.pirates.cards.FortuneCard;
import org.joshi.pirates.cards.SeaBattleCard;
import org.joshi.pirates.cards.SkullCard;
import org.joshi.pirates.ui.ConsoleUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class TestUtils {

    private final Logger logger;

    public TestUtils(Logger logger) {
        this.logger = logger;
    }

    public List<String> waitForPrompt(BufferedReader reader, String prompt, boolean startsWith) throws IOException {
        List<String> lines = new ArrayList<>();

        Function<String, Boolean> check = (String line) -> {
            if (startsWith) {
                return line.startsWith(prompt);
            }
            return line.equals(prompt);
        };

        String line = reader.readLine();

        while (line != null && !check.apply(line)) {

            if (!line.isBlank()) {
                lines.add(line);
                logger.push(line);
            }
            line = reader.readLine();
        }
        lines.add(line);
        logger.push(line);
        return lines;
    }

    /**
     * Function that waits until a prompt is printed in the buffered reader.
     *
     * @return returns list of lines that were printed before the prompt showed
     */
    public List<String> waitForPrompt(BufferedReader reader, String prompt) throws IOException {
        return waitForPrompt(reader, prompt, false);
    }

    public void waitForUserPrompt(BufferedReader reader) throws IOException {
        waitForPrompt(reader, ConsoleUtils.USER_PROMPT);
    }

    public String getWinner(BufferedReader reader) throws IOException {
        waitForPrompt(reader, ConsoleUtils.getSysMsg(ConsoleUtils.WINNER_MSG));
        String winner = reader.readLine();
        logger.push(winner);
        return winner.split("\\s+")[1];
    }

    /**
     * Waits for a user prompt and then enters the given rigged fortune card.
     */
    public void rigFortuneCard(BufferedReader reader, BufferedWriter writer, FortuneCard fortuneCard) throws IOException {
        waitForUserPrompt(reader);
        String line;
        if (fortuneCard instanceof SeaBattleCard seaBattleCard) {
            line = FortuneCard.Type.SEA_BATTLE.ordinal() + " " + seaBattleCard.getSwords();
        } else if (fortuneCard instanceof SkullCard skullCard) {
            line = FortuneCard.Type.SKULLS.ordinal() + " " + skullCard.getSkulls();
        } else {
            line = String.valueOf(fortuneCard.getType().ordinal());
        }

        writeLine(writer, line);
    }

    public void rigDice(BufferedReader reader, BufferedWriter writer, List<Turn.RiggedDie> dice) throws IOException {
        waitForUserPrompt(reader);
        StringJoiner joiner = new StringJoiner(" ");
        for (var die : dice) {
            joiner.add(String.valueOf(die.index()));
            joiner.add(String.valueOf(die.die().getDiceSide().ordinal()));
        }

        writeLine(writer, joiner.toString());
    }

    public List<String> waitForEndTurn(BufferedReader reader, String playerName) throws IOException {
        return waitForPrompt(reader, ConsoleUtils.getEndTurnMsg(playerName));
    }

    public boolean playerDeadMsgNoSkullsRolled(BufferedReader reader) throws IOException {
        var lines = waitForPrompt(reader, ConsoleUtils.getSysMsg(ConsoleUtils.DEAD_MSG_SKULL_ISLAND));
        boolean playerDied = false;

        for (String line : lines) {
            if (line.equals(ConsoleUtils.getSysMsg(ConsoleUtils.DEAD_MSG_SKULL_ISLAND))) {
                playerDied = true;
                break;
            }
        }

        return playerDied;
    }

    /**
     * Method that returns true if the output contains player dead message.
     */
    public boolean playerDeadMsg(BufferedReader reader) throws IOException {
        var lines = waitForPrompt(reader, ConsoleUtils.getSysMsg(ConsoleUtils.DEAD_MSG));
        boolean playerDied = false;

        for (String line : lines) {
            if (line.equals(ConsoleUtils.getSysMsg(ConsoleUtils.DEAD_MSG))) {
                playerDied = true;
                break;
            }
        }

        return playerDied;
    }

    public void endTurn(BufferedReader reader, BufferedWriter writer) throws IOException {
        waitForUserPrompt(reader);
        writeLine(writer, "0");
    }

    public int getDamage(BufferedReader reader) throws IOException {
        var lines = waitForPrompt(reader, ConsoleUtils.SYSTEM_MSG_SEPARATOR + ConsoleUtils.DAMAGE_MSG, true);
        var split = lines.get(lines.size() - 1).split("\\s+");

        // Should contain something like 'xyz######'
        var scoreLine = split[split.length - 1];
        // Remove the separator
        return Integer.parseInt(scoreLine.substring(0, scoreLine.length() - ConsoleUtils.SYSTEM_MSG_SEPARATOR.length()));
    }

    public int getLoss(BufferedReader reader) throws IOException {
        var lines = waitForPrompt(reader, ConsoleUtils.SYSTEM_MSG_SEPARATOR + ConsoleUtils.LOSS_MSG, true);
        var split = lines.get(lines.size() - 1).split("\\s+");

        // Should contain something like 'xyz######'
        var scoreLine = split[split.length - 1];
        // Remove the separator
        return Integer.parseInt(scoreLine.substring(0, scoreLine.length() - ConsoleUtils.SYSTEM_MSG_SEPARATOR.length()));
    }

    public void writeLine(BufferedWriter writer, String line) throws IOException {
        if (writer == null) {
            return;
        }
        writer.write(line);
        writer.newLine();
        writer.flush();
        logger.push(line);
    }

    public Map<String, Integer> readScores(BufferedReader reader, int num) throws IOException {
        var scores = new HashMap<String, Integer>();
        waitForPrompt(reader, ConsoleUtils.getSysMsg(ConsoleUtils.SCORE_MSG));

        for (int i = 0; i < num; i++) {
            String line = reader.readLine();
            logger.push(line);
            var split = line.split("\\s+");
            scores.put(split[0], Integer.parseInt(split[1]));
        }

        return scores;
    }
}
