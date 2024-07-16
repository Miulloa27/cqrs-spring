package com.mikauran.banking.account.query.infrastructure;

import com.mikauran.banking.cqrs.core.infraestructure.QueryDispatcher;
import com.mikauran.banking.cqrs.core.queries.BaseQuery;
import com.mikauran.banking.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {
    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseQuery> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());

        if(handlers == null || handlers.size() <= 0){
            throw new RuntimeException("Neither query handler has been registered for this query object");
        }

        if(handlers.size() > 1){
            throw new RuntimeException("Is not possible send a Query that have two or more handlers");
        }

        return handlers.get(0).handler(query);
    }
}
