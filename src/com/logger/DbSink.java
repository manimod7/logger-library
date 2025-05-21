package com.logger;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DbSink implements Sink {
    private final String host;
    private final String port;
    private final DateTimeFormatter formatter;
    private final boolean async;
    private final ExecutorService executor;

    public DbSink(String host, String port, DateTimeFormatter formatter, boolean async, boolean multiThreaded) {
        this.host = host;
        this.port = port;
        this.formatter = formatter;
        this.async = async;
        this.executor = multiThreaded ? Executors.newCachedThreadPool() : Executors.newSingleThreadExecutor();
    }

    @Override
    public void log(LogMessage message) {
        Runnable task = () -> {
            // Simulate a DB insert
            System.out.printf("INSERT INTO logs (timestamp, level, namespace, content) VALUES ('%s', '%s', '%s', '%s')%n",
                    formatter.format(message.getTimestamp()), message.getLevel(), message.getNamespace(), message.getContent());
        };

        if (async) executor.submit(task);
        else task.run();
    }

    @Override
    public void close() {
        executor.shutdown();
    }
}
