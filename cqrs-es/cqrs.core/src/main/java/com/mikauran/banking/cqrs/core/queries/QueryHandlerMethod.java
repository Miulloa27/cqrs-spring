package com.mikauran.banking.cqrs.core.queries;

import com.mikauran.banking.cqrs.core.domain.BaseEntity;
import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod <T extends BaseQuery>{
    List<BaseEntity> handler(T query);
}
