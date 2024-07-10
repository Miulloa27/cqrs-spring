package com.mikauran.banking.account.query.infrastructure.handlers;

import com.mikauran.banking.account.common.events.AccountClosedEvent;
import com.mikauran.banking.account.common.events.AccountOpenedEvent;
import com.mikauran.banking.account.common.events.FundsDepositedEvent;
import com.mikauran.banking.account.common.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
    void on(FundsDepositedEvent event);
    void on(FundsWithdrawnEvent event);
    void on(AccountClosedEvent event);
}
