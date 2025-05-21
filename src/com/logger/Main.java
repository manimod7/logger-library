package com.logger;

public class Main {
    public static void main(String[] args) throws Exception {
        LoggerConfig config = new LoggerConfig("config/sample-file-logger.properties");
        Logger logger = new Logger(config);

        logger.info("App.Main", "Logger started");
        logger.warn("App.User", "This is a warning");
        logger.error("App.DB", "Database error occurred");
        logger.debug("App.Debug", "Debugging info");
        logger.fatal("App.Fatal", "Critical failure");

        logger.shutdown();
    }
}
