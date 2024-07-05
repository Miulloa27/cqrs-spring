package com.mikauran.banking.cqrs.core.handlers;

import com.mikauran.banking.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);
    T getById(String id);
}
