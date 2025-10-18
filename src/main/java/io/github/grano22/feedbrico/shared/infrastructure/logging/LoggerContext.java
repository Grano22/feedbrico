package io.github.grano22.feedbrico.shared.infrastructure.logging;

import org.slf4j.MDC;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class LoggerContext {
    private static final ConcurrentHashMap<String, String> globalContextDetails = new ConcurrentHashMap<>();
    private static final Marker APPLICATION_SERVICE_MARKER = MarkerFactory.getMarker("APPLICATION_SERVICE");

    private Marker marker;
    private StringBuffer message = new StringBuffer();
    private ConcurrentHashMap<String, String> contextDetails = new ConcurrentHashMap<>();

    public static void setGlobalDetail(String key, String value) {
        globalContextDetails.computeIfAbsent(key, k -> value);
    }

    public static void clearGlobalDetails() {
        globalContextDetails.clear();
    }

    public static LoggerContext unknown(String name) {
        return new LoggerContext(null);
    }

    public static LoggerContext applicationService() {
        return new LoggerContext(APPLICATION_SERVICE_MARKER);
    }

    private LoggerContext(Marker marker) {
        this.marker = marker;
    }

    public LoggerContext nextMessage(String message) {
        if (!this.message.isEmpty()) {
            this.message.append(System.lineSeparator());
        }

        this.message.append(message);

        return this;
    }

    public LoggerContext replaceMessage(String message) {
        this.message = new StringBuffer(message);

        return this;
    }

    public void log(LogAction logAction) {
        for (var entry : globalContextDetails.entrySet()) {
            MDC.put(entry.getKey(), entry.getValue());
        }

        for (var entry : contextDetails.entrySet()) {
            MDC.put(entry.getKey(), entry.getValue());
        }

        logAction.log(marker, message.toString());

        MDC.clear();
    }
}
