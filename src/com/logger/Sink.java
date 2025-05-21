package com.logger;

public interface Sink {
    void log(LogMessage message) throws Exception;
    void close() throws Exception;
}
