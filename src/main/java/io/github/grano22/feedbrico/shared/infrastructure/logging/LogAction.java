package io.github.grano22.feedbrico.shared.infrastructure.logging;

import org.slf4j.Marker;

@FunctionalInterface
public interface LogAction {
    void log(Marker marker, String message, Object... args);
}
