package com.mikauran.banking.cqrs.core.producers;

import com.mikauran.banking.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);

}
