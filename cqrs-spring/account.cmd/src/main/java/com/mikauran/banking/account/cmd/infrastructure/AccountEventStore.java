package com.mikauran.banking.account.cmd.infrastructure;

import com.mikauran.banking.account.cmd.domain.AccountAggregate;
import com.mikauran.banking.account.cmd.domain.EventStoreRepository;
import com.mikauran.banking.cqrs.core.events.BaseEvent;
import com.mikauran.banking.cqrs.core.events.EventModel;
import com.mikauran.banking.cqrs.core.exceptions.AggregateNotFoundException;
import com.mikauran.banking.cqrs.core.exceptions.ConcurrencyException;
import com.mikauran.banking.cqrs.core.infraestructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountEventStore implements EventStore {

    @Autowired
    private EventStoreRepository eventStoreRepository;
    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);

        if(expectedVersion != -1 && eventStream.get(eventStream.size() -1).getVersion() != expectedVersion){
            throw new ConcurrencyException();
        }

        var version = expectedVersion;

        for(var event: events){
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timeStamp(new Date())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();

            var persistedEvent = eventStoreRepository.save(eventModel);

            if(persistedEvent != null){
                //Produce or create a Kafka Event
                //**TODO Produce or create a Kafka Event **//


            }
        }
    }

    @Override
    public List<BaseEvent> getEvent(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);

        if(eventStream == null || eventStream.isEmpty()){
            throw new AggregateNotFoundException("The bank account is incorrect");
        }
        //return eventStream.stream().map(evt -> evt.getEventData()).collect(Collectors.toList());
        return eventStream.stream().map(EventModel::getEventData).collect(Collectors.toList());
    }
}
