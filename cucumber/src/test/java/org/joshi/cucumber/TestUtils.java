package org.joshi.cucumber;

import org.joshi.pirates.ui.ConsoleUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> waitForUserPrompt(BufferedReader reader) throws IOException {
        return waitForPrompt(reader, ConsoleUtils.USER_PROMPT);
    }

    public void writeLine(BufferedWriter writer, String line) throws IOException {
        writer.write(line);
        writer.newLine();
        writer.flush();
        logger.push(line);
    }

}
