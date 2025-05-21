package com.logger;

import java.io.*;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.zip.GZIPOutputStream;

public class FileSink implements Sink {
    private final Path filePath;
    private final DateTimeFormatter formatter;
    private final long maxSizeBytes;
    private final boolean async;
    private final ExecutorService executor;

    private BufferedWriter writer;

    public FileSink(String path, DateTimeFormatter formatter, boolean async, boolean multiThreaded, long maxSizeBytes) throws IOException {
        this.filePath = Paths.get(path);
        this.formatter = formatter;
        this.async = async;
        this.maxSizeBytes = maxSizeBytes;
        this.executor = multiThreaded ? Executors.newCachedThreadPool() : Executors.newSingleThreadExecutor();
        Files.createDirectories(filePath.getParent());
        this.writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    @Override
    public void log(LogMessage message) throws Exception {
        Runnable task = () -> {
            try {
                synchronized (this) {
                    writer.write(message.format(formatter));
                    writer.newLine();
                    writer.flush();
                    rotateIfNeeded();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        if (async) executor.submit(task);
        else task.run();
    }

    private void rotateIfNeeded() throws IOException {
        if (Files.size(filePath) > maxSizeBytes) {
            writer.close();
            Path rotated = Paths.get(filePath.toString() + "." + System.currentTimeMillis() + ".gz");
            try (
                    InputStream in = Files.newInputStream(filePath);
                    OutputStream out = new GZIPOutputStream(Files.newOutputStream(rotated))
            ) {
                in.transferTo(out);
            }
            Files.newBufferedWriter(filePath, StandardOpenOption.TRUNCATE_EXISTING).close();
            this.writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        }
    }

    @Override
    public void close() throws Exception {
        writer.close();
        executor.shutdown();
    }
}
