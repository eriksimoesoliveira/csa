package com.eriks.core.util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    public static Logger getLogger() {
        Logger logger = Logger.getLogger("MyGameLogger");
        try {
            // Get the directory of the executable
            String logFilePath = Paths.get("").toAbsolutePath() + "/game.log";
            FileHandler fileHandler = new FileHandler(logFilePath, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }
}