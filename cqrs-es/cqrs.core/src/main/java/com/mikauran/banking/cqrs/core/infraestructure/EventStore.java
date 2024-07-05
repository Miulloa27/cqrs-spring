package com.mikauran.banking.cqrs.core.infraestructure;

import com.mikauran.banking.cqrs.core.events.BaseEvent;

import java.util.List;

public interface EventStore {
    void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion);
    List<BaseEvent> getEvent(String aggregateId);
}
