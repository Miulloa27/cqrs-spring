package com.mikauran.banking.account.common.events;

import com.mikauran.banking.account.common.dto.AccountType;
import com.mikauran.banking.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundsWithdrawnEvent extends BaseEvent {
    private String id;
    private String accountHolder;
    private AccountType accountType;
    private Date operationDate;

    private BigDecimal amount;
    private BigDecimal openingBalance;

}
