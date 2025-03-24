package com.matty.logging_framework;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.FileWriter;
import java.util.Date;

interface LogAppender {
    void append(LogMessage logMessage);
}

class FileAppender implements LogAppender {
    private String fileName;
    FileAppender(String fileName) {
        this.fileName = fileName;
    }
    @Override
    public void append(LogMessage logMessage) {
        try (FileWriter writer = new FileWriter(fileName, true)) {

            writer.write(logMessage.toString() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ConsoleAppender implements LogAppender {

    @Override
    public void append(LogMessage logMessage) {
        System.out.println(logMessage.getMessage());
    }
}

class TimeStampDecorator implements LogAppender {

    private final LogAppender appender;
    TimeStampDecorator(LogAppender appender) {
        this.appender = appender;
    }

    @Override
    public void append(LogMessage logMessage) {
        String formattedString = String.format("[%s] %s %s", logMessage.getMessage(), logMessage.getLevel(), logMessage.getCreatedOn());
        appender.append(new LogMessage(logMessage.getLevel(), formattedString));
    }
}
enum LogLevel {
    INFO, WARNING, ERROR
}


@Getter
class LogMessage {
    private final LogLevel level;
    private final String message;
    private final Date createdOn;
    LogMessage(LogLevel level, String message) {
        this.level = level;
        this.message = message;
        this.createdOn = new Date();
    }
}


@AllArgsConstructor
@Getter
class LoggerConfig {
    private LogLevel level;
    private LogAppender appender;
}

@Setter
class Logger {
    @Getter
    private LoggerConfig config;
    private static Logger instance = new Logger();
    private Logger() {
        config = new LoggerConfig(LogLevel.INFO, new ConsoleAppender());
    }
    public static Logger getInstance() {
        return instance;
    }

    private void log(LogLevel level, String message) {
        if (level.ordinal() >= this.getConfig().getLevel().ordinal()) {
            LogMessage logMessage = new LogMessage(level, message);
           this.getConfig().getAppender().append(logMessage);
        }
    }


    public void setConfig(LoggerConfig config) {
        this.config = config;
    }


    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

}
public class LoggingDemo {
    public static void main(String[] args) {
        Logger logger = Logger.getInstance();
        TimeStampDecorator timeStampDecorator = new TimeStampDecorator(new ConsoleAppender());
        logger.setConfig(new LoggerConfig(LogLevel.WARNING, timeStampDecorator));
        logger.info("This is info message");
        logger.warning("Warning");
    }
}
