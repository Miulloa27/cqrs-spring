package com.mikauran.banking.account.common.events;

import com.mikauran.banking.account.common.dto.AccountType;
import lombok.Data;
import com.mikauran.banking.cqrs.core.events.BaseEvent;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
    private String id;
    private String accountHolder;
    private AccountType accountType;
    private Date closedDate;
}
