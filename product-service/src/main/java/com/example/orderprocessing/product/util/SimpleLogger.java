package com.example.orderprocessing.product.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleLogger {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static void info(String message, Object... args) {
        log("INFO", message, args);
    }

    public static void error(String message, Object... args) {
        log("ERROR", message, args);
    }

    private static void log(String level, String message, Object... args) {
        String formattedMessage = message;
        for (Object arg : args) {
            formattedMessage = formattedMessage.replaceFirst("\\{\\}", arg == null ? "null" : arg.toString());
        }
        System.out.println(String.format("[%s] [%-5s] [%s] - %s",
                LocalDateTime.now().format(formatter),
                level,
                Thread.currentThread().getName(),
                formattedMessage));
    }
}
