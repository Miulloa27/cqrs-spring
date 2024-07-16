package com.mikauran.banking.cqrs.core.infraestructure;

import com.mikauran.banking.cqrs.core.queries.BaseQuery;
import com.mikauran.banking.cqrs.core.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler);
    <U extends BaseQuery>List<U> send(BaseQuery query);
}
