package io.github.grano22.feedbrico.shared.application;

import java.time.LocalDateTime;
import java.util.UUID;

/** @apiNote Event used only internally by the app cannot be used outside as an external contract */
public interface AppEvent<M, P> {
    public UUID id();
    public M metadata();
    public P payload();
    public LocalDateTime occurredAt();
}
