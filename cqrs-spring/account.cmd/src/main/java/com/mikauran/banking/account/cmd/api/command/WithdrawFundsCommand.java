package com.mikauran.banking.account.cmd.api.command;

import com.mikauran.banking.cqrs.core.commands.BaseCommand;

import java.math.BigDecimal;

public class WithdrawFundsCommand extends BaseCommand {
    private BigDecimal amount;
}
