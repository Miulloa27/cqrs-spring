package com.mikauran.banking.account.query.api.queries;

import com.mikauran.banking.account.query.api.dto.EqualityType;
import com.mikauran.banking.cqrs.core.queries.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class FindAccountWithBalanceQuery extends BaseQuery {
    private BigDecimal balance;
    private EqualityType equalityType;
}
