package com.mikauran.banking.account.cmd.api.command;


import com.mikauran.banking.account.common.dto.AccountType;
import com.mikauran.banking.cqrs.core.commands.BaseCommand;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolder;
    private AccountType accountType;
    private BigDecimal openingBalance;
}
