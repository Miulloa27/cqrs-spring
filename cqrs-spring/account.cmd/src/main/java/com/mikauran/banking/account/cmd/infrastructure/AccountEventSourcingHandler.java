package com.mikauran.banking.account.cmd.infrastructure;

import com.mikauran.banking.account.cmd.domain.AccountAggregate;
import com.mikauran.banking.cqrs.core.domain.AggregateRoot;
import com.mikauran.banking.cqrs.core.events.BaseEvent;
import com.mikauran.banking.cqrs.core.handlers.EventSourcingHandler;
import com.mikauran.banking.cqrs.core.infraestructure.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;

    @Autowired
    public AccountEventSourcingHandler(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommitedChanges(),
                              aggregate.getVersion());

        aggregate.markChangesAsCommit();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvent(id);

        if(events != null && !events.isEmpty()){
            aggregate.replayEvent(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion)
                                     .max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }
}
