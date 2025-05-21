package com.logger;

import java.net.InetAddress;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class LogMessage {
    private final Instant timestamp;
    private final LogLevel level;
    private final String namespace;
    private final String content;
    private final String trackingId;
    private final String hostName;


    public LogMessage(LogLevel level, String namespace, String content) {
        this.timestamp = Instant.now();
        this.level = level;
        this.namespace = namespace;
        this.content = content;
        this.trackingId = UUID.randomUUID().toString();
        String host;
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            host = "unknown-host";
        }
        this.hostName = host;
    }

    public String format(DateTimeFormatter formatter) {
        return String.format("[%s] [%s] [%s] [%s] [%s] %s",
                formatter.format(timestamp.atZone(ZoneId.systemDefault())),
                hostName,
                trackingId,
                level,
                namespace,
                content
        );
    }

    public LogLevel getLevel() { return level; }
    public String getNamespace() { return namespace; }
    public String getContent() { return content; }
    public Instant getTimestamp() { return timestamp; }
}
