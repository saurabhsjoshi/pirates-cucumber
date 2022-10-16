package org.joshi.cucumber;

import org.joshi.pirates.Turn;
import org.joshi.pirates.cards.FortuneCard;
import org.joshi.pirates.cards.SeaBattleCard;
import org.joshi.pirates.cards.SkullCard;
import org.joshi.pirates.ui.ConsoleUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class TestUtils {

    private final Logger logger;

    public TestUtils(Logger logger) {
        this.logger = logger;
    }

    /**
     * Function that waits until a prompt is printed in the buffered reader.
     *
     * @return returns list of lines that were printed before the prompt showed
     */
    public List<String> waitForPrompt(BufferedReader reader, String prompt) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = reader.readLine();
        while (line != null && !line.equals(prompt)) {
            if (!line.isBlank()) {
                lines.add(line);
                logger.push(line);
            }
            line = reader.readLine();
        }
        logger.push(line);
        return lines;
    }

    public void waitForUserPrompt(BufferedReader reader) throws IOException {
        waitForPrompt(reader, ConsoleUtils.USER_PROMPT);
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

    /**
     * Method that returns true if the output contains player dead message.
     */
    public boolean playerDeadMsg(BufferedReader reader, String playerName) throws IOException {
        var lines = waitForEndTurn(reader, playerName);
        boolean playerDied = false;

        for (String line : lines) {
            if (line.equals(ConsoleUtils.getSysMsg(ConsoleUtils.DEAD_MSG))) {
                playerDied = true;
                break;
            }
        }

        return playerDied;
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
