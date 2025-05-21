# 🪵 Java Logger Library

A lightweight, extensible logger library built with **Java 17**. This library supports logging messages to configurable sinks (such as text files and databases), with features like log level filtering, timestamp formatting, log rotation, asynchronous writing, and enriched context.

---

## ✨ Features

- ✅ Log levels: DEBUG, INFO, WARN, ERROR, FATAL
- ✅ Timestamp enrichment (configurable format)
- ✅ Hostname and tracking ID per log message
- ✅ Pluggable sinks (FileSink, DbSink implemented)
- ✅ Config-driven behavior
- ✅ Async and multi-threaded logging support
- ✅ Log rotation with gzip compression
- ✅ Zero dependencies (pure Java)

---

## 🛠 Configuration

### `sample-file-logger.properties`

```properties
ts_format=yyyy-MM-dd HH:mm:ss,SSS
log_level=INFO
sink_type=FILE
file_location=./logs/app.log
thread_model=SINGLE
write_mode=SYNC
max_file_size_bytes=1048576
```

### `sample-db-logger.properties`

```properties
ts_format=dd:MM:yyyy HH:mm:ss
log_level=ERROR
sink_type=DB
dbhost=127.0.0.1
dbport=5432
thread_model=MULTI
write_mode=ASYNC
```

---

## 📦 Project Structure

```text
logger_project/
├── src/com/logger/
│   ├── Logger.java
│   ├── LoggerConfig.java
│   ├── LogLevel.java
│   ├── LogMessage.java
│   ├── Sink.java
│   ├── FileSink.java
│   ├── DbSink.java
│   └── Main.java
├── config/
│   ├── sample-file-logger.properties
│   └── sample-db-logger.properties
├── logs/
│   └── app.log
├── README.md
└── .gitignore
```

---

## 🏃‍♂️ How to Run

### CLI

```bash
javac -d out src/com/logger/*.java
java -cp out com.logger.Main
```

### IntelliJ

1. Open `logger_project`
2. Right-click `src` → Mark as Sources Root
3. Right-click `Main.java` → Run

---

## 🧠 Design Overview

- **Logger**: main logging API, routes messages to sinks
- **LogMessage**: represents enriched message with timestamp, host, etc.
- **Sink**: interface implemented by FileSink and DbSink
- **FileSink**: writes to rotating, compressed files
- **DbSink**: mocks a DB sink using console output
- **LoggerConfig**: parses property files for behavior

---

## 📄 Sample Output

```text
[2025-05-21 13:59:48,894] [HOST123] [c736e06b-66fb-4926-a8a9-afd330e68b03] [INFO] [App.Main] Logger started
[2025-05-21 13:59:48,989] [HOST123] [a3a2cea7-3da1-4d5e-a6b6-15fd60851e0c] [ERROR] [App.DB] Database error occurred
```

---

## 🧱 Extending the Logger

To add a new sink:

```java
public class KafkaSink implements Sink {
    public void log(LogMessage msg) { /* push to Kafka */ }
    public void close() {}
}
```

Register it in `Logger.createSink()`.

---