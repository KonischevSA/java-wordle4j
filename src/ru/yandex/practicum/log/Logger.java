package ru.yandex.practicum.log;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private String logFile;
    private boolean isActive;

    public Logger(String logFile, boolean isActive) {

        this.logFile = logFile;
        this.isActive = isActive;
    }

    public void writeLog(String message, LogMessageType type) throws IOException {

        if (!isActive) {
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(logFile, true))) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            pw.printf("%s [%s] %s\n", currentDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")), type, message);

        } catch (IOException e) {
            throw new IOException("Ошибка записи в лог-файл.");
        }
    }

    public void writeLog(String message) throws IOException {
        writeLog(message, LogMessageType.INFO);
    }
}
