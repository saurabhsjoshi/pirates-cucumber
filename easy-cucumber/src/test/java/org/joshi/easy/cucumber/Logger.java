package org.joshi.easy.cucumber;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Utility class that writes content to file for multiplayer acceptance tests.
 */
public class Logger implements Runnable {
    private final BlockingQueue<String> queue = new ArrayBlockingQueue<>(200);

    private volatile boolean stopped = false;

    private final String filename;

    public Logger(String filename) throws IOException {
        Files.createDirectories(Paths.get("logs"));
        this.filename = "logs" + File.separator + filename;
    }

    @Override
    public void run() {
        BufferedWriter writer;

        try {
            writer = new BufferedWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (!stopped) {
            try {
                var line = queue.take();
                System.out.println(line);
                writer.write(line);
                writer.newLine();
                writer.flush();
            } catch (InterruptedException | IOException ignore) {
                break;
            }
        }
    }

    public void push(String line) {
        if (line == null || line.isBlank()) {
            return;
        }

        try {
            queue.put(line);
        } catch (InterruptedException ignore) {
        }
    }

    public void stop() {
        stopped = true;
    }
}
