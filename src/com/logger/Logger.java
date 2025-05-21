package com.logger;

public class Logger {
    private final Sink sink;
    private final LoggerConfig config;

    public Logger(LoggerConfig config) throws Exception {
        this.config = config;
        this.sink = createSink();
    }

    private Sink createSink() throws Exception {
        return switch (config.sinkType.toUpperCase()) {
            case "FILE" -> new FileSink(config.props.getProperty("file_location"), config.formatter,
                    config.async, config.multiThreaded, config.maxSize);
            case "DB" -> new DbSink(config.props.getProperty("dbhost"), config.props.getProperty("dbport"),
                    config.formatter, config.async, config.multiThreaded);
            default -> throw new IllegalArgumentException("Unknown sink type: " + config.sinkType);
        };
    }

    public void log(LogLevel level, String namespace, String message) {
        if (level.isEnabled(config.threshold)) {
            try {
                sink.log(new LogMessage(level, namespace, message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void info(String ns, String msg) { log(LogLevel.INFO, ns, msg); }
    public void debug(String ns, String msg) { log(LogLevel.DEBUG, ns, msg); }
    public void warn(String ns, String msg) { log(LogLevel.WARN, ns, msg); }
    public void error(String ns, String msg) { log(LogLevel.ERROR, ns, msg); }
    public void fatal(String ns, String msg) { log(LogLevel.FATAL, ns, msg); }

    public void shutdown() throws Exception {
        sink.close();
    }
}
