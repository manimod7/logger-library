package com.logger;

import java.io.FileInputStream;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class LoggerConfig {
    public final DateTimeFormatter formatter;
    public final LogLevel threshold;
    public final String sinkType;
    public final Properties props;
    public final boolean async;
    public final boolean multiThreaded;
    public final long maxSize;

    public LoggerConfig(String configPath) throws Exception {
        props = new Properties();
        try (var in = new FileInputStream(configPath)) {
            props.load(in);
        }

        this.formatter = DateTimeFormatter.ofPattern(props.getProperty("ts_format"));
        this.threshold = LogLevel.valueOf(props.getProperty("log_level"));
        this.sinkType = props.getProperty("sink_type");
        this.async = "ASYNC".equalsIgnoreCase(props.getProperty("write_mode", "SYNC"));
        this.multiThreaded = "MULTI".equalsIgnoreCase(props.getProperty("thread_model", "SINGLE"));
        this.maxSize = Long.parseLong(props.getProperty("max_file_size_bytes", "1048576"));
    }
}
