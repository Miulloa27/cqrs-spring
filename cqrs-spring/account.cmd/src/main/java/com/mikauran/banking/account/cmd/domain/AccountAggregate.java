package com.mikauran.banking.account.cmd.domain;

import com.mikauran.banking.account.cmd.api.command.OpenAccountCommand;
import com.mikauran.banking.account.common.events.AccountClosedEvent;
import com.mikauran.banking.account.common.events.AccountOpenedEvent;
import com.mikauran.banking.account.common.events.FundsDepositedEvent;
import com.mikauran.banking.account.common.events.FundsWithdrawnEvent;
import com.mikauran.banking.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private BigDecimal balance;

    public AccountAggregate(OpenAccountCommand command){
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .createdDate(new Date())
                .accountType(command.getAccountType())
                .build());
    }

    public void apply(AccountOpenedEvent event){
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFunds(BigDecimal amount){
        if(!this.active){
            throw new IllegalStateException("The found can not be added to this account");
        }

        if(amount.doubleValue() <= 0.0){
            throw new IllegalStateException("Deposit can be less than zero");
        }

        raiseEvent(FundsDepositedEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositedEvent event){
        this.id = event.getId();
        if (event.getAmount() != null) {
            this.balance = this.balance.add(event.getAmount());
            //this.balance += event.getAmount();
        }
    }

    public void withdrawFunds(BigDecimal amount) {
        if (!this.active) {
            throw new IllegalStateException("The bank account is closed");
        }

        raiseEvent(FundsWithdrawnEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawnEvent event){
        this.id = event.getId();
        if (event.getAmount() != null) {
            //this.balance -= event.getAmount();
            this.balance = this.balance.subtract(event.getAmount());
        }
    }

    public void closeAccount(){
        if(!this.active){
            throw new IllegalStateException("The account bank has been closed..");
        }

        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event){
        this.id = event.getId();
        this.active = false;
    }
}
