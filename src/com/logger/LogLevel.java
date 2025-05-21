package com.logger;

public enum LogLevel {
    DEBUG(1), INFO(2), WARN(3), ERROR(4), FATAL(5);

    private final int severity;

    LogLevel(int severity) {
        this.severity = severity;
    }

    public boolean isEnabled(LogLevel threshold) {
        return this.severity >= threshold.severity;
    }
}
